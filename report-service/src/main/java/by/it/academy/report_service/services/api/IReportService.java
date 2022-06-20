package by.it.academy.report_service.services.api;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.PageDTO;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IReportService {

    /**
     * Asynchronous report generation
     * @param reportMap map with report's params
     * @param type report's type
     * @return report
     */
    IReport async(Map<String, Object> reportMap, ReportType type);

    /**
     * Synchronous report generation
     * @param reportMap map with report's params
     * @param type report's type
     * @return report
     */
    IReport sync(Map<String, Object> reportMap, ReportType type);

    /**
     * Start report's creating for saving in database
     * @param report report dto
     * @return report from database
     */
    IReport run(IReport report);

    /**
     * Saving report in database
     * @param report report dto
     * @return report from database
     */
    IReport create(IReport report);

    /**
     * Getting page of reports
     * @param pageable param for pagination
     * @return page of reports
     */
    PageDTO<IReport> getPage(Pageable pageable);

    /**
     * Getting report by uuid
     * @param uuid report's uuid
     * @return report from database
     */
    IReport get(UUID uuid);

    /**
     * Getting list of all reports
     * @return list of reports
     */
    List<IReport> getAll();

    /**
     * Added generated document to report
     * @param uuid report's uuid
     * @param dtUpdate report's update date-time
     * @param documentUrl generated document's url in cloud
     * @param type report's status
     * @return report from database
     */
    IReport addDocumentToReport(UUID uuid, long dtUpdate, String documentUrl, StatusType type);

    /**
     * Getting url for report's downloading from cloud storage
     * @param uuid report's uuid
     * @return url in cloud
     */
    String download(UUID uuid);

    /**
     * Report's readiness checking
     * @param uuid report's uuid
     * @return true if report is ready and false is if report isn't ready
     */
    boolean isReady(UUID uuid);

}
