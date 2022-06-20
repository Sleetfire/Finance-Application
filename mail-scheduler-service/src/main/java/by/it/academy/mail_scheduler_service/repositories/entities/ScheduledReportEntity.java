package by.it.academy.mail_scheduler_service.repositories.entities;

import by.it.academy.mail_scheduler_service.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "scheduled_report", schema = "mail_scheduler_service")
public class ScheduledReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name= "schedule_uuid")
    private ScheduleEntity scheduleEntity;

    @OneToOne
    @JoinColumn(name="report_uuid")
    private ReportEntity reportEntity;

    @Column(name = "username")
    private String username;

    public ScheduledReportEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, Status status, ScheduleEntity scheduleEntity,
                                 ReportEntity reportEntity, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.scheduleEntity = scheduleEntity;
        this.reportEntity = reportEntity;
        this.username = username;
    }

    public ScheduledReportEntity() {
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

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public ReportEntity getReportEntity() {
        return reportEntity;
    }

    public void setReportEntity(ReportEntity reportEntity) {
        this.reportEntity = reportEntity;
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
        ScheduledReportEntity that = (ScheduledReportEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && status == that.status
                && Objects.equals(scheduleEntity, that.scheduleEntity)
                && Objects.equals(reportEntity, that.reportEntity)
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, status, scheduleEntity, reportEntity, username);
    }

    @Override
    public String toString() {
        return "ScheduledReportEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", status=" + status +
                ", scheduleEntity=" + scheduleEntity +
                ", reportEntity=" + reportEntity +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private Status status;
        private ScheduleEntity scheduleEntity;
        private ReportEntity reportEntity;
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

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setScheduleEntity(ScheduleEntity scheduleEntity) {
            this.scheduleEntity = scheduleEntity;
            return this;
        }

        public Builder setReportEntity(ReportEntity reportEntity) {
            this.reportEntity = reportEntity;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ScheduledReportEntity build() {
            return new ScheduledReportEntity(this.uuid, this.dtCreate, this.dtUpdate, this.status, this.scheduleEntity,
                    this.reportEntity, this.username);
        }
    }
}
