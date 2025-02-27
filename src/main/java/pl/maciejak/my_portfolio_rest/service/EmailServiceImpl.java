package pl.maciejak.my_portfolio_rest.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.service.interfaces.EmailService;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${mail.contact}")
    private String contactMail;

    @Value("${mail.no-reply}")
    private String noReply;

    @Override
    public ResponseEntity<?> send(Map<String, String> params) {
        String from = params.get("from");
        String subject = params.get("subject");
        String text = params.get("text");

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(noReply);
            helper.setTo(contactMail);
            helper.setSubject(subject);
            helper.setText(buildHtmlEmail(from, subject, text), true);

            emailSender.send(message);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to send email");
        }
    }

    private String buildHtmlEmail(String from, String subject, String text) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email Notification</title>
                    <style>
                        body {
                            margin: 0;
                            padding: 0;
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background-color: #f9f9f9;
                            color: #202124;
                            line-height: 1.6;
                        }
                        .email-wrapper {
                            width: 100%%;
                            padding: 20px;
                        }
                        .container {
                            max-width: 600px;
                            background-color: #ffffff;
                            margin: auto;
                            border-radius: 8px;
                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                            padding: 20px;
                        }
                        header {
                            background-color: #0a66c2;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px;
                        }
                        header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        main {
                            padding: 20px;
                        }
                        .info {
                            margin: 15px 0;
                            padding: 0 20px;
                        }
                        .info p {
                            margin: 0 0 10px;
                        }
                        .info strong {
                            display: inline-block;
                            width: 80px;
                        }
                        .message {
                            padding: 0 20px;
                        }
                        section {
                            margin-top: 10px;
                        }
                        footer {
                            background-color: #f1f3f4;
                            text-align: center;
                            padding: 15px;
                            font-size: 12px;
                            color: #5f6368;
                        }
                        footer a {
                            color: #0a66c2;
                            text-decoration: none;
                        }
                        a:focus {
                            outline: 2px solid #0a66c2;
                            outline-offset: 2px;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-wrapper">
                        <div class="container">
                            <header>
                                <h1>Nowa wiadomość</h1>
                            </header>
                            <main>
                                <div class="info">
                                    <p><strong>Od:</strong> %s</p>
                                    <p><strong>Temat:</strong> %s</p>
                                </div>
                                <section class="message">
                                    <p>%s</p>
                                </section>
                            </main>
                            <footer>
                                <p>Email wygenerowany automatycznie, nie odpowiadaj na niego.</p>
                            </footer>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(from, subject, text);
    }

}