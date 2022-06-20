package by.it.academy.report_service.services.api;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Currency;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;

import java.util.List;
import java.util.UUID;

public interface IRequestService {

    /**
     * Sending request to get account from account-service
     * @param uuid account's uuid
     * @param username user's username
     * @return account
     */
    Account getAccount(UUID uuid, String username);

    /**
     * Sending request to get list of operations from account-service
     * @param uuid account's uuid
     * @param username user's username
     * @return list of operations
     */
    List<Operation> getOperations(UUID uuid, String username);

    /**
     * Sending request to get currency from classifier-service
     * @param uuid currency's uuid
     * @return currency
     */
    Currency getCurrency(UUID uuid);

    /**
     * Sending request to get operation category from classifier-service
     * @param uuid operation category's uuid
     * @return operation category
     */
    OperationCategory getOperationCategory(UUID uuid);

    /**
     * Sending request to get all operation's categories from classifier-service
     * @return list of operation categories
     */
    List<OperationCategory> getAllOperationCategory();

    /**
     * Sending report to mail
     * @param report report dto
     */
    void sendReportToMail(IReport report);

}
