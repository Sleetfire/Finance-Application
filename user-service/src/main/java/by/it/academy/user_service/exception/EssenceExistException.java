package by.it.academy.user_service.exception;

public class EssenceExistException extends RuntimeException{
    public EssenceExistException() {
        super();
    }

    public EssenceExistException(String message) {
        super(message);
    }

    public EssenceExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceExistException(Throwable cause) {
        super(cause);
    }

    protected EssenceExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
