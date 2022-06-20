package by.it.academy.account_scheduler_service.exception;

public class RunSchedulerException extends RuntimeException{
    public RunSchedulerException() {
        super();
    }

    public RunSchedulerException(String message) {
        super(message);
    }

    public RunSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunSchedulerException(Throwable cause) {
        super(cause);
    }

    protected RunSchedulerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
