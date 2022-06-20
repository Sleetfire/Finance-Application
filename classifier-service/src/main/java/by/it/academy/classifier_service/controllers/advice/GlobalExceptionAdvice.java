package by.it.academy.classifier_service.controllers.advice;

import by.it.academy.classifier_service.exception.EssenceNotFoundException;
import by.it.academy.classifier_service.exception.IncorrectInputParametersException;
import by.it.academy.classifier_service.exception.ValidationException;
import by.it.academy.classifier_service.models.errors.MultipleResponseErrors;
import by.it.academy.classifier_service.models.errors.SingleResponseError;
import by.it.academy.classifier_service.models.errors.ValidationError;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(IncorrectInputParametersException.class)
    public ResponseEntity<SingleResponseError> incorrectInputHandler(IncorrectInputParametersException exception) {
        logger.error("Incorrect input parameters exception. {}", exception.getErrorMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getErrorMessage() + " : " + exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MultipleResponseErrors<ValidationError>> validationHandler(ValidationException exception) {
        logger.error("Validation exception. {}", exception.getMessage());
        return new ResponseEntity<>(new MultipleResponseErrors(exception.getMessage(), exception.getErrors()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EssenceNotFoundException.class)
    public ResponseEntity<SingleResponseError> essenceNotFoundHandler(EssenceNotFoundException exception) {
        logger.error("Essence not found exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.NO_CONTENT);
    }

}
