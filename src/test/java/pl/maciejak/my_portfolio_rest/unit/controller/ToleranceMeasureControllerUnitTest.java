package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.controller.ToleranceMeasureController;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class ToleranceMeasureControllerUnitTest extends TestsDataRepository {

    @Mock
    private ToleranceMeasureService toleranceMeasureService;

    private ToleranceMeasureController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        controller = new ToleranceMeasureController(toleranceMeasureService);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void shouldCalculate() {
        MeasurementsDTO measurements = getMeasurementsDTO();
        Mockito.doReturn(ResponseEntity.ok("Calculation successful"))
                .when(toleranceMeasureService).calculate(any());

        ResponseEntity<?> response = controller.calculate(measurements);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Calculation successful", response.getBody());
    }
}
