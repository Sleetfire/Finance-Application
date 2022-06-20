package by.it.academy.mail_service.controllers.rest;

import by.it.academy.mail_service.model.dto.VerifyMail;
import by.it.academy.mail_service.services.api.IVerifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail/verify")
public class VerifyEmailController {

    private final IVerifyService verifyService;

    public VerifyEmailController(IVerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody VerifyMail verifyMail) {
        this.verifyService.send(verifyMail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
