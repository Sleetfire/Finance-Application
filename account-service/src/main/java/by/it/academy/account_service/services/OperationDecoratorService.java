package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.ValidationException;
import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.Balance;
import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.models.errors.ValidationError;
import by.it.academy.account_service.services.api.IAccountService;
import by.it.academy.account_service.services.api.IOperationService;
import by.it.academy.account_service.services.api.IValidateClassifiersService;
import by.it.academy.account_service.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OperationDecoratorService {

    private final IAccountService accountService;
    private final IOperationService operationService;
    private final BalanceService balanceService;
    private final IValidateClassifiersService validateClassifiersService;

    public OperationDecoratorService(@Qualifier("accountService") IAccountService accountService,
                                     @Qualifier("operationService") IOperationService operationService,
                                     @Qualifier("balanceService") BalanceService balanceService,
                                     IValidateClassifiersService validateClassifiersService) {
        this.accountService = accountService;
        this.operationService = operationService;
        this.balanceService = balanceService;
        this.validateClassifiersService = validateClassifiersService;
    }

    @Transactional
    public Operation create(Operation operation, UUID accountId) {
        this.checkClassifiers(operation);
        Account account = this.accountService.get(accountId);
        Balance balance = account.getBalance();
        balanceService.countValue(balance.getUuid(), DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate()),
                operation.getValue());
        return this.operationService.create(operation, account);
    }

    public PageDTO<Operation> getPage(UUID accountId, Pageable pageable) {
        Account account = this.accountService.get(accountId);
        return this.operationService.getPage(account, pageable);
    }

    public Operation get(UUID uuid) {
        return this.operationService.get(uuid);
    }

    public List<Operation> getAll(UUID uuid) {
        Account account = this.accountService.get(uuid);
        return this.operationService.getAll(account);
    }

    @Transactional
    public Operation update(UUID uuid, long dtUpdate, Operation updatedOperation) {
        Operation operation = this.operationService.get(uuid);
        Account account = operation.getAccount();
        Balance balance = account.getBalance();
        BigDecimal valueFromBalance = balance.getValue();
        BigDecimal mediumValue = valueFromBalance.subtract(operation.getValue());
        BigDecimal finalValue = mediumValue.add(updatedOperation.getValue());
        this.balanceService.updateValue(balance.getUuid(), DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate()),
                finalValue);
        return this.operationService.update(uuid, dtUpdate, updatedOperation);
    }

    @Transactional
    public void delete(UUID uuid, long dtUpdate, UUID accountUuid) {
        Operation operation = this.operationService.get(uuid);
        Account account = this.accountService.get(accountUuid);
        Balance balance = account.getBalance();
        BigDecimal valueFromBalance = balance.getValue();
        BigDecimal mediumValue = valueFromBalance.subtract(operation.getValue());
        this.balanceService.updateValue(balance.getUuid(), DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate()),
                mediumValue);
        this.operationService.delete(uuid, dtUpdate);
    }

    @Transactional
    public void deleteByAccount(Account account, long dtUpdate) {
        this.operationService.deleteByAccount(account, dtUpdate);
    }

    private void checkClassifiers(Operation operation) {
        List<ValidationError> errors = new ArrayList<>();
        if (!this.validateClassifiersService.isCurrencyExist(operation.getCurrency())) {
            errors.add(new ValidationError("currency", "this currency is not exist"));
        }
        if (!this.validateClassifiersService.isOperationCategoryExist(operation.getCategory())) {
            errors.add(new ValidationError("category", "this category is not exist"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Operation validation error", errors);
        }
    }

}
