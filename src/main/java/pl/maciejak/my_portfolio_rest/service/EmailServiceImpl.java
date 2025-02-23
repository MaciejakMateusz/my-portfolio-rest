package pl.maciejak.my_portfolio_rest.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.service.interfaces.EmailService;

import java.util.Map;

@Component
@Getter
@Setter
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${mail.contact}")
    private String contactMail;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public ResponseEntity<?> send(Map<String, String> params) {
        String from = params.get("from");
        String subject = params.get("subject");
        String text = params.get("text");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(contactMail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        return ResponseEntity.ok().build();
    }

}
