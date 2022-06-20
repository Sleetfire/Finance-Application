package by.it.academy.report_service.services;

import by.it.academy.report_service.controllers.utils.JwtTokenUtil;
import by.it.academy.report_service.exception.EssenceNotFoundException;
import by.it.academy.report_service.exception.ResponseException;
import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Currency;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;
import by.it.academy.report_service.services.api.IRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestService implements IRequestService {

    @Value(value = "${urls.account-url}")
    private String accountUrl;
    @Value(value = "${urls.operations-url}")
    private String operationsUrl;
    @Value(value = "${urls.currency-url}")
    private String currencyUrl;
    @Value(value = "${urls.operation-category-url}")
    private String operationCategoryUrl;
    @Value(value = "${urls.all-operation-category-url}")
    private String allOperationCategoryUrl;
    @Value(value = "${urls.email-url}")
    private String emailUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Account getAccount(UUID uuid, String username) {
        String url = String.format(this.accountUrl, uuid.toString());
        HttpEntity httpEntity = this.createHttpEntity(username);

        ResponseEntity<Account> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, Account.class, 1);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new EssenceNotFoundException("Account is not exist");
        } else {
            throw new ResponseException("the request to account-service is failed: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public List<Operation> getOperations(UUID uuid, String username) {
        String url = String.format(this.operationsUrl, uuid.toString());
        HttpEntity httpEntity = this.createHttpEntity(username);
        ResponseEntity<Operation[]> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, Operation[].class, 1);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            return Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new EssenceNotFoundException("Operations is not exist");
        } else {
            throw new ResponseException("the request to account-service is failed: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public Currency getCurrency(UUID uuid) {
        String url = String.format(this.currencyUrl, uuid.toString());
        HttpEntity httpEntity = this.createHttpEntity("");
        ResponseEntity<Currency> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, Currency.class, 1);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new EssenceNotFoundException("Currency is not exist");
        } else {
            throw new ResponseException("the request to classifier-service is failed: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public OperationCategory getOperationCategory(UUID uuid) {
        String url = String.format(this.operationCategoryUrl, uuid.toString());
        HttpEntity httpEntity = this.createHttpEntity("");
        ResponseEntity<OperationCategory> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, OperationCategory.class, 1);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new EssenceNotFoundException("Operation category is not exist");
        } else {
            throw new ResponseException("the request to classifier-service is failed: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public List<OperationCategory> getAllOperationCategory() {
        HttpEntity httpEntity = this.createHttpEntity("");
        ResponseEntity<OperationCategory[]> responseEntity = this.restTemplate.exchange(this.allOperationCategoryUrl,
                HttpMethod.GET, httpEntity, OperationCategory[].class, 1);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            return Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new EssenceNotFoundException("Operation categories are not exist");
        } else {
            throw new ResponseException("the request to classifier-service is failed: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public void sendReportToMail(IReport report) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();
        map.put("file_url", report.getUrl());
        map.put("email", report.getUsername());
        map.put("description", report.getDescription());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = this.restTemplate.postForEntity(this.emailUrl, entity, Map.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseException("the request to mail-service is failed: " + response.getStatusCode());
        }
    }

    private HttpEntity createHttpEntity(String username) {
        return new HttpEntity(this.createHeaders(username));
    }

    private HttpHeaders createHeaders(String username) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if (!username.isEmpty()) {
            httpHeaders.set("Authorization", this.createAuthorizationHeader(username));
        }
        return httpHeaders;
    }

    private String createAuthorizationHeader(String username) {
        String token = JwtTokenUtil.generateAccessToken(username);
        return "Bearer " + token;
    }
}
