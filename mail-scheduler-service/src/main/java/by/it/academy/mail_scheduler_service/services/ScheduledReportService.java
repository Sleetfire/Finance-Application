package by.it.academy.mail_scheduler_service.services;

import by.it.academy.mail_scheduler_service.exceptions.EssenceDeleteException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceNotFoundException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceUpdateException;
import by.it.academy.mail_scheduler_service.model.dto.PageDTO;
import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.model.enums.Status;
import by.it.academy.mail_scheduler_service.repositories.api.IScheduledReportRepository;
import by.it.academy.mail_scheduler_service.repositories.entities.ScheduledReportEntity;
import by.it.academy.mail_scheduler_service.services.api.IScheduleReportService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduledReportService implements IScheduleReportService {

    private final IScheduledReportRepository scheduledReportRepository;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private static final Logger logger = LogManager.getLogger(ScheduledReportService.class);

    public ScheduledReportService(IScheduledReportRepository scheduledReportRepository,
                                  ConversionService conversionService, UserHolder userHolder) {
        this.scheduledReportRepository = scheduledReportRepository;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public ScheduledReport create(ScheduledReport scheduledReport) {
        if (scheduledReport.getDtCreate() == null || scheduledReport.getDtUpdate() == null) {
            LocalDateTime dtNow = LocalDateTime.now();
            scheduledReport.setDtCreate(dtNow);
            scheduledReport.setDtUpdate(dtNow);
        }
        scheduledReport.setStatus(Status.RUN);
        scheduledReport.setUsername(this.userHolder.getUser().getUsername());
        ScheduledReportEntity scheduledReportEntity = this.conversionService.convert(scheduledReport,
                ScheduledReportEntity.class);
        if (scheduledReportEntity != null) {
            scheduledReportEntity = this.scheduledReportRepository.save(scheduledReportEntity);
            logger.info("Scheduled report with uuid: {} was create", scheduledReportEntity.getUuid());
        }
        return this.conversionService.convert(scheduledReportEntity, ScheduledReport.class);
    }

    @Override
    public ScheduledReport get(UUID uuid) {
        Optional<ScheduledReportEntity> optionalScheduledReportEntity = this.scheduledReportRepository
                .findById(uuid);
        if (optionalScheduledReportEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalScheduledReportEntity.get(), ScheduledReport.class);
    }

    @Override
    public PageDTO<ScheduledReport> getPage(Pageable pageable) {
        Page<ScheduledReportEntity> page = this.scheduledReportRepository.findAllByUsername(pageable,
                this.userHolder.getUser().getUsername());
        List<ScheduledReportEntity> scheduledReportEntityList = page.getContent();
        if (scheduledReportEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
        }
        List<ScheduledReport> scheduledOperationList = new ArrayList<>();
        scheduledReportEntityList.forEach(scheduledOperationEntity -> scheduledOperationList
                .add(this.conversionService.convert(scheduledOperationEntity, ScheduledReport.class)));
        return PageDTO.Builder.createBuilder(ScheduledReport.class)
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
    public ScheduledReport update(UUID uuid, long dtUpdate, ScheduledReport updatedScheduledReport) {
        ScheduledReport scheduledReport = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }

        Report report = updatedScheduledReport.getReport();

        if (report != null && !Objects.equals(report, scheduledReport.getReport())) {
            scheduledReport.setReport(report);
        }
        ScheduledReportEntity scheduledReportEntity = this.conversionService.convert(scheduledReport, ScheduledReportEntity.class);
        if (scheduledReportEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        scheduledReportEntity = this.scheduledReportRepository.save(scheduledReportEntity);
        logger.info("Scheduled report with uuid: {} was update", scheduledReportEntity.getUuid());
        return this.conversionService.convert(scheduledReportEntity, ScheduledReport.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        ScheduledReport scheduledReport = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        ScheduledReportEntity scheduledReportEntity = this.conversionService.convert(scheduledReport, ScheduledReportEntity.class);
        if (scheduledReportEntity == null) {
            throw new EssenceDeleteException("Incorrect delete");
        }
        this.scheduledReportRepository.delete(scheduledReportEntity);
        logger.info("Scheduled report with uuid: {} was delete", scheduledReportEntity.getUuid());
    }

    @Override
    public void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap) {
        this.updateStatus(uuid, dtUpdate, scheduledReportMap);
    }

    @Override
    public void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap) {
        this.updateStatus(uuid, dtUpdate, scheduledReportMap);
    }

    private void updateStatus(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap) {
        if (!scheduledOperationMap.containsKey("status")) {
            throw new IllegalArgumentException("Essence doesn't have 'status' field");
        }

        ScheduledReport scheduledReport = this.get(uuid);

        if (DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getDtUpdate()) != dtUpdate) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        Status status = Status.valueOf((String) scheduledOperationMap.get("status"));
        ScheduledReportEntity scheduledReportEntity = this.conversionService.convert(scheduledReport,
                ScheduledReportEntity.class);
        if (!Objects.equals(status, scheduledReport.getStatus()) && scheduledReportEntity != null) {
            scheduledReportEntity.setStatus(status);
            this.scheduledReportRepository.save(scheduledReportEntity);
        } else {
            throw new EssenceUpdateException("Nothing to update");
        }
    }
}
