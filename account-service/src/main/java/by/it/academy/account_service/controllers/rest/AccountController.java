package by.it.academy.account_service.controllers.rest;

import by.it.academy.account_service.exception.IncorrectInputParametersException;
import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.services.api.IAccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(@Qualifier("accountDecoratorService") IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return new ResponseEntity<>(this.accountService.create(account), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDTO<Account>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                    @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }

        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.accountService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Account> get(@PathVariable UUID id) {
        return new ResponseEntity<>(this.accountService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Account> update(@PathVariable UUID uuid,
                                          @PathVariable long dt_update,
                                          @RequestBody Account account) {
        return new ResponseEntity<>(this.accountService.update(uuid, dt_update, account), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}/dt_update/{dt_update}", method = RequestMethod.DELETE)
    public ResponseEntity<Account> delete(@PathVariable UUID id,
                                          @PathVariable long dt_update) {
        this.accountService.delete(id, dt_update);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}