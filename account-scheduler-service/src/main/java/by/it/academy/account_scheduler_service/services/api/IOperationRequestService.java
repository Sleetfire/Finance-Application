package by.it.academy.account_scheduler_service.services.api;

import by.it.academy.account_scheduler_service.models.dto.RequestOperation;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;

public interface IOperationRequestService {

    /**
     * Sending operation to account-service
     * @param scheduledOperation scheduled operation dto
     * @return operation from account-service response
     */
    RequestOperation sendOperationRequest(ScheduledOperation scheduledOperation);

}
