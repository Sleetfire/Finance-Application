package by.it.academy.report_service.services.api.execution;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.Report;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import by.it.academy.report_service.services.ExcelService;
import by.it.academy.report_service.services.api.execution.api.IReportExecution;
import by.it.academy.report_service.services.api.execution.api.ReportResultWrapper;
import by.it.academy.report_service.utils.ParamUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BalanceReportExecution implements IReportExecution {

    private final ExcelService excelService;

    public BalanceReportExecution(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Override
    public IReport create(Map<String, Object> paramMap) {
        ParamUtil.checkBalance(paramMap);

        return Report.Builder.createBuilder()
                .setStatus(StatusType.PROGRESS)
                .setType(ReportType.BALANCE)
                .setDescription("Report by balances")
                .setParams(paramMap)
                .build();
    }

    @Override
    public ReportResultWrapper run(IReport report) {
        byte[] bin = this.excelService.createBin(report);
        return new ReportResultWrapper(bin, report);
    }
}
