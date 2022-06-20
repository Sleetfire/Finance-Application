package by.it.academy.mail_scheduler_service.services.scheduler;

import by.it.academy.mail_scheduler_service.exceptions.RunSchedulerException;
import by.it.academy.mail_scheduler_service.model.dto.Schedule;
import by.it.academy.mail_scheduler_service.model.dto.ScheduledReport;
import by.it.academy.mail_scheduler_service.services.scheduler.api.IQuartzSchedulerService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class QuartzSchedulerService implements IQuartzSchedulerService {

    private final Scheduler scheduler;
    private static final Logger logger = LogManager.getLogger(QuartzSchedulerService.class);


    public QuartzSchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void create(ScheduledReport scheduledReport) {

        Schedule schedule = scheduledReport.getSchedule();
        UUID uuid = scheduledReport.getUuid();

        JobDetail job = JobBuilder.newJob(CreateReportJob.class)
                .withIdentity(uuid.toString(), "uuid")
                .usingJobData("uuid", uuid.toString())
                .build();

        Date start = DateTimeUtil.convertLocalDateToDate(schedule.getStartTime());
        Date stop;
        if (schedule.getStopTime() == null) {
            stop = null;
        } else {
            stop = DateTimeUtil.convertLocalDateToDate(schedule.getStopTime());
        }

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(uuid.toString(), "uuid")
                .startAt(start)
                .withSchedule(
                        CalendarIntervalScheduleBuilder
                                .calendarIntervalSchedule()
                                .withInterval(schedule.getInterval(), DateBuilder.IntervalUnit
                                        .valueOf(schedule.getTimeUnit().toString()))
                )
                .endAt(stop)
                .build();

        try {
            this.scheduler.scheduleJob(job, trigger);
            logger.info("Scheduler with job key: {} was create", uuid);
        } catch (SchedulerException e) {
            throw new RunSchedulerException("Scheduler startup error");
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            this.scheduler.deleteJob(new JobKey(uuid.toString(), "uuid"));
            logger.info("Scheduler with job key: {} was delete", uuid);
        } catch (SchedulerException e) {
            throw new RunSchedulerException("Scheduler shutdown error");
        }
    }
}
