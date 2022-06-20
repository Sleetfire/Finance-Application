package by.it.academy.mail_service.services;

import by.it.academy.mail_service.exceptions.InputOutputException;
import by.it.academy.mail_service.exceptions.SendMessageException;
import by.it.academy.mail_service.exceptions.ValidationException;
import by.it.academy.mail_service.model.dto.ReportEmail;
import by.it.academy.mail_service.model.errors.ValidationError;
import by.it.academy.mail_service.services.api.IReportEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReportEmailService implements IReportEmailService {

    private final JavaMailSender mailSender;

    public ReportEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(ReportEmail reportEmail) {
        this.check(reportEmail);
        String text = String.format("Hello, it's your %s. Have a nice day!", reportEmail.getDescription().toLowerCase(Locale.ROOT));
        MimeMessage message = this.mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(reportEmail.getEmail());
            helper.setSubject("Sending reports");
            helper.setText(text);

            String attachmentTitle = this.getFileTitle(reportEmail.getFileUrl());

            helper.addAttachment(attachmentTitle, this.getMultipartFile(reportEmail.getFileUrl()));

            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendMessageException("Error when sending a report message");
        }
    }

    private MultipartFile getMultipartFile(String url) {
        byte[] dataBuffer;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        ) {
            dataBuffer = in.readAllBytes();
        } catch (IOException e) {
            throw new InputOutputException("Byte array creating error for multipart file");
        }
        return new MockMultipartFile("report", dataBuffer);
    }

    private String getFileTitle(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

    private void check(ReportEmail reportEmail) {
        List<ValidationError> errors = new ArrayList<>();
        if (reportEmail.getFileUrl() == null || reportEmail.getFileUrl().isEmpty()) {
            errors.add(new ValidationError("file_url", "file_url is null or empty"));
        }
        if (reportEmail.getEmail() == null || reportEmail.getEmail().isEmpty()) {
            errors.add(new ValidationError("email", "email is null or empty"));
        }
        if (reportEmail.getDescription() == null || reportEmail.getDescription().isEmpty()) {
            errors.add(new ValidationError("description", "description is null or empty"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Report email validation error", errors);
        }

    }
}
