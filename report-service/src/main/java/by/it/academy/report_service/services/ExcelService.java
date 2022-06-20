package by.it.academy.report_service.services;

import by.it.academy.report_service.exception.IncorrectFileOutInException;
import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.rest_template.Account;
import by.it.academy.report_service.models.dto.rest_template.Currency;
import by.it.academy.report_service.models.dto.rest_template.Operation;
import by.it.academy.report_service.models.dto.rest_template.OperationCategory;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.services.api.IDocumentService;
import by.it.academy.report_service.services.api.IParamsService;
import by.it.academy.report_service.services.api.predicates.OperationCategoryPredicate;
import by.it.academy.report_service.services.api.predicates.OperationDatePredicate;
import by.it.academy.report_service.utils.DateTimeUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;

@Service
public class ExcelService implements IDocumentService<XSSFWorkbook> {

    private final IParamsService paramsService;

    public ExcelService(IParamsService paramsService) {
        this.paramsService = paramsService;
    }

    @Override
    public XSSFWorkbook create(IReport report) {
        XSSFWorkbook xssfWorkbook = this.createWithoutClose(report);
        try {
            xssfWorkbook.close();
        } catch (IOException e) {
            throw new IncorrectFileOutInException("Report creating error");
        }
        return xssfWorkbook;
    }

