package by.it.academy.user_service.exception;

public class UserDisableException extends RuntimeException{
    public UserDisableException() {
        super();
    }

    public UserDisableException(String message) {
        super(message);
    }

    public UserDisableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisableException(Throwable cause) {
        super(cause);
    }

    protected UserDisableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
