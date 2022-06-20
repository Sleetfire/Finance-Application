package by.it.academy.mail_scheduler_service.services.api;

import by.it.academy.mail_scheduler_service.model.dto.Schedule;

import java.util.List;
import java.util.UUID;

public interface IScheduleService {

    /**
     * Creating schedule
     * @param schedule schedule dto
     * @return created schedule from database
     */
    Schedule create(Schedule schedule);

    /**
     * Getting schedule from database
     * @param uuid schedule's uuid
     * @return schedule from database
     */
    Schedule get(UUID uuid);

    /**
     * Getting list of all schedules
     * @return list of schedules
     */
    List<Schedule> getAll();

    /**
     * Updating schedule
     * @param uuid schedule's uuid
     * @param dtUpdate schedule's update date-time
     * @param updatedSchedule schedule with updated fields
     * @return updated schedule from database
     */
    Schedule update(UUID uuid, long dtUpdate, Schedule updatedSchedule);

    /**
     * Deleting schedule
     * @param uuid schedule's uuid
     * @param dtUpdate schedule's update date-time
     */
    void delete(UUID uuid, long dtUpdate);


}
