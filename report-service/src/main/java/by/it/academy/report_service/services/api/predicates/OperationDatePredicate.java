package by.it.academy.report_service.services.api.predicates;

import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.utils.DateTimeUtil;

import java.util.function.Predicate;

public class OperationDatePredicate implements Predicate<Operation> {

    private final long from;
    private final long to;

    public OperationDatePredicate(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean test(Operation operation) {
        long operationDate = DateTimeUtil.convertLocalDateTimeToLong(operation.getDate());
        return operationDate >= from && operationDate <= to;
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
