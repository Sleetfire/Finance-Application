package by.it.academy.mail_scheduler_service.services.scheduler.api;

import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;

import java.util.UUID;

public interface IQuartzSchedulerService {

    /**
     * Creating scheduler
     * @param scheduledReport scheduled report dto
     */
    void create(ScheduledReport scheduledReport);

    /**
     * Deleting scheduler
     * @param uuid scheduled report's uuid
     */
    void delete(UUID uuid);

}
