package pl.maciejak.my_portfolio_rest.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ToleranceMeasureServiceImplTest extends TestsDataRepository {

    @Autowired
    private ToleranceMeasureService toleranceMeasureService;

    @Test
    public void shouldCalculate() {
        MeasurementsDTO measurementsDTO = getMeasurementsDTO();
        ResponseEntity<?> response = toleranceMeasureService.calculate(measurementsDTO);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assert body != null;
        Object pdf = body.get("pdf");
        assertNotNull(pdf);
        MeasurementAnalysis analysis = (MeasurementAnalysis) body.get("analysis");
        assertEquals(round(60.9), analysis.maxMeasurement());
        assertEquals(round(59.4), analysis.minMeasurement());
        assertEquals(round(60.1), analysis.average());
        assertEquals(1, analysis.greaterThanUpperBound());
        assertEquals(1, analysis.smallerThanLowerBound());
        assertEquals(round(1.5), analysis.difference());
    }

    private static BigDecimal round(Double value) {
        return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
    }
}
