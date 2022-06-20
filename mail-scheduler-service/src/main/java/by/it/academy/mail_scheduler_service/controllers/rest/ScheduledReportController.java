package by.it.academy.mail_scheduler_service.controllers.rest;

import by.it.academy.mail_scheduler_service.exceptions.IncorrectInputParametersException;
import by.it.academy.mail_scheduler_service.model.dto.PageDTO;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.services.api.IScheduleReportService;
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
@RequestMapping(value = "/scheduler/mail/report",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class ScheduledReportController {

    private final IScheduleReportService scheduleReportService;

    public ScheduledReportController(@Qualifier("scheduledReportDecoratorService") IScheduleReportService scheduleReportService) {
        this.scheduleReportService = scheduleReportService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<ScheduledReport> create(@RequestBody ScheduledReport scheduledReport) {
        return new ResponseEntity<>(this.scheduleReportService.create(scheduledReport), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<PageDTO<ScheduledReport>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                            @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }
        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.scheduleReportService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public ResponseEntity<ScheduledReport> update(@PathVariable UUID uuid, @PathVariable long dt_update,
                                                  @RequestBody ScheduledReport updatedScheduledReport) {
        return new ResponseEntity<>(this.scheduleReportService.update(uuid, dt_update, updatedScheduledReport), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/dt_delete/{dt_update}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable UUID uuid, @PathVariable long dt_update) {
        this.scheduleReportService.delete(uuid, dt_update);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/stop/dt_update/{dt_update}", method = RequestMethod.PATCH)
    public ResponseEntity<?> stop(@PathVariable UUID uuid,
                                  @PathVariable long dt_update,
                                  @RequestBody Map<String, Object> scheduledOperationMap) {
        this.scheduleReportService.stop(uuid, dt_update, scheduledOperationMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/start/dt_update/{dt_update}", method = RequestMethod.PATCH)
    public ResponseEntity<?> start(@PathVariable UUID uuid,
                                   @PathVariable long dt_update,
                                   @RequestBody Map<String, Object> scheduledOperationMap) {
        this.scheduleReportService.start(uuid, dt_update, scheduledOperationMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
