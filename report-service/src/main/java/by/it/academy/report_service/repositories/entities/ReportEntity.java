package by.it.academy.report_service.repositories.entities;

import by.it.academy.report_service.models.converters.HashMapConverter;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "report", schema = "report_service")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReportType type;

    @Column(name = "description")
    private String description;

    @Column(name = "params")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> params;

    @Column(name = "url")
    private String url;

    @Column(name = "username")
    private String username;

    public ReportEntity() {
    }

    public ReportEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, StatusType status, ReportType type,
                        String description, Map<String, Object> params, String url, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
        this.url = url;
        this.username = username;
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

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        ReportEntity that = (ReportEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && status == that.status && type == that.type
                && Objects.equals(description, that.description)
                && Objects.equals(params, that.params)
                && Objects.equals(url, that.url)
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, status, type, description, params, url, username);
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", status=" + status +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", params=" + params +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private StatusType status;
        private ReportType type;
        private String description;
        private Map<String, Object> params;
        private String url;
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

        public Builder setStatus(StatusType status) {
            this.status = status;
            return this;
        }

        public Builder setType(ReportType type) {
            this.type = type;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ReportEntity build() {
            return new ReportEntity(this.uuid, this.dtCreate, this.dtUpdate, this.status, this.type, this.description,
                    this.params, this.url, this.username);
        }
    }


}
