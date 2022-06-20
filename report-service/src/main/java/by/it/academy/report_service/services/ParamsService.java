package by.it.academy.report_service.services;

import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Currency;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;
import by.it.academy.report_service.services.api.IParamsService;
import by.it.academy.report_service.services.api.IRequestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ParamsService implements IParamsService {

    private final IRequestService requestService;

    public ParamsService(IRequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public OperationCategory getOperationCategory(List<OperationCategory> operationCategoryList, UUID uuid) {
        for (OperationCategory operationCategory : operationCategoryList) {
            if (operationCategory.getUuid().compareTo(uuid) == 0) {
                return operationCategory;
            }
        }
        return new OperationCategory();
    }

    @Override
    public Map<UUID, Map<UUID, List<Operation>>> groupByCategories(Map<UUID, List<Operation>> operationMap) {
        Map<UUID, Map<UUID, List<Operation>>> resultMap = new HashMap<>();
        for (Map.Entry<UUID, List<Operation>> entry : operationMap.entrySet()) {
           Map<UUID, List<Operation>> groupedMap = entry.getValue().stream().collect(Collectors.groupingBy(Operation::getCategory));
           resultMap.put(entry.getKey(), groupedMap);
        }
        return resultMap;
    }

    @Override
    public Map<UUID, Map<LocalDate, List<Operation>>> groupByDate(Map<UUID, List<Operation>> operationMap) {
        Map<UUID, Map<LocalDate, List<Operation>>> resultMap = new HashMap<>();
        for (Map.Entry<UUID, List<Operation>> entry : operationMap.entrySet()) {
            Map<LocalDate, List<Operation>> groupedMap = entry.getValue().stream().collect(Collectors.groupingBy(operation -> operation.getDate().toLocalDate()));
            resultMap.put(entry.getKey(), groupedMap);
        }
        return resultMap;
    }

    @Override
    public Map<UUID, List<Operation>> filterOperationList(Map<UUID, List<Operation>> operationMap,
                                                          List<Account> accountList, Predicate<Operation> operationPredicate) {
        Map<UUID, List<Operation>> resultMap = new HashMap<>();
        for (Account account : accountList) {
            List<Operation> operations = operationMap.get(account.getUuid()).stream()
                    .filter(operationPredicate)
                    .collect(Collectors.toList());
            resultMap.put(account.getUuid(), operations);
        }
        return resultMap;
    }

    @Override
    public List<UUID> convertStringListToUUID(List<String> stringList) {
        List<UUID> uuidList = new ArrayList<>();
        if (stringList != null) {
            stringList.forEach(s -> uuidList.add(UUID.fromString(s)));
        }
        return uuidList;
    }

    @Override
    public List<Account> getAccountList(List<UUID> uuidList, String username) {
        List<Account> accountList = new ArrayList<>();
        if (uuidList != null) {
            uuidList.forEach(uuid -> accountList.add(this.requestService.getAccount(uuid, username)));
        }
        return accountList;
    }

    @Override
    public Map<UUID, List<Operation>> getOperationMap(List<UUID> uuidList, String username) {
        Map<UUID, List<Operation>> operationMap = new HashMap<>();
        if (uuidList != null) {
            uuidList.forEach(uuid -> operationMap.put(uuid, this.requestService.getOperations(uuid, username)));
        }
        return operationMap;
    }

    @Override
    public Map<UUID, Currency> getCurrencyMap(List<Account> accountList) {
        Map<UUID, Currency> currencyMap = new HashMap<>();
        if (accountList != null) {
            accountList.forEach(account -> currencyMap.put(account.getCurrency(),
                    this.requestService.getCurrency(account.getCurrency())));
        }
        return currencyMap;
    }

    @Override
    public List<OperationCategory> getOperationCategoryList() {
        return this.requestService.getAllOperationCategory();
    }

    @Override
    public BigDecimal sumOperationListValue(List<Operation> operationList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Operation operation : operationList) {
            sum = operation.getValue().add(sum);
        }
        return sum;
    }
}
