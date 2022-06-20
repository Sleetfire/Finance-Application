package by.it.academy.mail_scheduler_service.services.scheduler;

import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.model.enums.ReportType;
import by.it.academy.mail_scheduler_service.services.api.IRequestService;
import by.it.academy.mail_scheduler_service.services.api.IScheduleReportService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class CreateReportJob implements Job {

    private final IScheduleReportService scheduleReportService;
    private final IRequestService requestService;

    public CreateReportJob(@Qualifier("scheduledReportDecoratorService") IScheduleReportService scheduleReportService,
                           IRequestService requestService) {
        this.scheduleReportService = scheduleReportService;
        this.requestService = requestService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        UUID uuid = UUID.fromString(jobExecutionContext.getMergedJobDataMap().getString("uuid"));
        ScheduledReport scheduledReport = this.scheduleReportService.get(uuid);
        Report report = scheduledReport.getReport();
        if (!Objects.equals(report.getType(), ReportType.BALANCE)) {
            Map<String, Object> updatedParams = this.updateParams(report);
            report.setParams(updatedParams);
            scheduledReport.setReport(report);
            scheduledReport = this.scheduleReportService.update(scheduledReport.getUuid(),
                    DateTimeUtil.convertLocalDateTimeToLong(scheduledReport.getDtUpdate()), scheduledReport);
        }
        this.requestService.sendReport(scheduledReport);
    }

    private Map<String, Object> updateParams(Report report) {
        Map<String, Object> params = report.getParams();
        if (params.containsKey("from") && params.containsKey("to")) {
            if (params.get("to") instanceof Long) {
                long to = (long) params.get("to");
                params.replace("from", to);
            } else {
                throw new RuntimeException("Data getting error from map");
            }
            long newTo = DateTimeUtil.convertLocalDateTimeToLong(LocalDateTime.now());
            params.replace("to", newTo);
        }
        return params;
    }

}
