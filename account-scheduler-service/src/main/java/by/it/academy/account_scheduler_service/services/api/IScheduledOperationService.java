package by.it.academy.account_scheduler_service.services.api;

import by.it.academy.account_scheduler_service.models.dto.PageDTO;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface IScheduledOperationService {

    /**
     * Creating scheduled operation
     * @param scheduledOperation scheduled operation dto
     * @return created scheduled operation from database
     */
    ScheduledOperation create(ScheduledOperation scheduledOperation);

    /**
     * Getting scheduled operation
     * @param uuid scheduled operation's uuid
     * @return scheduled operation from database
     */
    ScheduledOperation get(UUID uuid);

    /**
     * Getting page of scheduled operations
     * @param pageable param for pagination
     * @return page of scheduled operations from database
     */
    PageDTO<ScheduledOperation> getPage(Pageable pageable);

    /**
     * Updating scheduled operation
     * @param uuid scheduled operation's uuid
     * @param dtUpdate scheduled operation's update date-time
     * @param updatedScheduledOperation scheduled operation with updated fields
     * @return updated scheduled operation
     */
    ScheduledOperation update(UUID uuid, long dtUpdate, ScheduledOperation updatedScheduledOperation);

    /**
     * Deleting scheduled operation
     * @param uuid scheduled operation's uuid
     * @param dtUpdate scheduled operation's update date-time
     */
    void delete(UUID uuid, long dtUpdate);

    /**
     * Stopping scheduled operation
     * @param uuid scheduled operation's uuid
     * @param dtUpdate scheduled operation's update date-time
     * @param scheduledOperationMap map with scheduled operation status
     */
    void stop(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap);

    /**
     * Starting scheduled operation
     * @param uuid scheduled operation's uuid
     * @param dtUpdate scheduled operation's update date-time
     * @param scheduledOperationMap map with scheduled operation status
     */
    void start(UUID uuid, long dtUpdate, Map<String, Object> scheduledOperationMap);

}
