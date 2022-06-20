package by.it.academy.mail_service.services.api;

import by.it.academy.mail_service.model.dto.VerifyMail;

public interface IVerifyService {

    /**
     * Sending verify massage to email
     * @param verifyMail verify mail dto
     */
    void send(VerifyMail verifyMail);

}
