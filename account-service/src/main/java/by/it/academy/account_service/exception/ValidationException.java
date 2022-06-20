package by.it.academy.account_service.exception;

import by.it.academy.account_service.models.errors.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends IllegalArgumentException {

    private List<ValidationError> errors = new ArrayList<>();

    public ValidationException(String s, List<ValidationError> errors) {
        super(s);
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public ValidationException() {
        super();
    }

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}