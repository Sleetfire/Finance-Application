package by.it.academy.user_service.exception;

public class EssenceUpdateException extends RuntimeException{
    public EssenceUpdateException() {
        super();
    }

    public EssenceUpdateException(String message) {
        super(message);
    }

    public EssenceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceUpdateException(Throwable cause) {
        super(cause);
    }

    protected EssenceUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
