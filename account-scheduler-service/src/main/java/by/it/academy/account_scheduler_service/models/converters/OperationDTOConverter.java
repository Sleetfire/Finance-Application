package by.it.academy.account_scheduler_service.models.converters;

import by.it.academy.account_scheduler_service.models.dto.Operation;
import by.it.academy.account_scheduler_service.repositories.entities.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationDTOConverter implements Converter<OperationEntity, Operation> {
    @Override
    public Operation convert(OperationEntity source) {
        return Operation.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setAccount(source.getAccount())
                .setDescription(source.getDescription())
                .setCategory(source.getCategory())
                .setValue(source.getValue())
                .setCurrency(source.getCurrency())
                .build();
    }

    @Override
    public <U> Converter<OperationEntity, U> andThen(Converter<? super Operation, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
