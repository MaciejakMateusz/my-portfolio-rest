package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface AirQualityService {
    ResponseEntity<?> getCountries();

    ResponseEntity<?> getLocations(Integer countryId);

    ResponseEntity<?> getMeasurements(Integer locationId);
}
