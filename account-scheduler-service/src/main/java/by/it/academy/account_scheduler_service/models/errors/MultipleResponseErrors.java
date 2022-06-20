package by.it.academy.account_scheduler_service.models.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultipleResponseErrors<T> {

    @JsonProperty("logref")
    private final String logRef;
    @JsonProperty("errors")
    private final List<T> errors;

    public MultipleResponseErrors(String logRef, List<T> errors) {
        this.logRef = logRef;
        this.errors = errors;
    }

    public String getLogRef() {
        return logRef;
    }

    public List<T> getErrors() {
        return errors;
    }
}
