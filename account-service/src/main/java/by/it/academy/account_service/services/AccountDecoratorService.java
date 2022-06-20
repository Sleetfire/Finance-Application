package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.EssenceDeleteException;
import by.it.academy.account_service.exception.IncorrectInputParametersException;
import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.Balance;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.services.api.IAccountService;
import by.it.academy.account_service.services.api.IBalanceService;
import by.it.academy.account_service.services.api.IOperationService;
import by.it.academy.account_service.services.api.IValidateClassifiersService;
import by.it.academy.account_service.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountDecoratorService implements IAccountService {

    private final IAccountService accountService;
    private final IOperationService operationService;
    private final IBalanceService balanceService;
    private final IValidateClassifiersService validateClassifiersService;

    public AccountDecoratorService(@Qualifier("accountService") IAccountService accountService,
                                   @Qualifier("operationService") IOperationService operationService,
                                   @Qualifier("balanceService") BalanceService balanceService,
                                   IValidateClassifiersService validateClassifiersService) {
        this.accountService = accountService;
        this.operationService = operationService;
        this.balanceService = balanceService;
        this.validateClassifiersService = validateClassifiersService;
    }

    @Override
    @Transactional
    public Account create(Account account) {
        if (!this.validateClassifiersService.isCurrencyExist(account.getCurrency())) {
            throw new IncorrectInputParametersException("Currency does not exist");
        }
        Balance balance = new Balance();
        balance = this.balanceService.create(balance);
        account.setBalance(balance);
        return this.accountService.create(account);
    }

    @Override
    public PageDTO<Account> getPage(Pageable pageable) {
        return this.accountService.getPage(pageable);
    }

    @Override
    public Account get(UUID uuid) {
        return this.accountService.get(uuid);
    }

    @Override
    @Transactional
    public Account update(UUID uuid, long dtUpdate, Account updatedAccount) {
        return this.accountService.update(uuid, dtUpdate, updatedAccount);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, long dtUpdate) {
        Account account = this.accountService.get(uuid);
        UUID balanceUUId = account.getBalance().getUuid();
        LocalDateTime balanceDt = account.getBalance().getDtUpdate();
        if (this.operationService.getCountByAccountUUID(uuid) > 0) {
            throw new EssenceDeleteException("This entity is referenced by other entities: check operations");
        }
        this.accountService.delete(uuid, dtUpdate);
        this.balanceService.delete(balanceUUId, DateTimeUtil.convertLocalDateTimeToLong(balanceDt));
    }

}
