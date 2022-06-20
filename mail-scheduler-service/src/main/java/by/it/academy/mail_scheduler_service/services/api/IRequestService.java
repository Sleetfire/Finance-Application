package by.it.academy.mail_scheduler_service.services.api;

import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;

public interface IRequestService {

    /**
     * Sending a report to report-service
     * @param scheduledReport scheduled report
     */
    void sendReport(ScheduledReport scheduledReport);
}
