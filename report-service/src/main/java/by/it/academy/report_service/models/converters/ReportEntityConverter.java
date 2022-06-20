package by.it.academy.report_service.models.converters;

import by.it.academy.report_service.models.dto.Report;
import by.it.academy.report_service.repositories.entities.ReportEntity;
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
                .setStatus(source.getStatus())
                .setType(source.getType())
                .setDescription(source.getDescription())
                .setParams(source.getParams())
                .setUrl(source.getUrl())
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<Report, U> andThen(Converter<? super ReportEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
