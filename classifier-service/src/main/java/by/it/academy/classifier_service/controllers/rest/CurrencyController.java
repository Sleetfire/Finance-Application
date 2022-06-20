package by.it.academy.classifier_service.controllers.rest;

import by.it.academy.classifier_service.exception.IncorrectInputParametersException;
import by.it.academy.classifier_service.models.dto.Currency;
import by.it.academy.classifier_service.models.dto.PageDTO;
import by.it.academy.classifier_service.services.api.ICurrencyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classifier/currency")
public class CurrencyController {

    private final ICurrencyService currencyService;

    public CurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Currency> create(@RequestBody Currency currency) {
        return new ResponseEntity<>(this.currencyService.create(currency), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDTO<Currency>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                     @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }
        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.currencyService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Currency>> getAll() {
        return new ResponseEntity<>(this.currencyService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Currency> get(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.currencyService.get(uuid), HttpStatus.OK);
    }

}
