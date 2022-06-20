package by.it.academy.classifier_service.models.converters;

import by.it.academy.classifier_service.models.dto.Currency;
import by.it.academy.classifier_service.repositories.entities.CurrencyEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDTOConverter implements Converter<CurrencyEntity, Currency> {
    @Override
    public Currency convert(CurrencyEntity source) {
        return Currency.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .build();
    }

    @Override
    public <U> Converter<CurrencyEntity, U> andThen(Converter<? super Currency, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
