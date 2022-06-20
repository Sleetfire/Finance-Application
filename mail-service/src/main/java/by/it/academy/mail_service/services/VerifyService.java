package by.it.academy.mail_service.services;

import by.it.academy.mail_service.exceptions.SendMessageException;
import by.it.academy.mail_service.exceptions.ValidationException;
import by.it.academy.mail_service.model.dto.VerifyMail;
import by.it.academy.mail_service.model.errors.ValidationError;
import by.it.academy.mail_service.services.api.IVerifyService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class VerifyService implements IVerifyService {

    private final JavaMailSender mailSender;

    public VerifyService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(VerifyMail verifyMail) {
        this.check(verifyMail);
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your favourite finance application.";
        MimeMessage message = this.mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(verifyMail.getEmail());
            helper.setSubject("Verify your registration");
            content = content.replace("[[name]]", verifyMail.getName());
            content = content.replace("[[URL]]", verifyMail.getLink());
            helper.setText(content, true);
            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendMessageException("Error when sending a verify message");
        }
    }

    private void check(VerifyMail verifyMail) {
        List<ValidationError> errors = new ArrayList<>();
        if (verifyMail.getEmail() == null || verifyMail.getEmail().isEmpty()) {
            errors.add(new ValidationError("email", "email is null or empty"));
        }
        if (verifyMail.getName() == null || verifyMail.getName().isEmpty()) {
            errors.add(new ValidationError("name", "name is null or empty"));
        }
        if (verifyMail.getLink() == null || verifyMail.getLink().isEmpty()) {
            errors.add(new ValidationError("link", "link is null or empty"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Report email validation error", errors);
        }
    }
}
