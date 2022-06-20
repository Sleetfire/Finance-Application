package by.it.academy.mail_scheduler_service.services;

import by.it.academy.mail_scheduler_service.exceptions.ValidationException;
import by.it.academy.mail_scheduler_service.model.dto.PageDTO;
import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.model.dto.Schedule;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.model.errors.ValidationError;
import by.it.academy.mail_scheduler_service.services.api.IReportService;
import by.it.academy.mail_scheduler_service.services.api.IScheduleReportService;
import by.it.academy.mail_scheduler_service.services.api.IScheduleService;
import by.it.academy.mail_scheduler_service.services.scheduler.api.IQuartzSchedulerService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
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
public class ScheduledReportDecoratorService implements IScheduleReportService {

    private final IScheduleReportService scheduleReportService;
    private final IScheduleService scheduleService;
    private final IReportService reportService;
    private final IQuartzSchedulerService quartzService;

    public ScheduledReportDecoratorService(@Qualifier("scheduledReportService") IScheduleReportService scheduleReportService,
                                           IScheduleService scheduleService, IReportService reportService,
                                           IQuartzSchedulerService quartzService) {
        this.scheduleReportService = scheduleReportService;
        this.scheduleService = scheduleService;
        this.reportService = reportService;
        this.quartzService = quartzService;
    }

    @Override
    @Transactional
    public ScheduledReport create(ScheduledReport scheduledReport) {
        this.check(scheduledReport);
        Schedule schedule = scheduledReport.getSchedule();
        Report report = scheduledReport.getReport();

        LocalDateTime dtNow = LocalDateTime.now();
        schedule.setDtCreate(dtNow);
        schedule.setDtUpdate(dtNow);
        report.setDtCreate(dtNow);
        report.setDtUpdate(dtNow);
        scheduledReport.setDtCreate(dtNow);
        scheduledReport.setDtUpdate(dtNow);

        schedule = this.scheduleService.create(schedule);
        report = this.reportService.create(report);

        scheduledReport.setSchedule(schedule);
        scheduledReport.setReport(report);

        scheduledReport = this.scheduleReportService.create(scheduledReport);
        this.quartzService.create(scheduledReport);
        return scheduledReport;
    }

    @Override
    public ScheduledReport get(UUID uuid) {
        return this.scheduleReportService.get(uuid);
    }

    @Override
    public PageDTO<ScheduledReport> getPage(Pageable pageable) {
        return this.scheduleReportService.getPage(pageable);
    }

    @Override
    @Transactional
    public ScheduledReport update(UUID uuid, long dtUpdate, ScheduledReport updatedScheduledReport) {
        this.check(updatedScheduledReport);
        this.quartzService.delete(uuid);
        Schedule updatedSchedule = updatedScheduledReport.getSchedule();
        Report updatedReport = updatedScheduledReport.getReport();
        ScheduledReport scheduledReport = this.scheduleReportService.get(uuid);
        long scheduleDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getSchedule().getDtUpdate());
        long reportDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getReport().getDtUpdate());

        updatedSchedule = this.scheduleService.update(scheduledReport.getSchedule().getUuid(), scheduleDtUpdate, updatedSchedule);
        updatedReport = this.reportService.update(scheduledReport.getReport().getUuid(), reportDtUpdate, updatedReport);
        scheduledReport.setSchedule(updatedSchedule);
        scheduledReport.setReport(updatedReport);

        updatedScheduledReport = this.scheduleReportService.update(uuid, dtUpdate, scheduledReport);
        this.quartzService.create(updatedScheduledReport);
        return updatedScheduledReport;
    }

    @Override
    @Transactional
    public void delete(UUID uuid, long dtUpdate) {
        this.quartzService.delete(uuid);
        ScheduledReport scheduledReport = this.scheduleReportService.get(uuid);
        long scheduleDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getSchedule().getDtUpdate());
        long reportDtUpdate = DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getReport().getDtUpdate());
        this.scheduleReportService.delete(uuid, dtUpdate);
        this.scheduleService.delete(scheduledReport.getSchedule().getUuid(), scheduleDtUpdate);
        this.reportService.delete(scheduledReport.getReport().getUuid(), reportDtUpdate);
    }

    @Override
    @Transactional
    public void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap) {
        this.quartzService.delete(uuid);
        this.scheduleReportService.stop(uuid, dtUpdate, scheduledReportMap);
    }

    @Override
    @Transactional
    public void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap) {
        this.scheduleReportService.start(uuid, dtUpdate, scheduledReportMap);
        ScheduledReport scheduledReport = this.scheduleReportService.get(uuid);
        this.quartzService.create(scheduledReport);
    }

    private void check(ScheduledReport scheduledReport) {
        List<ValidationError> errors = new ArrayList<>();
        if (scheduledReport.getSchedule() == null) {
            errors.add(new ValidationError("schedule", "schedule is null"));
        }
        if (scheduledReport.getReport() == null) {
            errors.add(new ValidationError("report", "report is null"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Operation validation error", errors);
        }
    }

}
