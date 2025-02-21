package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AirQualityService {
    ResponseEntity<?> getCountries();

    ResponseEntity<?> getLocations(Integer countryId);

    ResponseEntity<?> getMeasurements(Map<String, String> params);
}
