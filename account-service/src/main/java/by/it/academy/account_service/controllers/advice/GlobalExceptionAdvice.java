package by.it.academy.account_service.controllers.advice;

import by.it.academy.account_service.exception.*;
import by.it.academy.account_service.models.errors.MultipleResponseErrors;
import by.it.academy.account_service.models.errors.SingleResponseError;
import by.it.academy.account_service.models.errors.ValidationError;
import by.it.academy.account_service.services.BalanceService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LogManager.getLogger(BalanceService.class);

    @ExceptionHandler(IncorrectInputParametersException.class)
    public ResponseEntity<SingleResponseError> incorrectInputHandler(IncorrectInputParametersException exception) {
        logger.error("Incorrect input parameters. {}", exception.getErrorMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getErrorMessage() + " : " + exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MultipleResponseErrors<ValidationError>> validationHandler(ValidationException exception) {
        logger.error("Validation error. {}", exception.getMessage());
        return new ResponseEntity<>(new MultipleResponseErrors(exception.getMessage(), exception.getErrors()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EssenceNotFoundException.class)
    public ResponseEntity<SingleResponseError> essenceNotFoundHandler(EssenceNotFoundException exception) {
        logger.error("Essence not found exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EssenceUpdateException.class)
    public ResponseEntity<SingleResponseError> essenceUpdateHandler(EssenceUpdateException exception) {
        logger.error("Essence update exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EssenceDeleteException.class)
    public ResponseEntity<SingleResponseError> essenceDeleteHandler(EssenceDeleteException exception) {
        logger.error("Essence delete exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<SingleResponseError> badRequestHandler(BadRequestException exception) {
        logger.error("Bad request exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}