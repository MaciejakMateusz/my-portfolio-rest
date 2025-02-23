package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TranslationsService {
    ResponseEntity<?> translate(Map<String, Object> params);
}
