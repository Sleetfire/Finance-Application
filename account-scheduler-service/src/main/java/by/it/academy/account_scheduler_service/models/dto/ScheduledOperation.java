package by.it.academy.account_scheduler_service.models.dto;

import by.it.academy.account_scheduler_service.models.deserializers.LocalDateTimeDeserializer;
import by.it.academy.account_scheduler_service.models.enums.Status;
import by.it.academy.account_scheduler_service.models.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ScheduledOperation {

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

    @JsonProperty("schedule")
    private Schedule schedule;

    @JsonProperty("operation")
    private Operation operation;

    @JsonProperty("status")
    private Status status;

    @JsonIgnore
    private String username;

    public ScheduledOperation(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, Schedule schedule,
                              Operation operation, Status status, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.schedule = schedule;
        this.operation = operation;
        this.status = status;
        this.username = username;
    }

    public ScheduledOperation() {
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        ScheduledOperation that = (ScheduledOperation) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && Objects.equals(schedule, that.schedule)
                && Objects.equals(operation, that.operation)
                && status == that.status
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, schedule, operation, status, username);
    }

    @Override
    public String toString() {
        return "ScheduledOperation{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", schedule=" + schedule +
                ", operation=" + operation +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private Schedule schedule;
        private Operation operation;
        private Status status;
        private String username;

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

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ScheduledOperation build() {
            return new ScheduledOperation(this.uuid, this.dtCreate, this.dtUpdate, this.schedule, this.operation,
                    this.status, this.username);
        }
    }
}
