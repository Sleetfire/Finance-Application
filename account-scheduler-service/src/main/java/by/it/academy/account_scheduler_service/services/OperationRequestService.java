package by.it.academy.account_scheduler_service.services;

import by.it.academy.account_scheduler_service.controllers.utils.JwtTokenUtil;
import by.it.academy.account_scheduler_service.exception.BadRequestException;
import by.it.academy.account_scheduler_service.models.dto.RequestOperation;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.services.api.IOperationRequestService;
import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OperationRequestService implements IOperationRequestService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${urls.post-url}")
    private String postUrl;

    public OperationRequestService() {
    }

    @Override
    public RequestOperation sendOperationRequest(ScheduledOperation scheduledOperation) {
        String accountUUID = scheduledOperation.getOperation().getAccount().toString();
        String url = String.format(this.postUrl, accountUUID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", this.createAuthorizationHeader(scheduledOperation.getUsername()));

        Map<String, Object> map = new HashMap<>();
        map.put("date", DateTimeUtil.convertLocalDateTimeToLong(LocalDateTime.now()));
        map.put("description", scheduledOperation.getOperation().getDescription());
        map.put("category", scheduledOperation.getOperation().getCategory());
        map.put("value", scheduledOperation.getOperation().getValue());
        map.put("currency", scheduledOperation.getOperation().getCurrency());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<RequestOperation> response = this.restTemplate.postForEntity(url, entity, RequestOperation.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new BadRequestException("Bad request to account-service with code " + response.getStatusCode());
        }
    }

    private String createAuthorizationHeader(String username) {
        String token = JwtTokenUtil.generateAccessToken(username);
        return "Bearer " + token;
    }
}
