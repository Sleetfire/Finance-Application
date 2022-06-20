package by.it.academy.report_service.services.api.execution.api;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.utils.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Map;

public interface IReportExecution {

    /**
     * Report creating
     * @param paramMap params for report creating
     * @return report
     */
    IReport create(Map<String, Object> paramMap);

    /**
     * Start report generated and wrap result
     * @param report report dto
     * @return wrapped report
     */
    ReportResultWrapper run(IReport report);

    /**
     * Create report's description
     * @param paramMap params for report creating
     * @param text text for description
     * @return created description
     */
    default String createDescription(Map<String, Object> paramMap, String text) {
        LocalDateTime from = DateTimeUtil.convertLongToLocalDateTime(Long.parseLong(paramMap.get("from").toString()));
        LocalDateTime to = DateTimeUtil.convertLongToLocalDateTime(Long.parseLong(paramMap.get("to").toString()));
        return text + " from " + DateTimeUtil.setLocalDateFormat(from.toLocalDate(), FormatStyle.SHORT)
                + " to " + DateTimeUtil.setLocalDateFormat(to.toLocalDate(), FormatStyle.SHORT);
    }
}
