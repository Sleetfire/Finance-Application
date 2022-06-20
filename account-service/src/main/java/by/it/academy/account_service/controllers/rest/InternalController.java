package by.it.academy.account_service.controllers.rest;

import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.services.OperationDecoratorService;
import by.it.academy.account_service.services.api.IAccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/internal/account",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class InternalController {

    private final IAccountService accountService;
    private final OperationDecoratorService operationDecoratorService;

    public InternalController(@Qualifier("accountDecoratorService") IAccountService accountService,
                              OperationDecoratorService operationDecoratorService) {
        this.accountService = accountService;
        this.operationDecoratorService = operationDecoratorService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccount(@PathVariable UUID id) {
        return new ResponseEntity<>(this.accountService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/operation/all", method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> getAllOperations(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.operationDecoratorService.getAll(uuid), HttpStatus.OK);
    }
}
