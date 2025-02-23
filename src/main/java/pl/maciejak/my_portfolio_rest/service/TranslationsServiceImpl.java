package pl.maciejak.my_portfolio_rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.maciejak.my_portfolio_rest.service.interfaces.TranslationsService;

import java.util.Map;

@Service
public class TranslationsServiceImpl implements TranslationsService {

    @Value("${DEEPL_API_KEY}")
    private String AUTH_KEY;

    private final RestTemplate restTemplate;

    public TranslationsServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<?> translate(Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "DeepL-Auth-Key " + AUTH_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(
                "https://api-free.deepl.com/v2/translate",
                HttpMethod.POST,
                entity,
                String.class
        );
    }
}
