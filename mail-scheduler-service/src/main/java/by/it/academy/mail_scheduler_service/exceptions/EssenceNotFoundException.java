package by.it.academy.mail_scheduler_service.exceptions;

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
