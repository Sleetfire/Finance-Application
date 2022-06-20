package by.it.academy.mail_scheduler_service.repositories.entities;

import by.it.academy.mail_scheduler_service.model.enums.TimeUnit;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "schedule", schema = "mail_scheduler_service")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "stop_time")
    private LocalDate stopTime;

    @Column(name = "interval")
    private int interval;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_unit")
    private TimeUnit timeUnit;

    public ScheduleEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, LocalDate startTime,
                          LocalDate stopTime, int interval, TimeUnit timeUnit) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    public ScheduleEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDate stopTime) {
        this.stopTime = stopTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity schedule = (ScheduleEntity) o;
        return interval == schedule.interval
                && Objects.equals(uuid, schedule.uuid)
                && Objects.equals(dtCreate, schedule.dtCreate)
                && Objects.equals(dtUpdate, schedule.dtUpdate)
                && Objects.equals(startTime, schedule.startTime)
                && Objects.equals(stopTime, schedule.stopTime)
                && timeUnit == schedule.timeUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, startTime, stopTime, interval, timeUnit);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", interval=" + interval +
                ", timeUnit=" + timeUnit +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private LocalDate startTime;
        private LocalDate stopTime;
        private int interval;
        private TimeUnit timeUnit;

        private Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public Builder setStartTime(LocalDate startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setStopTime(LocalDate stopTime) {
            this.stopTime = stopTime;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public ScheduleEntity build() {
            return new ScheduleEntity(this.uuid, this.dtCreate, this.dtUpdate, this.startTime, this.stopTime, this.interval,
                    this.timeUnit);
        }
    }
}
