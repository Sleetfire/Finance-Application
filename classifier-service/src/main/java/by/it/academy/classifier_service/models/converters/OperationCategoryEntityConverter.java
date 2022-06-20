package by.it.academy.classifier_service.models.converters;

import by.it.academy.classifier_service.models.dto.OperationCategory;
import by.it.academy.classifier_service.repositories.entities.OperationCategoryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationCategoryEntityConverter implements Converter<OperationCategory, OperationCategoryEntity> {
    @Override
    public OperationCategoryEntity convert(OperationCategory source) {
        return OperationCategoryEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .build();
    }

    @Override
    public <U> Converter<OperationCategory, U> andThen(Converter<? super OperationCategoryEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
