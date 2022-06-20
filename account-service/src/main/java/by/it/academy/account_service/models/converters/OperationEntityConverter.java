package by.it.academy.account_service.models.converters;

import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.repositories.entities.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationEntityConverter implements Converter<Operation, OperationEntity> {
    @Override
    public OperationEntity convert(Operation source) {
        return OperationEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setDate(source.getDate())
                .setDescription(source.getDescription())
                .setCategory(source.getCategory())
                .setValue(source.getValue())
                .setCurrency(source.getCurrency())
                .setAccountEntity(new AccountEntityConverter().convert(source.getAccount()))
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<Operation, U> andThen(Converter<? super OperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
