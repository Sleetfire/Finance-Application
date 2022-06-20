package by.it.academy.mail_scheduler_service.services.api;

import by.it.academy.mail_scheduler_service.model.dto.Report;

import java.util.UUID;

public interface IReportService {

    /**
     * Creating a report in database
     * @param report report dto
     * @return report from database
     */
    Report create(Report report);

    /**
     * Getting report from database
     * @param uuid report's uuid
     * @return report from database
     */
    Report get(UUID uuid);

    /**
     * Updating report
     * @param uuid report's uuid
     * @param dtUpdate report's update date-time
     * @param updatedReport report with updated fields
     * @return updated report
     */
    Report update(UUID uuid, long dtUpdate, Report updatedReport);

    /**
     * Deleting report
     * @param uuid report's uuid
     * @param dtUpdate report's update date-time
     */
    void delete(UUID uuid, long dtUpdate);

}
