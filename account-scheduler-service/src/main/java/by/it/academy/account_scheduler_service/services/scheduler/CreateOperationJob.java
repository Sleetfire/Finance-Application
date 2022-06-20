package by.it.academy.account_scheduler_service.services.scheduler;

import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.services.ScheduledOperationService;
import by.it.academy.account_scheduler_service.services.api.IOperationRequestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateOperationJob implements Job {

    private final ScheduledOperationService scheduledOperationService;
    private final IOperationRequestService operationRequestService;

    public CreateOperationJob(@Qualifier("scheduledOperationService") ScheduledOperationService scheduledOperationService,
                              IOperationRequestService operationRequestService) {
        this.scheduledOperationService = scheduledOperationService;
        this.operationRequestService = operationRequestService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        UUID uuid = UUID.fromString(jobExecutionContext.getMergedJobDataMap().getString("uuid"));
        ScheduledOperation scheduledOperation = this.scheduledOperationService.get(uuid);
        this.operationRequestService.sendOperationRequest(scheduledOperation);
    }
}
