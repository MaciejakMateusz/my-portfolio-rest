package pl.maciejak.my_portfolio_rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejak.my_portfolio_rest.service.interfaces.EmailService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> countries(@RequestBody Map<String, String> params) {
        return emailService.send(params);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}