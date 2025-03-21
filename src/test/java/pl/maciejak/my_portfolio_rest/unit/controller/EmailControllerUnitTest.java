package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.controller.EmailController;
import pl.maciejak.my_portfolio_rest.service.interfaces.EmailService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailControllerUnitTest extends TestsDataRepository {

    @Mock
    private EmailService emailService;

    private EmailController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        this.closeable = MockitoAnnotations.openMocks(this);
        this.controller = new EmailController(emailService);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void shouldSendEmail() {
        String successMsg = "I've sent an email";
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(emailService).send(Map.of());

        ResponseEntity<?> response = controller.sendEmail(Map.of());

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