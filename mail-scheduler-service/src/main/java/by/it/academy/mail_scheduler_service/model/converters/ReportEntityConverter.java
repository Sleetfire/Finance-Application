package by.it.academy.mail_scheduler_service.model.converters;

import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.repositories.entities.ReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportEntityConverter implements Converter<Report, ReportEntity> {
    @Override
    public ReportEntity convert(Report source) {
        return ReportEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setParams(source.getParams())
                .setType(source.getType())
                .build();
    }

    @Override
    public <U> Converter<Report, U> andThen(Converter<? super ReportEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
