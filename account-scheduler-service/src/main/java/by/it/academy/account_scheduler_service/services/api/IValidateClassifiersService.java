package by.it.academy.account_scheduler_service.services.api;

import java.util.UUID;

public interface IValidateClassifiersService {

    /**
     * Checking currency's existing
     * @param uuid currency's uuid
     * @return true - if currency exists, false - if it doesn't exist
     */
    boolean isCurrencyExist(UUID uuid);

    /**
     * Checking operation category's existing
     * @param uuid operation category's uuid
     * @return true - if operation category exists, false - if it doesn't exist
     */
    boolean isOperationCategoryExist(UUID uuid);

}
