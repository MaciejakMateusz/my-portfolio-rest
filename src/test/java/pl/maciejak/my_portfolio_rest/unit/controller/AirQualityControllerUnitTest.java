package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.controller.AirQualityController;
import pl.maciejak.my_portfolio_rest.service.interfaces.AirQualityService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AirQualityControllerUnitTest extends TestsDataRepository {

    @Mock
    private AirQualityService airQualityService;

    private AirQualityController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        this.closeable = MockitoAnnotations.openMocks(this);
        this.controller = new AirQualityController(airQualityService);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void shouldGetCountries() {
        String successMsg = "I fetched countries";
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(airQualityService).getCountries();

        ResponseEntity<?> response = controller.countries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMsg, response.getBody());
    }

    @Test
    void shouldGetLocations() {
        String successMsg = "I fetched locations";
        int testCountryId = 111;
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(airQualityService).getLocations(testCountryId);

        ResponseEntity<?> response = controller.locations(testCountryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMsg, response.getBody());
    }

    @Test
    void shouldGetMeasurements() {
        String successMsg = "I fetched measurements";
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(airQualityService).getMeasurements(Map.of());

        ResponseEntity<?> response = controller.measurements(Map.of());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMsg, response.getBody());
    }

    @Test
    void shouldGetOptions() {
        ResponseEntity<?> response = controller.options();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String allowHeaderValue = Objects.requireNonNull(response.getHeaders().get("Allow")).getFirst();
        assertEquals("GET, POST, OPTIONS", allowHeaderValue);
    }

}