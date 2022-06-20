package by.it.academy.account_scheduler_service.services;

import by.it.academy.account_scheduler_service.exception.EssenceDeleteException;
import by.it.academy.account_scheduler_service.exception.EssenceNotFoundException;
import by.it.academy.account_scheduler_service.exception.EssenceUpdateException;
import by.it.academy.account_scheduler_service.exception.ValidationException;
import by.it.academy.account_scheduler_service.models.dto.Operation;
import by.it.academy.account_scheduler_service.models.errors.ValidationError;
import by.it.academy.account_scheduler_service.repositories.api.IOperationRepository;
import by.it.academy.account_scheduler_service.repositories.entities.OperationEntity;
import by.it.academy.account_scheduler_service.services.api.IOperationService;
import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OperationService implements IOperationService {

    private final IOperationRepository operationRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(OperationService.class);

    public OperationService(IOperationRepository operationRepository, ConversionService conversionService) {
        this.operationRepository = operationRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Operation create(Operation operation) {
        this.checkOperation(operation);
        if (operation.getDtCreate() == null || operation.getDtUpdate() == null) {
            LocalDateTime dtNow = LocalDateTime.now();
            operation.setDtCreate(dtNow);
            operation.setDtUpdate(dtNow);
        }
        OperationEntity operationEntity = this.conversionService.convert(operation, OperationEntity.class);
        if (operationEntity != null) {
            operationEntity = this.operationRepository.save(operationEntity);
            logger.info("Operation with uuid: {} was created", operationEntity.getUuid());
        }
        return this.conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public Operation get(UUID uuid) {
        Optional<OperationEntity> optionalOperation = this.operationRepository.findById(uuid);
        if (optionalOperation.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalOperation.get(), Operation.class);
    }

    @Override
    public List<Operation> getAll() {
        List<OperationEntity> operationEntityList = this.operationRepository.findAll();
        if (operationEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
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
        UUID account = updatedOperation.getAccount();
        String description = updatedOperation.getDescription();
        UUID category = updatedOperation.getCategory();
        BigDecimal value = updatedOperation.getValue();
        UUID currency = updatedOperation.getCurrency();
        if (!Objects.equals(account, operation.getAccount())) {
            operation.setAccount(account);
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
        logger.info("Operation with uuid {} was updated", operation.getUuid());
        return this.conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        Operation operation = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(operation.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        OperationEntity operationEntity = this.conversionService.convert(operation, OperationEntity.class);
        if (operationEntity == null) {
            throw new EssenceDeleteException("Incorrect delete");
        }
        this.operationRepository.delete(operationEntity);
        logger.info("Operation with uuid {} was delete", operationEntity.getUuid());
    }

    private void checkOperation(Operation operation) {
        List<ValidationError> errors = new ArrayList<>();
        if (operation.getAccount() == null) {
            errors.add(new ValidationError("account", "Account is null"));
        }
        if (operation.getDescription() == null) {
            errors.add(new ValidationError("description", "Description is null"));
        }
        if (operation.getCategory() == null) {
            errors.add(new ValidationError("category", "Category is null"));
        }
        if (operation.getValue() == null) {
            errors.add(new ValidationError("value", "Value is null"));
        }
        if (operation.getCurrency() == null) {
            errors.add(new ValidationError("currency", "Currency is null"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

}
