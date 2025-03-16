package pl.maciejak.my_portfolio_rest.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.util.ApiRequestUtils;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ToleranceMeasureControllerTest extends TestsDataRepository {

    @Autowired
    private ApiRequestUtils apiRequestUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenValidData_shouldCalculate() throws Exception {
        MeasurementsDTO measurements = getMeasurementsDTO();
        Map<?, ?> response = apiRequestUtils.postAndReturnResponseBody(
                "/api/tolerance-measure/calculate", measurements, status().isOk());

        Object pdf = response.get("pdf");
        assertNotNull(pdf);
        LinkedHashMap<?, ?> analysis = (LinkedHashMap<?, ?>) response.get("analysis");
        assertEquals(17, analysis.size());
        assertEquals(60.9, analysis.get("maxMeasurement"));
        assertEquals(59.4, analysis.get("minMeasurement"));
        assertEquals(60.1, analysis.get("average"));
        assertEquals(1, analysis.get("greaterThanUpperBound"));
        assertEquals(1, analysis.get("smallerThanLowerBound"));
        assertEquals(1.5, analysis.get("difference"));
    }

    @Test
    void whenInvalidData_shouldNotCalculate() throws Exception {
        MeasurementsDTO measurements = getInvalidMeasurementsDTO();
        Map<?, ?> response = apiRequestUtils.postAndExpectErrors(
                "/api/tolerance-measure/calculate", measurements);

        assertEquals("Pole nie może być puste", response.get("productLength"));
        assertEquals("Musi być równe lub mniejsze od 0", response.get("negTolerance"));
        assertEquals("Musi być równe lub większe od 0", response.get("posTolerance"));
        assertEquals("Wielkość musi należeć do zakresu od 2 do 2147483647", response.get("measurements"));
    }

    @Test
    void testOptionsEndpoint() throws Exception {
        mockMvc.perform(options("/api/tolerance-measure"))
                .andExpect(status().isOk())
                .andExpect(header().string("Allow", "POST, OPTIONS"));
    }
}