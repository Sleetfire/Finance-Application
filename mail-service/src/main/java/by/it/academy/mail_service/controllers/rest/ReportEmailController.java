package by.it.academy.mail_service.controllers.rest;

import by.it.academy.mail_service.model.dto.ReportEmail;
import by.it.academy.mail_service.services.api.IReportEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail/report")
public class ReportEmailController {

    private final IReportEmailService reportEmailService;

    public ReportEmailController(IReportEmailService reportEmailService) {
        this.reportEmailService = reportEmailService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody ReportEmail reportEmail) {
        this.reportEmailService.send(reportEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
