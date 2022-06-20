package by.it.academy.report_service.services;

import by.it.academy.report_service.exception.IncorrectFileOutInException;
import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.services.api.IDocumentService;
import by.it.academy.report_service.services.api.IParamsService;
import by.it.academy.report_service.services.api.IRequestService;
import by.it.academy.report_service.services.api.predicates.OperationDatePredicate;
import by.it.academy.report_service.utils.DateTimeUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

@Service
public class DiagramService implements IDocumentService<JFreeChart> {

    private final IParamsService paramsService;
    private final IRequestService requestService;

    public DiagramService(IParamsService paramsService, IRequestService requestService) {
        this.paramsService = paramsService;
        this.requestService = requestService;
    }

    @Override
    public JFreeChart create(IReport report) {
        Map<String, Object> params = report.getParams();
        List<UUID> uuidList = new ArrayList<>();
        if (params.containsKey("accounts") && params.get("accounts") instanceof Collection<?>) {
            uuidList = this.paramsService.convertStringListToUUID((List<String>) params.get("accounts"));
        }
        List<Account> accounts = this.paramsService.getAccountList(uuidList, report.getUsername());
        DefaultPieDataset dataset = this.createDiagramDataSet(report);

        String title = accounts.get(0).getTitle() + ", " + this.requestService.getCurrency(accounts.get(0).getCurrency()).getTitle();
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setNoDataMessage("No data");

        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        return chart;
    }

    @Override
    public byte[] createBin(IReport report) {
        JFreeChart chart = this.create(report);
        chart.setBorderVisible(true);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsPNG(out, chart, 640, 480);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IncorrectFileOutInException("Error in the process of creating a byte array");
        }
    }

    private DefaultPieDataset createDiagramDataSet(IReport report) {
        Map<String, Object> params = report.getParams();
        DefaultPieDataset dataset = new DefaultPieDataset();

        List<UUID> uuidList = new ArrayList<>();
        if (params.containsKey("accounts") && params.get("accounts") instanceof Collection<?>) {
            uuidList = this.paramsService.convertStringListToUUID((List<String>) params.get("accounts"));
        }

        long from = 0;
        if (params.containsKey("from") && params.get("from") instanceof Long) {
            from = (long) params.get("from");
        }

        long to = DateTimeUtil.convertLocalDateToLong(LocalDate.now());
        if (params.containsKey("to") && params.get("to") instanceof Long) {
            to = (long) params.get("to");
        }

        Map<UUID, List<Operation>> operationMap = this.paramsService.getOperationMap(uuidList, report.getUsername());
        List<OperationCategory> categories = this.paramsService.getOperationCategoryList();
        List<Account> accounts = this.paramsService.getAccountList(uuidList, report.getUsername());

        Predicate<Operation> operationDatePredicate = new OperationDatePredicate(from, to);
        operationMap = this.paramsService.filterOperationList(operationMap, accounts, operationDatePredicate);

        Map<UUID, Map<UUID, List<Operation>>> groupByParamCategory;
        Map<UUID, Map<LocalDate, List<Operation>>> groupByDate;
        if (report.getType().equals(ReportType.CATEGORY_DIAGRAM)) {
            groupByParamCategory = paramsService.groupByCategories(operationMap);
            for (Map.Entry<UUID, Map<UUID, List<Operation>>> entry : groupByParamCategory.entrySet()) {
                for (Map.Entry<UUID, List<Operation>> operationEntry : entry.getValue().entrySet()) {
                    dataset.setValue(this.getCategoryTitle(operationEntry.getKey(), categories),
                           Math.abs(this.paramsService.sumOperationListValue(operationEntry.getValue()).doubleValue()));
                }
            }

        } else if (report.getType().equals(ReportType.DATE_DIAGRAM)) {
            groupByDate = paramsService.groupByDate(operationMap);
            for (Map.Entry<UUID, Map<LocalDate, List<Operation>>> entry : groupByDate.entrySet()) {
                for (Map.Entry<LocalDate, List<Operation>> operationEntry : entry.getValue().entrySet()) {
                    dataset.setValue(DateTimeUtil.setLocalDateFormat(operationEntry.getKey(), FormatStyle.SHORT),
                            Math.abs(this.paramsService.sumOperationListValue(operationEntry.getValue()).doubleValue()));
                }
            }
        }

        return dataset;
    }

    private Comparable<String> getCategoryTitle(UUID uuid, List<OperationCategory> categories) {
        for (OperationCategory category : categories) {
            if (uuid.compareTo(category.getUuid()) == 0) {
                return category.getTitle();
            }
        }
        return "";
    }

}
