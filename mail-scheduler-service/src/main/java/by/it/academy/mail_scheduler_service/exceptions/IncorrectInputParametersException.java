package by.it.academy.mail_scheduler_service.exceptions;

public class IncorrectInputParametersException extends IllegalArgumentException{

    private String errorMessage = "The request contains incorrect data. Change the request and send it again";

    public String getErrorMessage() {
        return errorMessage;
    }

    public IncorrectInputParametersException() {
        super();
    }

    public IncorrectInputParametersException(String s) {
        super(s);
    }

    public IncorrectInputParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputParametersException(Throwable cause) {
        super(cause);
    }
}