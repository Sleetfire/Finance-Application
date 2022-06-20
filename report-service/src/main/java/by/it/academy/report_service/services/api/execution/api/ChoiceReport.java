package by.it.academy.report_service.services.api.execution.api;

import by.it.academy.report_service.exception.IncorrectInputParametersException;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.services.api.execution.*;
import org.springframework.stereotype.Component;

@Component
public class ChoiceReport {

    private final BalanceReportExecution balanceReportExecution;
    private final CategoryReportExecution categoryReportExecution;
    private final DateReportExecution dateReportExecution;
    private final CategoryDiagramReportExecution categoryDiagramReportExecution;
    private final DateDiagramReportExecution dateDiagramReportExecution;

    public ChoiceReport(BalanceReportExecution balanceReportExecution, CategoryReportExecution categoryReportExecution,
                        DateReportExecution dateReportExecution, CategoryDiagramReportExecution categoryDiagramReportExecution,
                        DateDiagramReportExecution dateDiagramReportExecution) {
        this.balanceReportExecution = balanceReportExecution;
        this.categoryReportExecution = categoryReportExecution;
        this.dateReportExecution = dateReportExecution;
        this.categoryDiagramReportExecution = categoryDiagramReportExecution;
        this.dateDiagramReportExecution = dateDiagramReportExecution;
    }

    public IReportExecution get(ReportType type) {
        switch (type) {
            case BALANCE:
                return this.balanceReportExecution;
            case BY_DATE:
                return this.dateReportExecution;
            case BY_CATEGORY:
                return this.categoryReportExecution;
            case CATEGORY_DIAGRAM:
                return this.categoryDiagramReportExecution;
            case DATE_DIAGRAM:
                return this.dateDiagramReportExecution;
            default:
                throw new IncorrectInputParametersException("Report type doesn't exist");
        }
    }
}
