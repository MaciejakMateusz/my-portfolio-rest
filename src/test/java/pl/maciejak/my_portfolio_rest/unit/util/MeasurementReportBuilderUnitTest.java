package pl.maciejak.my_portfolio_rest.unit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import pl.maciejak.my_portfolio_rest.MessageSourceConfig;
import pl.maciejak.my_portfolio_rest.util.MeasurementReportBuilder;
import pl.maciejak.my_portfolio_rest.util.MsgProvider;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeasurementReportBuilderUnitTest extends TestsDataRepository {

    private MeasurementReportBuilder reportBuilder;

    @BeforeEach
    void setup() {
        MessageSourceConfig config = new MessageSourceConfig();
        MessageSource messageSource = config.messageSource();
        MsgProvider msgProvider = new MsgProvider(messageSource);
        this.reportBuilder = new MeasurementReportBuilder(msgProvider);
    }

    @Test
    void shouldBuildReport() {
        String report = reportBuilder.buildReport(getMeasurementAnalysis());
        assertTrue(report.contains(EXPECTED_REPORT));
    }
}
