package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.controller.TranslationsController;
import pl.maciejak.my_portfolio_rest.service.interfaces.TranslationsService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslationsControllerUnitTest extends TestsDataRepository {

    @Mock
    private TranslationsService translationsService;

    private TranslationsController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        this.closeable = MockitoAnnotations.openMocks(this);
        this.controller = new TranslationsController(translationsService);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void shouldTranslate() {
        String successMsg = "I fetched translation";
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(translationsService).translate(Map.of());

        ResponseEntity<?> response = controller.translate(Map.of());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successMsg, response.getBody());
    }

    @Test
    void shouldGetOptions() {
        ResponseEntity<?> response = controller.options();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String allowHeaderValue = Objects.requireNonNull(response.getHeaders().get("Allow")).getFirst();
        assertEquals("POST, OPTIONS", allowHeaderValue);
    }

}