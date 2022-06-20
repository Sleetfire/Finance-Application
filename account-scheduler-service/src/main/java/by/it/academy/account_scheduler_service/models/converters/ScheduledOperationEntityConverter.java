package by.it.academy.account_scheduler_service.models.converters;

import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.repositories.entities.ScheduledOperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationEntityConverter implements Converter<ScheduledOperation, ScheduledOperationEntity> {

    @Override
    public ScheduledOperationEntity convert(ScheduledOperation source) {
        return ScheduledOperationEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setScheduleEntity(new ScheduleEntityConverter().convert(source.getSchedule()))
                .setOperationEntity(new OperationEntityConverter().convert(source.getOperation()))
                .setStatus(source.getStatus())
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<ScheduledOperation, U> andThen(Converter<? super ScheduledOperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
