package by.it.academy.classifier_service.models.converters;

import by.it.academy.classifier_service.models.dto.OperationCategory;
import by.it.academy.classifier_service.repositories.entities.OperationCategoryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationCategoryDTOConverter implements Converter<OperationCategoryEntity, OperationCategory> {
    @Override
    public OperationCategory convert(OperationCategoryEntity source) {
        return OperationCategory.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .build();
    }

    @Override
    public <U> Converter<OperationCategoryEntity, U> andThen(Converter<? super OperationCategory, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
