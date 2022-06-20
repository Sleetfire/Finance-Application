package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.*;
import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.models.errors.ValidationError;
import by.it.academy.account_service.repositories.api.IOperationRepository;
import by.it.academy.account_service.repositories.entities.AccountEntity;
import by.it.academy.account_service.repositories.entities.OperationEntity;
import by.it.academy.account_service.services.api.IOperationService;
import by.it.academy.account_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OperationService implements IOperationService {

    private final IOperationRepository operationRepository;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private static final Logger logger = LogManager.getLogger(OperationService.class);

    public OperationService(IOperationRepository operationRepository, ConversionService conversionService,
                            UserHolder userHolder) {
        this.operationRepository = operationRepository;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public Operation create(Operation operation, Account account) {
        this.checkOperation(operation);
        if (operation.getCurrency().compareTo(account.getCurrency()) != 0) {
            throw new IncorrectInputParametersException("Operation and account currency must match");
        }
        operation.setAccount(account);
        operation.setUsername(this.userHolder.getUser().getUsername());
        LocalDateTime dtNow = LocalDateTime.now();
        OperationEntity operationEntity = this.conversionService.convert(operation, OperationEntity.class);
        if (operationEntity != null) {
            operationEntity.setDtCreate(dtNow);
            operationEntity.setDtUpdate(dtNow);
            operationEntity = this.operationRepository.save(operationEntity);
            logger.info("Operation with uuid {} was create", operationEntity.getUuid());

        }
        return this.conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public PageDTO<Operation> getPage(Account account, Pageable pageable) {
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        Page<OperationEntity> operationEntityPage = this.operationRepository
                .findByAccountEntityAndUsername(accountEntity, this.userHolder.getUser().getUsername(), pageable);
        List<OperationEntity> operationEntityList = operationEntityPage.getContent();
        if (operationEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Entities are not exist");
        }
        List<Operation> operations = new ArrayList<>();
        operationEntityList.forEach(operationEntity -> operations.add(this.conversionService.convert(operationEntity,
                Operation.class)));
        return PageDTO.Builder.createBuilder(Operation.class)
                .setNumber(operationEntityPage.getNumber())
                .setSize(operationEntityPage.getSize())
                .setTotalPages(operationEntityPage.getTotalPages())
                .setTotalElements(Math.toIntExact(operationEntityPage.getTotalElements()))
                .setFirst(operationEntityPage.isFirst())
                .setNumberOfElements(operationEntityPage.getNumberOfElements())
                .setLast(operationEntityPage.isLast())
                .setContent(operations)
                .build();
    }

    @Override
    public Operation get(UUID uuid) {
        Optional<OperationEntity> optionalOperationEntity = this.operationRepository.findByUuidAndUsername(uuid,
                this.userHolder.getUser().getUsername());
        if (optionalOperationEntity.isEmpty()) {
            throw new EssenceNotFoundException("Entity is not exist");
        }
        return this.conversionService.convert(optionalOperationEntity.get(), Operation.class);
    }

    @Override
    public List<Operation> getAll(Account account) {
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        List<OperationEntity> operationEntityList = this.operationRepository
                .findAllByAccountEntityAndUsername(accountEntity, this.userHolder.getUser().getUsername());
        if (operationEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        List<Operation> operationList = new ArrayList<>();
        operationEntityList.forEach(operationEntity -> operationList.add(this.conversionService.convert(operationEntity,
                Operation.class)));
        return operationList;
    }

    @Override
    public Operation update(UUID uuid, long dtUpdate, Operation updatedOperation) {
        this.checkOperation(updatedOperation);
        Operation operation = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(operation.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        LocalDateTime date = updatedOperation.getDate();
        String description = updatedOperation.getDescription();
        UUID category = updatedOperation.getCategory();
        BigDecimal value = updatedOperation.getValue();
        UUID currency = updatedOperation.getCurrency();

        if (date.compareTo(operation.getDate()) != 0) {
            operation.setDate(date);
        }
        if (!Objects.equals(description, operation.getDescription())) {
            operation.setDescription(description);
        }
        if (!Objects.equals(category, operation.getCategory())) {
            operation.setCategory(category);
        }
        if (value.compareTo(operation.getValue()) != 0) {
            operation.setValue(value);
        }
        if (!Objects.equals(currency, operation.getCurrency())) {
            operation.setCurrency(currency);
        }
        OperationEntity operationEntity = this.conversionService.convert(operation, OperationEntity.class);
        if (operationEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        operationEntity = this.operationRepository.save(operationEntity);
        logger.info("Operation with uuid {} was update", operationEntity.getUuid());
        return this.conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        Operation operation = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(operation.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        OperationEntity operationEntity = this.conversionService.convert(operation, OperationEntity.class);
        if (operationEntity != null) {
            this.operationRepository.delete(operationEntity);
            logger.info("Operation with uuid {} was delete", operationEntity.getUuid());
        } else {
            throw new EssenceDeleteException("Incorrect delete");
        }
    }

    @Override
    public void deleteByAccount(Account account, long dtUpdate) {
        AccountEntity accountEntity = this.conversionService.convert(account, AccountEntity.class);
        List<OperationEntity> operationEntityList = this.operationRepository
                .findAllByAccountEntityAndUsername(accountEntity, this.userHolder.getUser().getUsername());
        if (operationEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Entities are not exist");
        }
        this.operationRepository
                .deleteAllByAccountEntityAndUsername(accountEntity, this.userHolder.getUser().getUsername());
    }

    @Override
    public long getCountByAccountUUID(UUID accountUUID) {
        return this.operationRepository.countOperationEntitiesByAccountEntity_UuidAndUsername(accountUUID,
                this.userHolder.getUser().getUsername());
    }

    /**
     * Validation check
     *
     * @param operation account's operation
     */
    private void checkOperation(Operation operation) {
        List<ValidationError> errors = new ArrayList<>();
        if (operation.getDate() == null) {
            errors.add(new ValidationError("date", "Date is null"));
        }
        if (operation.getDescription() == null || operation.getDescription().isEmpty()) {
            errors.add(new ValidationError("description", "Description is null or empty"));
        }
        if (operation.getCategory() == null || operation.getCurrency().toString().isEmpty()) {
            errors.add(new ValidationError("category", "Category is null or empty"));
        }
        if (operation.getValue() == null) {
            errors.add(new ValidationError("value", "Value is null"));
        }
        if (operation.getCurrency() == null || operation.getCurrency().toString().isEmpty()) {
            errors.add(new ValidationError("currency", "Currency is null or empty"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Operation validation error", errors);
        }
    }

}
