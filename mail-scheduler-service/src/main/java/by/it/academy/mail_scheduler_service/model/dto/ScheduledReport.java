package by.it.academy.mail_scheduler_service.model.dto;

import by.it.academy.mail_scheduler_service.model.deserializers.LocalDateTimeDeserializer;
import by.it.academy.mail_scheduler_service.model.enums.Status;
import by.it.academy.mail_scheduler_service.model.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ScheduledReport {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("schedule")
    private Schedule schedule;

    @JsonProperty("report")
    private Report report;

    @JsonProperty("username")
    private String username;

    public ScheduledReport(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, Status status, Schedule schedule,
                           Report report,
                           String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.schedule = schedule;
        this.report = report;
        this.username = username;
    }

    public ScheduledReport() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledReport that = (ScheduledReport) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && status == that.status
                && Objects.equals(schedule, that.schedule)
                && Objects.equals(report, that.report)
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, status, schedule, report, username);
    }

    @Override
    public String toString() {
        return "ScheduledReport{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", status=" + status +
                ", schedule=" + schedule +
                ", report=" + report +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private Status status;
        private Schedule schedule;
        private Report report;
        private String username;

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

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setReport(Report report) {
            this.report = report;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ScheduledReport build() {
            return new ScheduledReport(this.uuid, this.dtCreate, this.dtUpdate, this.status, this.schedule,
                    this.report, this.username);
        }
    }
}
