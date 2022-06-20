package by.it.academy.account_scheduler_service.exception;

public class EssenceNotFoundException extends RuntimeException {
    public EssenceNotFoundException() {
        super();
    }

    public EssenceNotFoundException(String message) {
        super(message);
    }

    public EssenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected EssenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
