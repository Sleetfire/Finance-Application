package by.it.academy.report_service.services.api.execution;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.Report;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import by.it.academy.report_service.services.ExcelService;
import by.it.academy.report_service.services.api.execution.api.IReportExecution;
import by.it.academy.report_service.services.api.execution.api.ReportResultWrapper;
import by.it.academy.report_service.utils.DateTimeUtil;
import by.it.academy.report_service.utils.ParamUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CategoryReportExecution implements IReportExecution {

    private final ExcelService excelService;

    public CategoryReportExecution(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Override
    public IReport create(Map<String, Object> paramMap) {
        ParamUtil.checkDateAndCategories(paramMap);

        return Report.Builder.createBuilder()
                .setStatus(StatusType.PROGRESS)
                .setType(ReportType.BY_CATEGORY)
                .setDescription(this.createDescription(paramMap, "Report by categories"))
                .setParams(paramMap)
                .build();
    }

    @Override
    public ReportResultWrapper run(IReport report) {
        byte[] bin = this.excelService.createBin(report);
        return new ReportResultWrapper(bin, report);
    }

}
