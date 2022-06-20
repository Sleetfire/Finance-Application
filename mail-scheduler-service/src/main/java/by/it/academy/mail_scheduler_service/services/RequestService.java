package by.it.academy.mail_scheduler_service.services;

import by.it.academy.mail_scheduler_service.controllers.utils.JwtTokenUtil;
import by.it.academy.mail_scheduler_service.exceptions.BadRequestException;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.model.enums.ReportType;
import by.it.academy.mail_scheduler_service.services.api.IRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class RequestService implements IRequestService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${urls.report-service-url}")
    private String reportServiceUrl;

    @Override
    public void sendReport(ScheduledReport scheduledReport) {
        ReportType reportType = scheduledReport.getReport().getType();
        String url = String.format(this.reportServiceUrl, reportType.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", this.createAuthorizationHeader(scheduledReport.getUsername()));

        Map<String, Object> requestMap = scheduledReport.getReport().getParams();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);
        ResponseEntity<Map> response = this.restTemplate.postForEntity(url, entity, Map.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new BadRequestException("Bad request to report-service with code" + response.getStatusCode());
        }
    }

    private String createAuthorizationHeader(String username) {
        String token = JwtTokenUtil.generateAccessToken(username);
        return "Bearer " + token;
    }
}
