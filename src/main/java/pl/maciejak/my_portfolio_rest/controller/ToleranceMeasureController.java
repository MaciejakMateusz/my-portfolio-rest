package pl.maciejak.my_portfolio_rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tolerance-measure")
public class ToleranceMeasureController {

    private final ToleranceMeasureService toleranceMeasureService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody MeasurementsDTO measurementsDTO) {
        return toleranceMeasureService.calculate(measurementsDTO);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}