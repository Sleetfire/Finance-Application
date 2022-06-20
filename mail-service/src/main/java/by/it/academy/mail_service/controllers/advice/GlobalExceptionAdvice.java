package by.it.academy.mail_service.controllers.advice;

import by.it.academy.mail_service.exceptions.InputOutputException;
import by.it.academy.mail_service.exceptions.SendMessageException;
import by.it.academy.mail_service.exceptions.ValidationException;
import by.it.academy.mail_service.model.errors.MultipleResponseErrors;
import by.it.academy.mail_service.model.errors.SingleResponseError;
import by.it.academy.mail_service.model.errors.ValidationError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MultipleResponseErrors<ValidationError>> validationHandler(ValidationException exception) {
        logger.error("Validation exception. {}", exception.getMessage());
        return new ResponseEntity<>(new MultipleResponseErrors(exception.getMessage(), exception.getErrors()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SendMessageException.class)
    public ResponseEntity<SingleResponseError> sendMessageHandler(SendMessageException exception) {
        logger.error("SendMessageException exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InputOutputException.class)
    public ResponseEntity<SingleResponseError>inputOutputHandler(InputOutputException exception) {
        logger.error("InputOutputException exception. {}", exception.getMessage());
        return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
