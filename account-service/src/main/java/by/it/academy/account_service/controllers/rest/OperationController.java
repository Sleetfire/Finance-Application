package by.it.academy.account_service.controllers.rest;

import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.models.dto.PageDTO;
import by.it.academy.account_service.services.OperationDecoratorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account/{uuid}/operation")
public class OperationController {

    private final OperationDecoratorService operationDecoratorService;

    public OperationController(OperationDecoratorService operationDecoratorService) {
        this.operationDecoratorService = operationDecoratorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Operation> create(@PathVariable UUID uuid, @RequestBody Operation operation) {
        return new ResponseEntity<>(this.operationDecoratorService.create(operation, uuid), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDTO<Operation>> getPage(@PathVariable UUID uuid,
                                                      @RequestParam(defaultValue = "0", required = false) int page,
                                                      @RequestParam(defaultValue = "1", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.operationDecoratorService.getPage(uuid, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Operation>> getAll(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.operationDecoratorService.getAll(uuid), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid_operation}/dt_update/{dt_update}")
    public ResponseEntity<Operation> update(@PathVariable UUID uuid_operation,
                                            @PathVariable long dt_update,
                                            @RequestBody Operation operation) {
        return new ResponseEntity<>(this.operationDecoratorService.update(uuid_operation, dt_update, operation), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid_operation}/dt_update/{dt_update}", method = RequestMethod.DELETE)
    public ResponseEntity<Operation> delete(@PathVariable UUID uuid,
                                            @PathVariable UUID uuid_operation,
                                            @PathVariable long dt_update) {
        this.operationDecoratorService.delete(uuid_operation, dt_update, uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
