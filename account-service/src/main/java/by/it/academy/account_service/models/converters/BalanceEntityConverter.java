package by.it.academy.account_service.models.converters;

import by.it.academy.account_service.models.dto.Balance;
import by.it.academy.account_service.repositories.entities.BalanceEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BalanceEntityConverter implements Converter<Balance, BalanceEntity> {
    @Override
    public BalanceEntity convert(Balance source) {
        return BalanceEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setValue(source.getValue())
                .build();
    }

    @Override
    public <U> Converter<Balance, U> andThen(Converter<? super BalanceEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
