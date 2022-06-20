package by.it.academy.mail_scheduler_service.model.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleResponseError {

    @JsonProperty("logref")
    private final String logRef;
    @JsonProperty("message")
    private final String message;

    public SingleResponseError(String message) {
        this.message = message;
        this.logRef = "error";
    }

    public String getLogRef() {
        return logRef;
    }

    public String getMessage() {
        return message;
    }
}