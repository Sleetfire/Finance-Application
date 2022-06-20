package by.it.academy.user_service.services;

import by.it.academy.user_service.exception.BadRequestException;
import by.it.academy.user_service.services.api.IRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestService implements IRequestService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value(value = "${urls.email-url}")
    private String emailUrl;
    @Value(value = "${urls.verify-link}")
    private String verifyLink;

    @Override
    public void sendVerifyCode(String email, String name, String code) {
        HttpHeaders headers = new HttpHeaders();
        String link = String.format(verifyLink, code);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("name", name);
        map.put("link", link);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(emailUrl, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException("Bad request to mail-service with answer:" + response.getStatusCode());
        }

    }
}
