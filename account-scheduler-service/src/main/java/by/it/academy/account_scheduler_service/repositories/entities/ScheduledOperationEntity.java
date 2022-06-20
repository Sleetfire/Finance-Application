package by.it.academy.account_scheduler_service.repositories.entities;

import by.it.academy.account_scheduler_service.models.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "scheduled_operation", schema = "account_scheduler_service")
public class ScheduledOperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @OneToOne
    @JoinColumn(name="schedule_uuid")
    private ScheduleEntity scheduleEntity;

    @OneToOne
    @JoinColumn(name="operation_uuid")
    private OperationEntity operationEntity;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String username;

    public ScheduledOperationEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, ScheduleEntity scheduleEntity,
                                    OperationEntity operationEntity, Status status, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.scheduleEntity = scheduleEntity;
        this.operationEntity = operationEntity;
        this.status = status;
        this.username = username;
    }

    public ScheduledOperationEntity() {
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

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public OperationEntity getOperationEntity() {
        return operationEntity;
    }

    public void setOperationEntity(OperationEntity operationEntity) {
        this.operationEntity = operationEntity;
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
        ScheduledOperationEntity that = (ScheduledOperationEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && Objects.equals(scheduleEntity, that.scheduleEntity)
                && Objects.equals(operationEntity, that.operationEntity)
                && status == that.status
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, scheduleEntity, operationEntity, status, username);
    }

    @Override
    public String toString() {
        return "ScheduledOperationEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", scheduleEntity=" + scheduleEntity +
                ", operationEntity=" + operationEntity +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private ScheduleEntity scheduleEntity;
        private OperationEntity operationEntity;
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

        public Builder setScheduleEntity(ScheduleEntity scheduleEntity) {
            this.scheduleEntity = scheduleEntity;
            return this;
        }

        public Builder setOperationEntity(OperationEntity operationEntity) {
            this.operationEntity = operationEntity;
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

        public ScheduledOperationEntity build() {
            return new ScheduledOperationEntity(this.uuid, this.dtCreate, this.dtUpdate,  this.scheduleEntity,
                    this.operationEntity, this.status, this.username);
        }
    }
}
