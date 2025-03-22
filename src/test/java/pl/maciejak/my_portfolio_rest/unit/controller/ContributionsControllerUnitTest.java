package pl.maciejak.my_portfolio_rest.unit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.controller.ContributionsController;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;
import pl.maciejak.my_portfolio_rest.service.interfaces.ContributionsService;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContributionsControllerUnitTest extends TestsDataRepository {

    @Mock
    private ContributionsService contributionsService;

    private ContributionsController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        this.closeable = MockitoAnnotations.openMocks(this);
        this.controller = new ContributionsController(contributionsService);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Objects.nonNull(closeable)) closeable.close();
    }

    @Test
    void shouldGetContributions() {
        String successMsg = "I fetched contributions";
        Mockito.doReturn(ResponseEntity.ok(successMsg)).when(contributionsService).getContributions(new YearRange());

        ResponseEntity<?> response = controller.contributions(new YearRange());

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