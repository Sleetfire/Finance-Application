package by.it.academy.mail_service.exceptions;

public class InputOutputException extends RuntimeException{
    public InputOutputException() {
        super();
    }

    public InputOutputException(String message) {
        super(message);
    }

    public InputOutputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputOutputException(Throwable cause) {
        super(cause);
    }

    protected InputOutputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
