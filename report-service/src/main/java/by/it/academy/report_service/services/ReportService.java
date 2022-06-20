package by.it.academy.report_service.services;

import by.it.academy.report_service.exception.EssenceNotFoundException;
import by.it.academy.report_service.exception.EssenceUpdateException;
import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.PageDTO;
import by.it.academy.report_service.models.dto.Report;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import by.it.academy.report_service.repositories.api.IReportRepository;
import by.it.academy.report_service.repositories.entities.ReportEntity;
import by.it.academy.report_service.services.api.IReportService;
import by.it.academy.report_service.services.api.execution.api.ChoiceReport;
import by.it.academy.report_service.services.api.execution.api.IReportExecution;
import by.it.academy.report_service.services.api.execution.api.ReportResultWrapper;
import by.it.academy.report_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@Transactional(readOnly = true)
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;
    private final ConversionService conversionService;
    private final ChoiceReport choiceReport;
    private final UserHolder userHolder;
    private final RequestService requestService;
    private static final Logger logger = LogManager.getLogger(ReportService.class);

    @Value(value = "${cloud-storage.cloud_name}")
    private String cloudName;
    @Value(value = "${cloud-storage.api_key}")
    private String apiKey;
    @Value(value = "${cloud-storage.api_secret}")
    private String apiSecret;

    public ReportService(IReportRepository reportRepository, ConversionService conversionService,
                         ChoiceReport choiceReport, UserHolder userHolder,
                         RequestService requestService) {
        this.reportRepository = reportRepository;
        this.conversionService = conversionService;
        this.choiceReport = choiceReport;
        this.userHolder = userHolder;
        this.requestService = requestService;
    }

    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "cloud_name",
            "api_key", "api_key",
            "api_secret", "api_secret",
            "secure", true));

    @Override
    @Transactional
    public IReport async(Map<String, Object> reportMap, ReportType type) {
        IReportExecution iReportExecution = choiceReport.get(type);
        IReport resultMeta = iReportExecution.create(reportMap);
        IReport createdReport = this.create(resultMeta);

        Runnable runnable = () -> {
            ReportResultWrapper run = iReportExecution.run(resultMeta);
            IReport report = this.get(createdReport.getUuid());
            String url = this.getUrlFromCloud(run.getBin(), report.getType());
            IReport savedReport = this.addDocumentToReport(report.getUuid(),
                    DateTimeUtil.convertLocalDateTimeToLong(report.getDtUpdate()), url, StatusType.DONE);
            this.requestService.sendReportToMail(savedReport);
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        executorService.schedule(runnable, 10, TimeUnit.SECONDS);
        executorService.shutdown();

        return createdReport;
    }

    @Override
    @Transactional
    public IReport sync(Map<String, Object> reportMap, ReportType type) {
        IReportExecution iReportExecution = choiceReport.get(type);
        IReport resultMeta = iReportExecution.create(reportMap);
        resultMeta.setType(type);

        resultMeta = this.create(resultMeta);
        IReport savedReport = run(resultMeta);
        this.requestService.sendReportToMail(savedReport);
        return savedReport;
    }

    @Override
    @Transactional
    public IReport run(IReport report) {
        IReportExecution iReportExecution = choiceReport.get(report.getType());

        ReportResultWrapper run = iReportExecution.run(report);
        String url = this.getUrlFromCloud(run.getBin(), run.getReport().getType());
        return this.addDocumentToReport(report.getUuid(), DateTimeUtil.convertLocalDateTimeToLong(report.getDtUpdate()),
                url, StatusType.DONE);
    }

    @Override
    public PageDTO<IReport> getPage(Pageable pageable) {
        Page<ReportEntity> reportEntityPage = this.reportRepository
                .findAllByUsername(pageable, this.userHolder.getUser().getUsername());
        List<ReportEntity> reportEntityList = reportEntityPage.getContent();
        List<IReport> reportList = new ArrayList<>();
        if (reportEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Entities are not exist");
        }
        reportEntityList.forEach(reportEntity -> reportList.add(this.conversionService.convert(reportEntity, Report.class)));
        return PageDTO.Builder.createBuilder(IReport.class)
                .setNumber(reportEntityPage.getNumber())
                .setSize(reportEntityPage.getSize())
                .setTotalPages(reportEntityPage.getTotalPages())
                .setTotalElements(Math.toIntExact(reportEntityPage.getTotalElements()))
                .setFirst(reportEntityPage.isFirst())
                .setNumberOfElements(reportEntityPage.getNumberOfElements())
                .setLast(reportEntityPage.isLast())
                .setContent(reportList)
                .build();
    }

    @Override
    public IReport get(UUID uuid) {
        Optional<ReportEntity> optionalReportEntity = this.reportRepository.findById(uuid);
        if (optionalReportEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalReportEntity.get(), Report.class);
    }

    @Override
    public List<IReport> getAll() {
        List<ReportEntity> reportEntityList = this.reportRepository
                .findAllByUsername(this.userHolder.getUser().getUsername());
        if (reportEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        List<IReport> reportList = new ArrayList<>();
        reportEntityList.forEach(reportEntity -> reportList.add(this.conversionService.convert(reportEntity, Report.class)));
        return reportList;
    }

    @Override
    @Transactional
    public IReport addDocumentToReport(UUID uuid, long dtUpdate, String documentUrl, StatusType type) {
        IReport report = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(report.getDtUpdate())) {
            throw new EssenceUpdateException("Date-times do not match");
        }
        report.setUrl(documentUrl);
        report.setStatus(type);
        ReportEntity reportEntity = this.conversionService.convert(report, ReportEntity.class);
        if (reportEntity != null) {
            reportEntity = this.reportRepository.save(reportEntity);
        }
        return conversionService.convert(reportEntity, Report.class);
    }

    @Override
    public String download(UUID uuid) {
        IReport report = this.get(uuid);
        return report.getUrl();
    }

    @Override
    public boolean isReady(UUID uuid) {
        IReport report = this.get(uuid);
        return Objects.equals(report.getStatus(), StatusType.DONE);
    }

    private String getUrlFromCloud(Object object, ReportType type) {
        String fileName = UUID.randomUUID() + ".xlsx";
        String resourceType = "raw";
        if (Objects.equals(type, ReportType.CATEGORY_DIAGRAM) || Objects.equals(type, ReportType.DATE_DIAGRAM)) {
            fileName = UUID.randomUUID() + ".png";
            resourceType = "image";
        }
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(object, ObjectUtils.asMap("resource_type", resourceType, "public_id", fileName));
        } catch (IOException e) {
            logger.error("Error loading the report with file name: {}. {}", fileName, e.getMessage());
        }
        if (uploadResult != null) {
            return uploadResult.get("url").toString();
        } else {
            return "";
        }
    }

    @Transactional
    @Override
    public IReport create(IReport report) {
        LocalDateTime now = LocalDateTime.now();
        report.setDtCreate(now);
        report.setDtUpdate(now);
        report.setStatus(StatusType.LOADED);
        report.setUsername(this.userHolder.getUser().getUsername());
        ReportEntity reportEntity = this.conversionService.convert(report, ReportEntity.class);
        if (reportEntity != null) {
            reportEntity = this.reportRepository.save(reportEntity);
            logger.info("Report with uuid: {} was create", reportEntity.getUuid());
        }
        return this.conversionService.convert(reportEntity, Report.class);
    }
}
