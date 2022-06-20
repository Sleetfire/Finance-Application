package by.it.academy.mail_scheduler_service.model.converters;

import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.repositories.entities.ScheduledReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReportEntityConverter implements Converter<ScheduledReport, ScheduledReportEntity> {
    @Override
    public ScheduledReportEntity convert(ScheduledReport source) {
        return ScheduledReportEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setStatus(source.getStatus())
                .setScheduleEntity(new ScheduleEntityConverter().convert(source.getSchedule()))
                .setReportEntity(new ReportEntityConverter().convert((Report) source.getReport()))
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<ScheduledReport, U> andThen(Converter<? super ScheduledReportEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
