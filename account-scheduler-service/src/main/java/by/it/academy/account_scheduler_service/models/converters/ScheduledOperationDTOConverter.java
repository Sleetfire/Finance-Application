package by.it.academy.account_scheduler_service.models.converters;

import by.it.academy.account_scheduler_service.models.dto.ScheduledOperation;
import by.it.academy.account_scheduler_service.repositories.entities.ScheduledOperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationDTOConverter implements Converter<ScheduledOperationEntity, ScheduledOperation> {

    @Override
    public ScheduledOperation convert(ScheduledOperationEntity source) {
        return ScheduledOperation.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setSchedule(new ScheduleDTOConverter().convert(source.getScheduleEntity()))
                .setOperation(new OperationDTOConverter().convert(source.getOperationEntity()))
                .setStatus(source.getStatus())
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<ScheduledOperationEntity, U> andThen(Converter<? super ScheduledOperation, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
