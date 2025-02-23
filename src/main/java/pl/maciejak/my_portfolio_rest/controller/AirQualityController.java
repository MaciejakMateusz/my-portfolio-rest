package pl.maciejak.my_portfolio_rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejak.my_portfolio_rest.service.interfaces.AirQualityService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aq")
public class AirQualityController {

    private final AirQualityService aqService;

    @GetMapping("/countries")
    public ResponseEntity<?> countries() {
        return aqService.getCountries();
    }

    @PostMapping("/locations")
    public ResponseEntity<?> locations(@RequestBody Integer countryId) {
        return aqService.getLocations(countryId);
    }

    @PostMapping("/measurements")
    public ResponseEntity<?> measurements(@RequestBody Map<String, String> params) {
        return aqService.getMeasurements(params);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}