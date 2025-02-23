package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmailService {
    ResponseEntity<?> send(Map<String, String> params);
}
