package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import pl.maciejak.my_portfolio_rest.controller.ResponseHelper;
import pl.maciejak.my_portfolio_rest.controller.ToleranceMeasureController;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.math.BigDecimal;
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
        this.closeable = MockitoAnnotations.openMocks(this);
        ResponseHelper responseHelper = Mockito.mock(ResponseHelper.class);
        this.controller = new ToleranceMeasureController(toleranceMeasureService, responseHelper);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void whenValidData_shouldCalculate() {
        MeasurementsDTO measurements = getMeasurementsDTO();
        String successMsg = "Calculation successful";
        Mockito.doReturn(ResponseEntity.ok(successMsg))
                .when(toleranceMeasureService).calculate(any());
        BindingResult bindingResult = new BeanPropertyBindingResult(measurements, "measurements");

        ResponseEntity<?> response = controller.calculate(measurements, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMsg, response.getBody());
    }

    @Test
    void whenInvalidData_shouldNotCalculate() {
        MeasurementsDTO measurements = getInvalidMeasurementsDTO();
        String errorMsg = "Measurements are invalid";
        Mockito.doReturn(ResponseEntity.badRequest().body(errorMsg))
                .when(toleranceMeasureService).calculate(any());

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(measurements, "measurements");
        bindingResult.recordFieldValue("productLength", MeasurementsDTO.class, null);
        bindingResult.recordFieldValue("posTolerance", MeasurementsDTO.class, BigDecimal.valueOf(-0.5));

        ResponseEntity<?> response = controller.calculate(measurements, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMsg, response.getBody());
    }
}
