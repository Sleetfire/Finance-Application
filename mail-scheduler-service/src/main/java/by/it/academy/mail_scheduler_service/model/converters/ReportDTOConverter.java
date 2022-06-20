package by.it.academy.mail_scheduler_service.model.converters;

import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.repositories.entities.ReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOConverter implements Converter<ReportEntity, Report> {
    @Override
    public Report convert(ReportEntity source) {
        return Report.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setParams(source.getParams())
                .setType(source.getType())
                .build();
    }

    @Override
    public <U> Converter<ReportEntity, U> andThen(Converter<? super Report, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
