package pl.maciejak.my_portfolio_rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.maciejak.my_portfolio_rest.service.interfaces.AirQualityService;

@Service
public class AirQualityServiceImp implements AirQualityService {

    @Value("${openaq.key}")
    private String openaqKey;

    @Value("${openaq.url}")
    private String openaqUrl;

    private final RestTemplate restTemplate;

    public AirQualityServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<?> getCountries() {
        String url = openaqUrl + "/countries?limit=200";
        return executeExchange(url);
    }

    @Override
    public ResponseEntity<?> getLocations(Integer countryId) {
        String url = openaqUrl + "/locations?countries_id=" + countryId;
        return executeExchange(url);
    }

    @Override
    public ResponseEntity<?> getMeasurements(Integer locationId) {
        String url = "https://api.openaq.org/v3/measurements?location_id=" + locationId +
                "&parameter=pm25" +
                "&date_from=2024-01-01" +
                "&date_to=2024-01-31";
        return executeExchange(url);
    }

    private ResponseEntity<?> executeExchange(String parametrizedUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", openaqKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(parametrizedUrl, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }
}