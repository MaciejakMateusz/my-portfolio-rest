package pl.maciejak.my_portfolio_rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tolerance-measure")
public class ToleranceMeasureController {

    private final ToleranceMeasureService toleranceMeasureService;
    private final ResponseHelper responseHelper;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body(responseHelper.getFieldErrors(br));
        }
        return toleranceMeasureService.calculate(measurementsDTO);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}