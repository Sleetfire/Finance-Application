package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.BadRequestException;
import by.it.academy.account_service.services.api.IValidateClassifiersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Service
public class ValidateClassifiersService implements IValidateClassifiersService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value(value = "${urls.currency-url}")
    private String currencyUrl;
    @Value(value = "${urls.operation-category-url}")
    private String operationCategoryUrl;

    @Override
    public boolean isCurrencyExist(UUID uuid) {
        String url = String.format(this.currencyUrl, uuid.toString());
        return this.isClassifierExist(url);
    }

    @Override
    public boolean isOperationCategoryExist(UUID uuid) {
        String url = String.format(this.operationCategoryUrl, uuid.toString());
        return this.isClassifierExist(url);
    }

    private boolean isClassifierExist(String url) {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class);
        if (Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)) {
            return true;
        } else if (Objects.equals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT)) {
            return false;
        } else {
            throw new BadRequestException("Bad request to classifier-service with code " + responseEntity.getStatusCode());
        }
    }
}
