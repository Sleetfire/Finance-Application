package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.EssenceDeleteException;
import by.it.academy.account_service.exception.EssenceNotFoundException;
import by.it.academy.account_service.exception.EssenceUpdateException;
import by.it.academy.account_service.exception.ValidationException;
import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.models.enums.AccountType;
import by.it.academy.account_service.models.errors.ValidationError;
import by.it.academy.account_service.repositories.api.IAccountRepository;
import by.it.academy.account_service.repositories.entities.AccountEntity;
import by.it.academy.account_service.services.api.IAccountService;
import by.it.academy.account_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private static final Logger logger = LogManager.getLogger(AccountService.class);

    public AccountService(IAccountRepository accountRepository, ConversionService conversionService, UserHolder userHolder) {
        this.accountRepository = accountRepository;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public Account create(Account account) {
        this.checkAccount(account);
        LocalDateTime dtNow = LocalDateTime.now();
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        if (accountEntity != null) {
            accountEntity.setDtCreate(dtNow);
            accountEntity.setDtUpdate(dtNow);
            accountEntity.setUsername(userHolder.getUser().getUsername());
            accountEntity = this.accountRepository.save(accountEntity);
            logger.info("Account with title {} and uuid {} was create", accountEntity.getTitle(),
                    accountEntity.getUuid());
        }
        return this.conversionService.convert(accountEntity, Account.class);
    }

    @Override
    public PageDTO<Account> getPage(Pageable pageable) {
        Page<AccountEntity> accountEntityPage = this.accountRepository.findAllByUsername(pageable,
                this.userHolder.getUser().getUsername());
        List<AccountEntity> accountEntityList = accountEntityPage.getContent();
        List<Account> accounts = new ArrayList<>();
        if (accountEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Entities are not exist");
        }
        accountEntityList.forEach(accountEntity -> accounts.add(this.conversionService.convert(accountEntity,
                Account.class)));
        return PageDTO.Builder.createBuilder(Account.class)
                .setNumber(accountEntityPage.getNumber())
                .setSize(accountEntityPage.getSize())
                .setTotalPages(accountEntityPage.getTotalPages())
                .setTotalElements(Math.toIntExact(accountEntityPage.getTotalElements()))
                .setFirst(accountEntityPage.isFirst())
                .setNumberOfElements(accountEntityPage.getNumberOfElements())
                .setLast(accountEntityPage.isLast())
                .setContent(accounts)
                .build();
    }

    @Override
    public Account get(UUID uuid) {
        Optional<AccountEntity> optionalAccountEntity = this.accountRepository.findByUuidAndUsername(uuid,
                this.userHolder.getUser().getUsername());
        if (optionalAccountEntity.isEmpty()) {
            throw new EssenceNotFoundException("Entity is not exist");
        }
        return this.conversionService.convert(optionalAccountEntity.get(), Account.class);
    }

    @Override
    public Account update(UUID uuid, long dtUpdate, Account updatedAccount) {
        this.checkAccount(updatedAccount);
        Account account = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(account.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }

        String title = updatedAccount.getTitle();
        String description = updatedAccount.getDescription();
        AccountType type = updatedAccount.getType();
        UUID currency = updatedAccount.getCurrency();

        if (!Objects.equals(title, account.getTitle())) {
            account.setTitle(title);
        }
        if (!Objects.equals(description, account.getDescription())) {
            account.setDescription(description);
        }
        if (!Objects.equals(type, account.getType())) {
            account.setType(type);
        }
        if (!Objects.equals(currency, account.getCurrency())) {
            account.setCurrency(currency);
        }
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        if (accountEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        accountEntity = this.accountRepository.save(accountEntity);
        logger.info("Account with uuid {} was update", accountEntity.getUuid());
        return this.conversionService.convert(accountEntity, Account.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        Account account = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(account.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        if (accountEntity != null) {
            this.accountRepository.delete(accountEntity);
            logger.info("Account with uuid {} was delete", accountEntity.getUuid());
        } else {
            throw new EssenceDeleteException("Incorrect delete");
        }
    }

    private void checkAccount(Account account) {
        List<ValidationError> errors = new ArrayList<>();
        if (account.getTitle() == null || account.getTitle().isEmpty()) {
            errors.add(new ValidationError("title", "Title is null or empty"));
        }
        if (account.getDescription() == null || account.getDescription().isEmpty()) {
            errors.add(new ValidationError("description", "Description is null or empty"));
        }
        if (account.getType() == null) {
            errors.add(new ValidationError("type", "Type is null"));
        }
        if (account.getCurrency() == null) {
            errors.add(new ValidationError("currency", "Currency is null"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Account validation error", errors);
        }
    }

}
