package by.it.academy.mail_scheduler_service.services.api;

import by.it.academy.mail_scheduler_service.model.dto.PageDTO;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface IScheduleReportService {

    /**
     * Creating a scheduled report
     * @param scheduledReport scheduled report dto
     * @return scheduled report from database
     */
    ScheduledReport create(ScheduledReport scheduledReport);

    /**
     * Getting scheduled report from database
     * @param uuid scheduled report's uuid
     * @return scheduled report from database
     */
    ScheduledReport get(UUID uuid);

    /**
     * Getting page of scheduled reports
     * @param pageable param for pagination
     * @return page of scheduled reports
     */
    PageDTO<ScheduledReport> getPage(Pageable pageable);

    /**
     * Updating scheduled report
     * @param uuid scheduled report's uuid
     * @param dtUpdate scheduled report's update date-time
     * @param updatedScheduledReport scheduled report with updated fields
     * @return updated scheduled report
     */
    ScheduledReport update(UUID uuid, long dtUpdate, ScheduledReport updatedScheduledReport);

    /**
     * Deleting scheduled report
     * @param uuid scheduled report's uuid
     * @param dtUpdate scheduled report's update date-time
     */
    void delete(UUID uuid, long dtUpdate);

    /**
     * Stopping scheduled report
     * @param uuid scheduled report's uuid
     * @param dtUpdate scheduled report's update date-time
     * @param scheduledReportMap map with scheduled report status
     */
    void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap);

    /**
     * Starting scheduled report
     * @param uuid scheduled report's uuid
     * @param dtUpdate scheduled report's update date-time
     * @param scheduledReportMap map with scheduled report status
     */
    void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledReportMap);

}
