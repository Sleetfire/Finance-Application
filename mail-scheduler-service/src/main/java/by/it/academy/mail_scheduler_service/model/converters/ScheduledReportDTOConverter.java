package by.it.academy.mail_scheduler_service.model.converters;

import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.repositories.entities.ScheduledReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReportDTOConverter implements Converter<ScheduledReportEntity, ScheduledReport> {
    @Override
    public ScheduledReport convert(ScheduledReportEntity source) {
        return ScheduledReport.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setStatus(source.getStatus())
                .setSchedule(new ScheduleDTOConverter().convert(source.getScheduleEntity()))
                .setReport(new ReportDTOConverter().convert(source.getReportEntity()))
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<ScheduledReportEntity, U> andThen(Converter<? super ScheduledReport, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
