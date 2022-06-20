package by.it.academy.report_service.services.api.execution;

import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.Report;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.models.enums.StatusType;
import by.it.academy.report_service.services.DiagramService;
import by.it.academy.report_service.services.api.execution.api.IReportExecution;
import by.it.academy.report_service.services.api.execution.api.ReportResultWrapper;
import by.it.academy.report_service.utils.ParamUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CategoryDiagramReportExecution implements IReportExecution {

    private final DiagramService diagramService;

    public CategoryDiagramReportExecution(DiagramService diagramService) {
        this.diagramService = diagramService;
    }

    @Override
    public IReport create(Map<String, Object> paramMap) {
        ParamUtil.checkDateAndCategories(paramMap);
        return Report.Builder.createBuilder()
                .setStatus(StatusType.PROGRESS)
                .setType(ReportType.CATEGORY_DIAGRAM)
                .setDescription(this.createDescription(paramMap, "Categories diagram"))
                .setParams(paramMap)
                .build();
    }

    @Override
    public ReportResultWrapper run(IReport report) {
        byte[] bin = this.diagramService.createBin(report);
        return new ReportResultWrapper(bin, report);
    }
}
