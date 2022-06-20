package by.it.academy.account_service.models.converters;

import by.it.academy.account_service.models.dto.Balance;
import by.it.academy.account_service.repositories.entities.BalanceEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BalanceDTOConverter implements Converter<BalanceEntity, Balance> {
    @Override
    public Balance convert(BalanceEntity source) {
        return Balance.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setValue(source.getValue())
                .build();
    }

    @Override
    public <U> Converter<BalanceEntity, U> andThen(Converter<? super Balance, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
