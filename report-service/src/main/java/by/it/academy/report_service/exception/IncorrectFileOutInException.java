package by.it.academy.report_service.exception;

import java.io.IOException;

public class IncorrectFileOutInException extends RuntimeException {
    public IncorrectFileOutInException() {
        super();
    }

    public IncorrectFileOutInException(String message) {
        super(message);
    }

    public IncorrectFileOutInException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectFileOutInException(Throwable cause) {
        super(cause);
    }
}
