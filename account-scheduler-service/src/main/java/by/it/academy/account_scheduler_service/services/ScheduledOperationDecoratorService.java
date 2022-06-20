package by.it.academy.account_scheduler_service.services;

import by.it.academy.account_scheduler_service.exception.ValidationException;
import by.it.academy.account_scheduler_service.models.dto.Operation;
import by.it.academy.account_scheduler_service.models.dto.PageDTO;
import by.it.academy.account_scheduler_service.models.dto.Schedule;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.models.errors.ValidationError;
import by.it.academy.account_scheduler_service.services.api.IOperationService;
import by.it.academy.account_scheduler_service.services.api.IValidateClassifiersService;
import by.it.academy.account_scheduler_service.services.scheduler.api.IQuartzSchedulerService;
import by.it.academy.account_scheduler_service.services.api.IScheduleService;
import by.it.academy.account_scheduler_service.services.api.IScheduledOperationService;
import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ScheduledOperationDecoratorService implements IScheduledOperationService {

    private final IScheduledOperationService scheduledOperationService;
    private final IScheduleService scheduleService;
    private final IOperationService operationService;
    private final IQuartzSchedulerService quartzService;
    private final IValidateClassifiersService validateClassifiersService;

    public ScheduledOperationDecoratorService(@Qualifier("scheduledOperationService") IScheduledOperationService scheduledOperationService,
                                              IScheduleService scheduleService,
                                              IOperationService operationService,
                                              IQuartzSchedulerService quartzService,
                                              IValidateClassifiersService validateClassifiersService) {
        this.scheduledOperationService = scheduledOperationService;
        this.scheduleService = scheduleService;
        this.operationService = operationService;
        this.quartzService = quartzService;
        this.validateClassifiersService = validateClassifiersService;
    }

    @Override
    @Transactional
    public ScheduledOperation create(ScheduledOperation scheduledOperation) {
        this.check(scheduledOperation);
        Schedule schedule = scheduledOperation.getSchedule();
        Operation operation = scheduledOperation.getOperation();
        this.checkClassifiers(operation);

        LocalDateTime dtNow = LocalDateTime.now();
        schedule.setDtCreate(dtNow);
        schedule.setDtUpdate(dtNow);
        operation.setDtCreate(dtNow);
        operation.setDtUpdate(dtNow);
        scheduledOperation.setDtCreate(dtNow);
        scheduledOperation.setDtUpdate(dtNow);

        schedule = this.scheduleService.create(schedule);
        operation = this.operationService.create(operation);

        scheduledOperation.setSchedule(schedule);
        scheduledOperation.setOperation(operation);

        scheduledOperation = this.scheduledOperationService.create(scheduledOperation);
        this.quartzService.create(scheduledOperation);
        return scheduledOperation;
    }

    @Override
    public ScheduledOperation get(UUID uuid) {
        return this.scheduledOperationService.get(uuid);
    }

    @Override
    public PageDTO<ScheduledOperation> getPage(Pageable pageable) {
        return this.scheduledOperationService.getPage(pageable);
    }

    @Override
    @Transactional
    public ScheduledOperation update(UUID uuid, long dtUpdate, ScheduledOperation updatedScheduledOperation) {
        this.check(updatedScheduledOperation);
        this.quartzService.delete(uuid);
        Schedule updatedSchedule = updatedScheduledOperation.getSchedule();
        Operation updatedOperation = updatedScheduledOperation.getOperation();
        ScheduledOperation scheduledOperation = this.scheduledOperationService.get(uuid);
        long scheduleDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getSchedule().getDtUpdate());
        long operationDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getOperation().getDtUpdate());

        updatedSchedule = this.scheduleService.update(scheduledOperation.getSchedule().getUuid(), scheduleDtUpdate, updatedSchedule);
        updatedOperation = this.operationService.update(scheduledOperation.getOperation().getUuid(), operationDtUpdate, updatedOperation);
        updatedScheduledOperation.setSchedule(updatedSchedule);
        updatedScheduledOperation.setOperation(updatedOperation);

        updatedScheduledOperation = this.scheduledOperationService.update(uuid, dtUpdate, updatedScheduledOperation);
        this.quartzService.create(updatedScheduledOperation);
        return updatedScheduledOperation;
    }

    @Override
    @Transactional
    public void delete(UUID uuid, long dtUpdate) {
        this.quartzService.delete(uuid);
        ScheduledOperation scheduledOperation = this.scheduledOperationService.get(uuid);
        long scheduleDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getSchedule().getDtUpdate());
        long operationDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getOperation().getDtUpdate());
        this.scheduledOperationService.delete(uuid, dtUpdate);
        this.scheduleService.delete(scheduledOperation.getSchedule().getUuid(), scheduleDtUpdate);
        this.operationService.delete(scheduledOperation.getOperation().getUuid(), operationDtUpdate);
    }

    @Override
    @Transactional
    public void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        this.quartzService.delete(uuid);
        this.scheduledOperationService.stop(uuid, dtUpdate, scheduledOperationMap);
    }

    @Override
    @Transactional
    public void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        this.scheduledOperationService.start(uuid, dtUpdate, scheduledOperationMap);
        ScheduledOperation scheduledOperation = this.scheduledOperationService.get(uuid);
        this.quartzService.create(scheduledOperation);
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

    private void check(ScheduledOperation scheduledOperation) {
        List<ValidationError> errors = new ArrayList<>();
        if (scheduledOperation.getSchedule() == null) {
            errors.add(new ValidationError("schedule", "schedule is null"));
        }
        if (scheduledOperation.getOperation() == null) {
            errors.add(new ValidationError("operation", "operation is null"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Operation validation error", errors);
        }
    }



}
