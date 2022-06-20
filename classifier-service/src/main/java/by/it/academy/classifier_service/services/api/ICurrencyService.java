package by.it.academy.classifier_service.services.api;

import by.it.academy.classifier_service.models.dto.Currency;
import by.it.academy.classifier_service.models.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICurrencyService {

    /**
     * Creating currency in database
     * @param currency  currency dto
     * @return created currency
     */
    Currency create(Currency currency);

    /**
     * Getting currency from database
     * @param uuid currency's uuid
     * @return currency
     */
    Currency get(UUID uuid);

    /**
     * Getting page of currencies
     * @param pageable param for pagination
     * @return page of currencies
     */
    PageDTO<Currency> getPage(Pageable pageable);

    /**
     * Getting all currencies
     * @return list of currencies
     */
    List<Currency> getAll();

}
