package by.it.academy.mail_scheduler_service.model.converters;

import by.it.academy.mail_scheduler_service.model.dto.Schedule;
import by.it.academy.mail_scheduler_service.repositories.entities.ScheduleEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDTOConverter implements Converter<ScheduleEntity, Schedule> {
    @Override
    public Schedule convert(ScheduleEntity source) {
        return Schedule.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setStartTime(source.getStartTime())
                .setStopTime(source.getStopTime())
                .setInterval(source.getInterval())
                .setTimeUnit(source.getTimeUnit())
                .build();
    }

    @Override
    public <U> Converter<ScheduleEntity, U> andThen(Converter<? super Schedule, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
