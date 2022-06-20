package by.it.academy.mail_scheduler_service.services;

import by.it.academy.mail_scheduler_service.exceptions.EssenceDeleteException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceNotFoundException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceUpdateException;
import by.it.academy.mail_scheduler_service.exceptions.ValidationException;
import by.it.academy.mail_scheduler_service.model.dto.Schedule;
import by.it.academy.mail_scheduler_service.model.enums.TimeUnit;
import by.it.academy.mail_scheduler_service.model.errors.ValidationError;
import by.it.academy.mail_scheduler_service.repositories.api.IScheduleRepository;
import by.it.academy.mail_scheduler_service.repositories.entities.ScheduleEntity;
import by.it.academy.mail_scheduler_service.services.api.IScheduleService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository scheduleRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(ScheduledReportService.class);

    public ScheduleService(IScheduleRepository scheduleRepository, ConversionService conversionService) {
        this.scheduleRepository = scheduleRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Schedule create(Schedule schedule) {
        this.checkSchedule(schedule);
        if (schedule.getDtCreate() == null || schedule.getDtUpdate() == null) {
            LocalDateTime dtNow = LocalDateTime.now();
            schedule.setDtCreate(dtNow);
            schedule.setDtUpdate(dtNow);
        }
        ScheduleEntity scheduleEntity = this.conversionService.convert(schedule, ScheduleEntity.class);
        if (scheduleEntity != null) {
            scheduleEntity = this.scheduleRepository.save(scheduleEntity);
            logger.info("Schedule with uuid: {} was create", scheduleEntity.getUuid());
        }
        return this.conversionService.convert(scheduleEntity, Schedule.class);
    }

    @Override
    public Schedule get(UUID uuid) {
        Optional<ScheduleEntity> optionalSchedule = this.scheduleRepository.findById(uuid);
        if (optionalSchedule.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalSchedule.get(), Schedule.class);
    }

    @Override
    public List<Schedule> getAll() {
        List<ScheduleEntity> scheduleEntityList = this.scheduleRepository.findAll();
        if (scheduleEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
        }
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleEntityList.forEach(scheduleEntity -> scheduleList.add(this.conversionService.convert(scheduleEntity,
                Schedule.class)));
        return scheduleList;
    }

    @Override
    public Schedule update(UUID uuid, long dtUpdate, Schedule updatedSchedule) {
        this.checkSchedule(updatedSchedule);
        Schedule schedule = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(schedule.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        LocalDate startTime = updatedSchedule.getStartTime();
        LocalDate stopTime = updatedSchedule.getStopTime();
        int interval = updatedSchedule.getInterval();
        TimeUnit timeUnit = updatedSchedule.getTimeUnit();

        if (!Objects.equals(startTime, schedule.getStartTime())) {
            schedule.setStartTime(startTime);
        }
        if (!Objects.equals(stopTime, schedule.getStopTime())) {
            schedule.setStopTime(stopTime);
        }
        if (interval != schedule.getInterval()) {
            schedule.setInterval(interval);
        }
        if (timeUnit.compareTo(schedule.getTimeUnit()) != 0) {
            schedule.setTimeUnit(timeUnit);
        }
        ScheduleEntity scheduleEntity = this.conversionService.convert(schedule, ScheduleEntity.class);
        if (scheduleEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        scheduleEntity = this.scheduleRepository.save(scheduleEntity);
        logger.info("Schedule with uuid: {} was update", scheduleEntity.getUuid());
        return this.conversionService.convert(scheduleEntity, Schedule.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        Schedule schedule = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(schedule.getDtUpdate())) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        ScheduleEntity scheduleEntity = this.conversionService.convert(schedule, ScheduleEntity.class);
        if (scheduleEntity == null) {
            throw new EssenceDeleteException("Incorrect delete");
        }
        this.scheduleRepository.delete(scheduleEntity);
        logger.info("Schedule with uuid: {} was delete", scheduleEntity.getUuid());
    }

    private void checkSchedule(Schedule schedule) {
        List<ValidationError> errors = new ArrayList<>();
        if (schedule.getStartTime() == null) {
            errors.add(new ValidationError("start_time", "start_time is null"));
        }
//        if (schedule.getStopTime() == null) {
//            errors.add(new ValidationError("stop_time", "stop_time is null"));
//        }
        if (schedule.getInterval() <= 0) {
            errors.add(new ValidationError("interval", "interval must be greater than zero"));
        }
        if (schedule.getTimeUnit() == null) {
            errors.add(new ValidationError("time_unit", "time_unit is null"));
        }
        if (schedule.getStopTime() != null && schedule.getStopTime().compareTo(schedule.getStartTime()) < 1) {
            errors.add(new ValidationError("stop_time", "stop_time must be greater than start_time"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }
}
