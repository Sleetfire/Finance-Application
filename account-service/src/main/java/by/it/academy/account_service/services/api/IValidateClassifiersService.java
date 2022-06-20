package by.it.academy.account_service.services.api;

import java.util.UUID;

public interface IValidateClassifiersService {

    /**
     * Checking currency existing by it id
     * @param uuid currency's id
     * @return true if currency is existing, false - if currency is not existing
     */
    boolean isCurrencyExist(UUID uuid);

    /**
     * Checking operation category existing by it id
     * @param uuid operation category's id
     * @return true if operation category is existing, false - if operation category is not existing
     */
    boolean isOperationCategoryExist(UUID uuid);

}
