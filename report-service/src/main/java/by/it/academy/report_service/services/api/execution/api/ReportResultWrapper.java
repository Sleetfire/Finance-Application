package by.it.academy.report_service.services.api.execution.api;

import by.it.academy.report_service.models.dto.IReport;

public class ReportResultWrapper {
    private byte[] bin;
    private IReport report;

    public ReportResultWrapper(byte[] bin, IReport report) {
        this.bin = bin;
        this.report = report;
    }

    public byte[] getBin() {
        return bin;
    }

    public IReport getReport() {
        return report;
    }
}
