package by.it.academy.account_scheduler_service.controllers.rest;

import by.it.academy.account_scheduler_service.exception.IncorrectInputParametersException;
import by.it.academy.account_scheduler_service.models.dto.PageDTO;
import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.services.api.IScheduledOperationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/scheduler/operation")
public class ScheduledOperationController {

    private final IScheduledOperationService scheduledOperationService;

    public ScheduledOperationController(@Qualifier("scheduledOperationDecoratorService") IScheduledOperationService scheduledOperationService) {
        this.scheduledOperationService = scheduledOperationService;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduledOperation> create(@RequestBody ScheduledOperation scheduledOperation) {
        return new ResponseEntity<>(this.scheduledOperationService.create(scheduledOperation), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<PageDTO<ScheduledOperation>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }
        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.scheduledOperationService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduledOperation> update(@PathVariable UUID uuid, @PathVariable long dt_update,
                                                     @RequestBody ScheduledOperation updatedScheduledOperation) {
        return new ResponseEntity<>(this.scheduledOperationService.update(uuid, dt_update, updatedScheduledOperation),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/dt_delete/{dt_update}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable UUID uuid, @PathVariable long dt_update) {
        this.scheduledOperationService.delete(uuid, dt_update);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/{uuid}/stop/dt_update/{dt_update}", method = RequestMethod.PATCH)
    public ResponseEntity<?> stop(@PathVariable UUID uuid,
                                  @PathVariable long dt_update,
                                  @RequestBody Map<String, Object> scheduledOperationMap) {
        this.scheduledOperationService.stop(uuid, dt_update, scheduledOperationMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/start/dt_update/{dt_update}", method = RequestMethod.PATCH)
    public ResponseEntity<?> start(@PathVariable UUID uuid,
                                   @PathVariable long dt_update,
                                   @RequestBody Map<String, Object> scheduledOperationMap) {
        this.scheduledOperationService.start(uuid, dt_update, scheduledOperationMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
