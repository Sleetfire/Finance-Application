package by.it.academy.mail_scheduler_service.services;

import by.it.academy.mail_scheduler_service.exceptions.EssenceDeleteException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceNotFoundException;
import by.it.academy.mail_scheduler_service.exceptions.EssenceUpdateException;
import by.it.academy.mail_scheduler_service.model.dto.Report;
import by.it.academy.mail_scheduler_service.repositories.api.IReportRepository;
import by.it.academy.mail_scheduler_service.repositories.entities.ReportEntity;
import by.it.academy.mail_scheduler_service.services.api.IReportService;
import by.it.academy.mail_scheduler_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(ReportService.class);

    public ReportService(IReportRepository reportRepository, ConversionService conversionService) {
        this.reportRepository = reportRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Report create(Report report) {
        if (report.getDtCreate() == null || report.getDtUpdate() == null) {
            LocalDateTime now = LocalDateTime.now();
            report.setDtCreate(now);
            report.setDtUpdate(now);
        }
        ReportEntity reportEntity = this.conversionService.convert(report, ReportEntity.class);
        if (reportEntity != null) {
            reportEntity = this.reportRepository.save(reportEntity);
            logger.info("Report with uuid: {} was create", reportEntity.getUuid());
        }
        return this.conversionService.convert(reportEntity, Report.class);
    }

    @Override
    public Report update(UUID uuid, long dtUpdate, Report updatedReport) {
        Report report = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(report.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times don't match");
        }
        if (!Objects.equals(updatedReport.getType(), report.getType())) {
            report.setType(updatedReport.getType());
        }
        if (!Objects.equals(updatedReport.getParams(), report.getParams())) {
            report.setParams(updatedReport.getParams());
        }
        ReportEntity reportEntity = this.conversionService.convert(report, ReportEntity.class);
        if (reportEntity == null) {
            throw new EssenceUpdateException("Incorrect update");
        }
        reportEntity = this.reportRepository.save(reportEntity);
        logger.info("Report with uuid: {} was update", reportEntity.getUuid());
        return this.conversionService.convert(reportEntity, Report.class);
    }

    @Override
    public Report get(UUID uuid) {
        Optional<ReportEntity> optionalReportEntity = this.reportRepository.findById(uuid);
        if (optionalReportEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalReportEntity.get(), Report.class);
    }

    @Override
    public void delete(UUID uuid, long dtUpdate) {
        Report report = this.get(uuid);
        if (DateTimeUtil.convertLocalDateTimeToLong(report.getDtUpdate()) != dtUpdate) {
            throw new EssenceDeleteException("Date-times don't match");
        }
        ReportEntity reportEntity = this.conversionService.convert(report, ReportEntity.class);
        if (reportEntity == null) {
            throw new EssenceDeleteException("Incorrect delete");
        }
        this.reportRepository.delete(reportEntity);
        logger.info("Report with uuid: {} was delete", reportEntity.getUuid());
    }
}
