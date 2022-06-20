package by.it.academy.mail_service.services.api;

import by.it.academy.mail_service.model.dto.ReportEmail;

public interface IReportEmailService {

    /**
     * Sending report to email
     * @param reportEmail report email dto
     */
    void send(ReportEmail reportEmail);

}
