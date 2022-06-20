package by.it.academy.mail_scheduler_service.exceptions;

public class EssenceDeleteException extends RuntimeException{
    public EssenceDeleteException() {
        super();
    }

    public EssenceDeleteException(String message) {
        super(message);
    }

    public EssenceDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceDeleteException(Throwable cause) {
        super(cause);
    }

    protected EssenceDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
