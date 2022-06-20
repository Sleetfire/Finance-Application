package by.it.academy.account_service.services.api;

import by.it.academy.account_service.models.dto.Balance;

import java.math.BigDecimal;
import java.util.UUID;

public interface IBalanceService {

    /**
     * Saving balance in database
     * @param balance param for saving
     * @return saved balance from database
     */
    Balance create(Balance balance);

    /**
     * Getting balance from database
     * @param uuid balance's id
     * @return balance from database
     */
    Balance get(UUID uuid);

    /**
     * Adding a new value in balance. It adds or subtracts value from the old one
     * @param uuid balance's id
     * @param dtUpdate update time for optimistic lock
     * @param value added value to balance
     * @return balance with new value
     */
    Balance countValue(UUID uuid, long dtUpdate, BigDecimal value);

    /**
     * Updating value in balance. It sets new value in balance
     * @param uuid balance's id
     * @param dtUpdate update time for optimistic lock
     * @param value new value for balance
     * @return balance with new value
     */
    Balance updateValue(UUID uuid, long dtUpdate, BigDecimal value);

    /**
     * Deleting balance from database
     * @param uuid balance's id
     * @param dtUpdate update time for optimistic lock
     */
    void delete(UUID uuid, long dtUpdate);

}
