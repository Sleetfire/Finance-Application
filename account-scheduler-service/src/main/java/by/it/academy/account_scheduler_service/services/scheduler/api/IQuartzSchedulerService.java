package by.it.academy.account_scheduler_service.services.scheduler.api;

import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;

import java.util.UUID;

public interface IQuartzSchedulerService {

    /**
     * Creating scheduler
     * @param scheduledOperation scheduled operation dto
     */
    void create(ScheduledOperation scheduledOperation);

    /**
     * Deleting scheduler
     * @param uuid scheduled operation's uuid
     */
    void delete(UUID uuid);

}
