package by.it.academy.report_service.services.api;

import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Currency;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public interface IParamsService {

    /**
     * Getting operation category by uuid
     * @param operationCategoryList list of operation categories
     * @param uuid uuid defined operation category
     * @return operation category
     */
    OperationCategory getOperationCategory(List<OperationCategory> operationCategoryList, UUID uuid);

    /**
     * Grouping operations by categories
     * @param operationMap map where key is account's uuid, value is list of operations
     * @return map where key is account's uuid, value is map whose key is category's uuid and value is list of operations
     */
    Map<UUID, Map<UUID, List<Operation>>> groupByCategories(Map<UUID, List<Operation>> operationMap);

    /**
     * Grouping by date
     * @param operationMap map where key is account's uuid, value is list of operations
     * @return map where key is account's uuid, value is map whose key is date and value is list of operations
     */
    Map<UUID, Map<LocalDate, List<Operation>>> groupByDate(Map<UUID, List<Operation>> operationMap);

    /**
     * Filtering list of operations by predicate
     * @param operationMap map where key is account uuid, value is list of operations
     * @param accountList list of accounts
     * @param operationPredicate predicate for filtering by date, by categories
     * @see by.it.academy.report_service.services.api.predicates.OperationCategoryPredicate
     * @see by.it.academy.report_service.services.api.predicates.OperationDatePredicate
     * @return map where key is account's uuid, value is list of operations
     */
    Map<UUID, List<Operation>> filterOperationList(Map<UUID, List<Operation>> operationMap, List<Account> accountList,
                                                   Predicate<Operation> operationPredicate);

    /**
     * Converting string list to uuid list
     * @param stringList list of strings
     * @return list of uuids
     */
    List<UUID> convertStringListToUUID(List<String> stringList);

    /**
     * Getting list of accounts
     * @param uuidList list of uuids
     * @param username user's username
     * @return list of accounts
     */
    List<Account> getAccountList(List<UUID> uuidList, String username);

    /**
     * Getting list of operation map
     * @param uuidList list of account's uuids
     * @return map where key is account's uuid, value is list of operations
     */
    Map<UUID, List<Operation>> getOperationMap(List<UUID> uuidList, String username);

    /**
     * Getting currencies map
     * @param accountList list of accounts
     * @return map where key is currency's uuid, value is currency
     */
    Map<UUID, Currency> getCurrencyMap(List<Account> accountList);

    /**
     * Getting list if operation categories
     * @return list if operation categories
     */
    List<OperationCategory> getOperationCategoryList();

    /**
     * Getting the sum of operations from the list
     * @param operationList list of operations
     * @return sum of operations
     */
    BigDecimal sumOperationListValue(List<Operation> operationList);

}
