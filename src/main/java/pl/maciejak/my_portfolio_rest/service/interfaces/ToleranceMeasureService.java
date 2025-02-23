package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

public interface ToleranceMeasureService {
    ResponseEntity<?> calculate(MeasurementsDTO measurementsDTO);
}
