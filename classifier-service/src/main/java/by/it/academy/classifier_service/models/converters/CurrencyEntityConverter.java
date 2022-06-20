package by.it.academy.classifier_service.models.converters;

import by.it.academy.classifier_service.models.dto.Currency;
import by.it.academy.classifier_service.repositories.entities.CurrencyEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyEntityConverter implements Converter<Currency, CurrencyEntity> {
    @Override
    public CurrencyEntity convert(Currency source) {
        return CurrencyEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .build();
    }

    @Override
    public <U> Converter<Currency, U> andThen(Converter<? super CurrencyEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