    @Override
    public byte[] createBin(IReport report) {
        XSSFWorkbook xssfWorkbook = this.createWithoutClose(report);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            xssfWorkbook.write(byteArrayOutputStream);
            xssfWorkbook.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw new IncorrectFileOutInException("Report creating error");
        }
        return byteArrayOutputStream.toByteArray();
    }

    private XSSFWorkbook createWithoutClose(IReport report) {
        Map<String, Object> params = report.getParams();
        List<UUID> uuidList = new ArrayList<>();
        if (params.containsKey("accounts") && params.get("accounts") instanceof Collection<?>) {
            uuidList = this.paramsService.convertStringListToUUID((List<String>) params.get("accounts"));
        }

        List<UUID> requestCategories = new ArrayList<>();
        if (params.containsKey("categories") || params.get("categories") instanceof Collection<?>) {
            requestCategories = this.paramsService.convertStringListToUUID((List<String>) params.get("categories"));
        }

        List<Account> accounts = this.paramsService.getAccountList(uuidList, report.getUsername());
        Map<UUID, List<Operation>> operationMap = this.paramsService.getOperationMap(uuidList, report.getUsername());
        Map<UUID, Currency> currencyMap = this.paramsService.getCurrencyMap(accounts);
        List<OperationCategory> categories = this.paramsService.getOperationCategoryList();
        ReportType reportType = report.getType();

        if (Objects.equals(reportType, ReportType.BALANCE)) {
            return this.createBalanceReport(accounts, operationMap, currencyMap, categories);
        }

        long from = 0;
        if (params.containsKey("from") && params.get("from") instanceof Long) {
            from = (long) params.get("from");
        }

        long to = DateTimeUtil.convertLocalDateToLong(LocalDate.now());
        if (params.containsKey("to") && params.get("to") instanceof Long) {
            to = (long) params.get("to");
        }

        Predicate<Operation> operationCategoryPredicate = new OperationCategoryPredicate(requestCategories);
        Predicate<Operation> operationDatePredicate = new OperationDatePredicate(from, to);
        Predicate<Operation> operationPredicate = operationCategoryPredicate.and(operationDatePredicate);

        if (Objects.equals(reportType, ReportType.BY_CATEGORY)) {
            return this.createCategoryReport(accounts, operationMap, currencyMap, categories, operationPredicate);
        }

        if (Objects.equals(reportType, ReportType.BY_DATE)) {
            return this.createDateReport(accounts, operationMap, currencyMap, categories, operationPredicate);
        }

        return new XSSFWorkbook();
    }

    private XSSFWorkbook createBalanceReport(List<Account> accounts, Map<UUID, List<Operation>> operationMap,
                                             Map<UUID, Currency> currencyMap, List<OperationCategory> categories) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        for (Account account : accounts) {
            XSSFSheet sheet = this.createSheet(account, xssfWorkbook);
            sheet.setDefaultColumnWidth(30);
            int rowNum = 0;
            this.createSheetTitle(xssfWorkbook, sheet, rowNum);

            for (Operation operation : operationMap.get(account.getUuid())) {
                XSSFRow newRow = this.createRow(sheet, ++rowNum);
                this.fillRow(account, operation, categories, currencyMap, xssfWorkbook, newRow);
            }

            XSSFRow lastRow = this.createRow(sheet, ++rowNum);
            XSSFCell resultTitleCell = this.createCell(lastRow, 4, CellType.STRING);
            this.setCellColorAndFont(xssfWorkbook, resultTitleCell, IndexedColors.BLUE);
            setCellValue(resultTitleCell, "Result");

            XSSFCell balanceValueCell = this.createCell(lastRow, 5, CellType.NUMERIC);
            setCellDoubleValue(balanceValueCell, account.getBalance());
        }
        return xssfWorkbook;
    }

    private XSSFWorkbook createCategoryReport(List<Account> accounts, Map<UUID, List<Operation>> operationMap,
                                              Map<UUID, Currency> currencyMap, List<OperationCategory> categories,
                                              Predicate<Operation> operationPredicate) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        operationMap = this.paramsService.filterOperationList(operationMap, accounts, operationPredicate);
        Map<UUID, Map<UUID, List<Operation>>> map = this.paramsService.groupByCategories(operationMap);

        for (Account account : accounts) {
            XSSFSheet sheet = this.createSheet(account, xssfWorkbook);
            sheet.setDefaultColumnWidth(30);
            int rowNum = 0;
            this.createSheetTitle(xssfWorkbook, sheet, rowNum);
            Map<UUID, List<Operation>> currentMap = map.get(account.getUuid());

            for (Map.Entry<UUID, List<Operation>> entry : currentMap.entrySet()) {
                for (Operation operation : entry.getValue()) {
                    XSSFRow newRow = this.createRow(sheet, ++rowNum);
                    this.fillRow(account, operation, categories, currencyMap, xssfWorkbook, newRow);
                }
                XSSFRow intermediateRow = this.createRow(sheet, ++rowNum);
                XSSFCell resultTitleCell = this.createCell(intermediateRow, 4, CellType.STRING);
                this.setCellColorAndFont(xssfWorkbook, resultTitleCell, IndexedColors.BLUE);
                setCellValue(resultTitleCell, "Result for this category");
                XSSFCell balanceValueCell = this.createCell(intermediateRow, 5, CellType.NUMERIC);
                setCellDoubleValue(balanceValueCell, this.paramsService.sumOperationListValue(entry.getValue()).doubleValue());
            }
        }
        return xssfWorkbook;
    }

    private XSSFWorkbook createDateReport(List<Account> accounts, Map<UUID, List<Operation>> operationMap,
                                          Map<UUID, Currency> currencyMap, List<OperationCategory> categories,
                                          Predicate<Operation> operationPredicate) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        operationMap = this.paramsService.filterOperationList(operationMap, accounts, operationPredicate);
        Map<UUID, Map<LocalDate, List<Operation>>> map = this.paramsService.groupByDate(operationMap);

        for (Account account : accounts) {
            XSSFSheet sheet = this.createSheet(account, xssfWorkbook);
            sheet.setDefaultColumnWidth(30);
            int rowNum = 0;
            this.createSheetTitle(xssfWorkbook, sheet, rowNum);
            Map<LocalDate, List<Operation>> currentMap = map.get(account.getUuid());

            for (Map.Entry<LocalDate, List<Operation>> entry : currentMap.entrySet()) {
                for (Operation operation : entry.getValue()) {
                    XSSFRow newRow = this.createRow(sheet, ++rowNum);
                    this.fillRow(account, operation, categories, currencyMap, xssfWorkbook, newRow);
                }
                XSSFRow intermediateRow = this.createRow(sheet, ++rowNum);
                XSSFCell resultTitleCell = this.createCell(intermediateRow, 4, CellType.STRING);
                this.setCellColorAndFont(xssfWorkbook, resultTitleCell, IndexedColors.BLUE);
                setCellValue(resultTitleCell, "Result for this date");
                XSSFCell balanceValueCell = this.createCell(intermediateRow, 5, CellType.NUMERIC);
                setCellDoubleValue(balanceValueCell, this.paramsService.sumOperationListValue(entry.getValue()).doubleValue());
            }
        }
        return xssfWorkbook;
    }

    private void createSheetTitle(XSSFWorkbook workbook, XSSFSheet sheet, int rowNum) {
        XSSFRow firstRow = this.createRow(sheet, rowNum);

        XSSFCell dateCell = this.createCell(firstRow, 0, CellType.STRING);
        setCellValue(dateCell, "Date");
        this.setCellColorAndFont(workbook, dateCell, IndexedColors.YELLOW);

        XSSFCell timeCell = this.createCell(firstRow, 1, CellType.STRING);
        setCellValue(timeCell, "Time");
        this.setCellColorAndFont(workbook, timeCell, IndexedColors.YELLOW);

        XSSFCell descriptionCell = this.createCell(firstRow, 2, CellType.STRING);
        setCellValue(descriptionCell, "Description");
        this.setCellColorAndFont(workbook, descriptionCell, IndexedColors.YELLOW);

        XSSFCell categoryCell = this.createCell(firstRow, 3, CellType.STRING);
        setCellValue(categoryCell, "Category");
        this.setCellColorAndFont(workbook, categoryCell, IndexedColors.YELLOW);

        XSSFCell currencyCell = this.createCell(firstRow, 4, CellType.STRING);
        setCellValue(currencyCell, "Currency");
        this.setCellColorAndFont(workbook, currencyCell, IndexedColors.YELLOW);

        XSSFCell valueCell = this.createCell(firstRow, 5, CellType.STRING);
        setCellValue(valueCell, "Value");
        this.setCellColorAndFont(workbook, valueCell, IndexedColors.YELLOW);
    }

    private void fillRow(Account account, Operation operation, List<OperationCategory> categories,
                         Map<UUID, Currency> currencyMap, XSSFWorkbook workbook, XSSFRow newRow) {
        XSSFCell newDateCell = this.createCell(newRow, 0, CellType.STRING);
        setCellValue(newDateCell, DateTimeUtil.setLocalDateFormat(operation.getDate().toLocalDate(), FormatStyle.SHORT));

        XSSFCell newTimeCell = this.createCell(newRow, 1, CellType.STRING);
        setCellValue(newTimeCell, DateTimeUtil.setLocalTimeFormat(operation.getDate().toLocalTime(), FormatStyle.SHORT));

        XSSFCell newDescriptionCell = this.createCell(newRow, 2, CellType.STRING);
        setCellValue(newDescriptionCell, operation.getDescription());

        XSSFCell newCategoryCell = this.createCell(newRow, 3, CellType.STRING);
        setCellValue(newCategoryCell, this.paramsService.getOperationCategory(categories, operation.getCategory()).getTitle());

        XSSFCell newCurrencyCell = this.createCell(newRow, 4, CellType.STRING);
        setCellValue(newCurrencyCell, currencyMap.get(account.getCurrency()).getTitle());

        XSSFCell newValueCell = this.createCell(newRow, 5, CellType.NUMERIC);
        if (operation.getValue().compareTo(BigDecimal.ZERO) < 1) {
            this.setCellColorAndFont(workbook, newValueCell, IndexedColors.RED);
        } else {
            this.setCellColorAndFont(workbook, newValueCell, IndexedColors.GREEN);
        }
        setCellDoubleValue(newValueCell, operation.getValue().doubleValue());
    }

    private XSSFSheet createSheet(Account account, XSSFWorkbook workbook) {
        return workbook.createSheet(account.getTitle());
    }

    private XSSFRow createRow(XSSFSheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    private XSSFCell createCell(XSSFRow row, int columnNum, CellType cellType) {
        return row.createCell(columnNum, cellType);
    }

    private void setCellValue(XSSFCell cell, String value) {
        cell.setCellValue(value);
    }

    private void setCellDoubleValue(XSSFCell cell, double value) {
        cell.setCellValue(value);
    }

    private XSSFCell setCellColorAndFont(XSSFWorkbook workbook, XSSFCell cell, IndexedColors indexedColors) {
        CellStyle cellStyle = this.createCellStyle(workbook);
        if (indexedColors != null) {
            cellStyle.setFillForegroundColor(indexedColors.index);
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private CellStyle createCellStyle(XSSFWorkbook xssfWorkbook) {
        return xssfWorkbook.createCellStyle();
    }

}
