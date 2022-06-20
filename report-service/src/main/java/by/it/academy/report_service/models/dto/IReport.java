package by.it.academy.report_service.models.dto;

import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public interface IReport {
    UUID getUuid();

    void setUuid(UUID uuid);

    LocalDateTime getDtCreate();

    void setDtCreate(LocalDateTime dtCreate);

    LocalDateTime getDtUpdate();

    void setDtUpdate(LocalDateTime dtUpdate);

    StatusType getStatus();

    void setStatus(StatusType status);

    ReportType getType();

    void setType(ReportType type);

    String getDescription();

    void setDescription(String description);

    Map<String, Object> getParams();

    void setParams(Map<String, Object> params);

    String getUrl();

    void setUrl(String url);

    String getUsername();

    void setUsername(String username);
}
