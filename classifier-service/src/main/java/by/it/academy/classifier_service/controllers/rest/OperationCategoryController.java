package by.it.academy.classifier_service.controllers.rest;

import by.it.academy.classifier_service.exception.IncorrectInputParametersException;
import by.it.academy.classifier_service.models.dto.OperationCategory;
import by.it.academy.classifier_service.models.dto.PageDTO;
import by.it.academy.classifier_service.services.api.IOperationCategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classifier/operation/category")
public class OperationCategoryController {

    private final IOperationCategoryService operationCategoryService;

    public OperationCategoryController(IOperationCategoryService operationCategoryService) {
        this.operationCategoryService = operationCategoryService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OperationCategory> create(@RequestBody OperationCategory operationCategory) {
        return new ResponseEntity<>(this.operationCategoryService.create(operationCategory), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDTO<OperationCategory>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                              @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }
        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.operationCategoryService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OperationCategory>> getAll() {
        return new ResponseEntity<>(this.operationCategoryService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<OperationCategory> get(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.operationCategoryService.get(uuid), HttpStatus.OK);
    }

}
