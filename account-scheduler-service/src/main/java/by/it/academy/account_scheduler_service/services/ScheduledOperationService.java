package by.it.academy.account_scheduler_service.services;

import by.it.academy.account_scheduler_service.exception.EssenceDeleteException;
import by.it.academy.account_scheduler_service.exception.EssenceNotFoundException;
import by.it.academy.account_scheduler_service.exception.EssenceUpdateException;
import by.it.academy.account_scheduler_service.models.dto.Operation;
import by.it.academy.account_scheduler_service.models.dto.PageDTO;
import by.it.academy.account_scheduler_service.models.dto.Schedule;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.models.enums.Status;
import by.it.academy.account_scheduler_service.repositories.api.IScheduledOperationRepository;
import by.it.academy.account_scheduler_service.repositories.entities.ScheduledOperationEntity;
import by.it.academy.account_scheduler_service.services.api.IScheduledOperationService;
import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduledOperationService implements IScheduledOperationService {

    private final IScheduledOperationRepository scheduledOperationRepository;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private static final Logger logger = LogManager.getLogger(ScheduledOperationService.class);

    public ScheduledOperationService(IScheduledOperationRepository scheduledOperationRepository,
                                     ConversionService conversionService,
                                     UserHolder userHolder) {
        this.scheduledOperationRepository = scheduledOperationRepository;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public ScheduledOperation create(ScheduledOperation scheduledOperation) {
        if (scheduledOperation.getDtCreate() == null || scheduledOperation.getDtUpdate() == null) {
            LocalDateTime dtNow = LocalDateTime.now();
            scheduledOperation.setDtCreate(dtNow);
            scheduledOperation.setDtUpdate(dtNow);
        }
        scheduledOperation.setStatus(Status.RUN);
        scheduledOperation.setUsername(this.userHolder.getUser().getUsername());
        ScheduledOperationEntity scheduledOperationEntity = this.conversionService.convert(scheduledOperation,
                ScheduledOperationEntity.class);
        if (scheduledOperationEntity != null) {
            scheduledOperationEntity = this.scheduledOperationRepository.save(scheduledOperationEntity);
            logger.info("ScheduledOperation with uuid: {} was create", scheduledOperation.getUuid());
        }
        return this.conversionService.convert(scheduledOperationEntity, ScheduledOperation.class);
    }

    @Override
    public ScheduledOperation get(UUID uuid) {
//        Optional<ScheduledOperationEntity> optionalScheduledOperationEntity = this.scheduledOperationRepository
//                .findByUuidAndUsername(uuid, this.userHolder.getUser().getUsername());
        Optional<ScheduledOperationEntity> optionalScheduledOperationEntity = this.scheduledOperationRepository
                .findById(uuid);
        if (optionalScheduledOperationEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalScheduledOperationEntity.get(), ScheduledOperation.class);
    }

    @Override
    public PageDTO<ScheduledOperation> getPage(Pageable pageable) {
        Page<ScheduledOperationEntity> page = this.scheduledOperationRepository.findAllByUsername(pageable,
                this.userHolder.getUser().getUsername());
        List<ScheduledOperationEntity> scheduledOperationEntityList = page.getContent();
        if (scheduledOperationEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
        }
        List<ScheduledOperation> scheduledOperationList = new ArrayList<>();
        scheduledOperationEntityList.forEach(scheduledOperationEntity -> scheduledOperationList
                .add(this.conversionService.convert(scheduledOperationEntity, ScheduledOperation.class)));
        return PageDTO.Builder.createBuilder(ScheduledOperation.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(Math.toIntExact(page.getTotalElements()))
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(scheduledOperationList)
                .build();
    }

    @Override
    public ScheduledOperation update(UUID uuid, long dtUpdate, ScheduledOperation updatedScheduledOperation) {
        ScheduledOperation scheduledOperation = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        Schedule schedule = updatedScheduledOperation.getSchedule();
        Operation operation = updatedScheduledOperation.getOperation();
        if (schedule != null && !Objects.equals(schedule, scheduledOperation.getSchedule())) {
            scheduledOperation.setSchedule(schedule);
        }
        if (operation != null && !Objects.equals(operation, scheduledOperation.getOperation())) {
            scheduledOperation.setOperation(operation);
        }
        ScheduledOperationEntity scheduledOperationEntity = this.conversionService.convert(scheduledOperation, ScheduledOperationEntity.class);
        if (scheduledOperationEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        scheduledOperationEntity = this.scheduledOperationRepository.save(scheduledOperationEntity);
        logger.info("Schedule operation with uuid: {} was update", scheduledOperationEntity.getUuid());
        return this.conversionService.convert(scheduledOperationEntity, ScheduledOperation.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        ScheduledOperation scheduledOperation = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        ScheduledOperationEntity scheduledOperationEntity = this.conversionService.convert(scheduledOperation, ScheduledOperationEntity.class);
        if (scheduledOperationEntity == null) {
            throw new EssenceDeleteException("Incorrect delete");
        }
        this.scheduledOperationRepository.delete(scheduledOperationEntity);
        logger.info("Schedule operation with uuid: {} was delete", scheduledOperationEntity.getUuid());
    }

    @Override
    public void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        this.updateStatus(uuid, dtUpdate, scheduledOperationMap);
        logger.info("Schedule operation with uuid: {} was stop", uuid);
    }

    @Override
    public void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        this.updateStatus(uuid, dtUpdate, scheduledOperationMap);
        logger.info("Schedule operation with uuid: {} was start", uuid);
    }

    private void updateStatus(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        if (!scheduledOperationMap.containsKey("status")) {
            throw new IllegalArgumentException("Essence doesn't have 'status' field");
        }

        ScheduledOperation scheduledOperation = this.get(uuid);

        if (DateTimeUtil.convertLocalDateTimeToLong(scheduledOperation.getDtUpdate()) != dtUpdate) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        Status status = Status.valueOf((String) scheduledOperationMap.get("status"));
        ScheduledOperationEntity scheduledOperationEntity = this.conversionService.convert(scheduledOperation,
                ScheduledOperationEntity.class);
        if (!Objects.equals(status, scheduledOperation.getStatus()) && scheduledOperationEntity != null) {
            scheduledOperationEntity.setStatus(status);
            this.scheduledOperationRepository.save(scheduledOperationEntity);
            logger.info("Status for scheduled operation with uuid {} was update", uuid);
        } else {
            throw new EssenceUpdateException("Nothing to update");
        }
    }

}
