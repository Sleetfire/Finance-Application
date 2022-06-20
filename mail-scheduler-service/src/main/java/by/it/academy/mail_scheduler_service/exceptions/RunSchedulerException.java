package by.it.academy.mail_scheduler_service.exceptions;

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
