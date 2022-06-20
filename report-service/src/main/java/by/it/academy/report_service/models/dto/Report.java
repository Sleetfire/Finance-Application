package by.it.academy.report_service.models.dto;

import by.it.academy.report_service.models.deserializers.LocalDateTimeDeserializer;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import by.it.academy.report_service.models.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Report implements IReport {

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
    private StatusType status;

    @JsonProperty("type")
    private ReportType type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("params")
    private Map<String, Object> params;

    @JsonIgnore
    private String url;

    @JsonIgnore
    private String username;

    public Report(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, StatusType status, ReportType type,
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

    public Report() {
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
        Report report = (Report) o;
        return Objects.equals(uuid, report.uuid)
                && Objects.equals(dtCreate, report.dtCreate)
                && Objects.equals(dtUpdate, report.dtUpdate)
                && status == report.status && type == report.type
                && Objects.equals(description, report.description)
                && Objects.equals(params, report.params)
                && Objects.equals(url, report.url)
                && Objects.equals(username, report.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, status, type, description, params, url, username);
    }

    @Override
    public String toString() {
        return "Report{" +
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

        public Report build() {
            return new Report(this.uuid, this.dtCreate, this.dtUpdate, this.status, this.type, this.description,
                    this.params, this.url, this.username);
        }
    }
}
