package by.it.academy.report_service.utils;

import by.it.academy.report_service.exception.ValidationException;
import by.it.academy.report_service.models.errors.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamUtil {

    private ParamUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkDateAndCategories(Map<String, Object> paramMap) {
        List<ValidationError> errors = new ArrayList<>();
        if (!paramMap.containsKey("accounts")) {
            errors.add(new ValidationError("accounts", "accounts is not exist"));
        }
        if (!paramMap.containsKey("from")) {
            errors.add(new ValidationError("from", "from is not exist"));
        }
        if (!paramMap.containsKey("to")) {
            errors.add(new ValidationError("to", "to is not exist"));
        }
        if (!paramMap.containsKey("categories")) {
            errors.add(new ValidationError("categories", "categories is not exist"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

    public static void checkBalance(Map<String, Object> paramMap) {
        List<ValidationError> errors = new ArrayList<>();
        if (!paramMap.containsKey("accounts")) {
           errors.add(new ValidationError("accounts", "accounts is not exist"));
        }
        if (paramMap.containsKey("from")) {
            errors.add(new ValidationError("from", "from is exist, but should not"));
        }
        if (paramMap.containsKey("to")) {
            errors.add(new ValidationError("to", "to is exist, but should not"));
        }
        if (paramMap.containsKey("categories")) {
            errors.add(new ValidationError("categories", "categories are exist, but should not"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

}
