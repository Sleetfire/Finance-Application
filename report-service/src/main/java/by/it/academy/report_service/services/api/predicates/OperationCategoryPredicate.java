package by.it.academy.report_service.services.api.predicates;

import by.it.academy.report_service.models.dto.rest_template.Operation;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class OperationCategoryPredicate implements Predicate<Operation> {

    private final List<UUID> categories;

    public OperationCategoryPredicate(List<UUID> categories) {
        this.categories = categories;
    }

    @Override
    public boolean test(Operation operation) {
        return categories.stream().anyMatch(category -> operation.getCategory().compareTo(category) == 0);
    }

    @Override
    public Predicate<Operation> and(Predicate<? super Operation> other) {
        return Predicate.super.and(other);
    }

    @Override
    public Predicate<Operation> negate() {
        return Predicate.super.negate();
    }

    @Override
    public Predicate<Operation> or(Predicate<? super Operation> other) {
        return Predicate.super.or(other);
    }
}
