package pl.maciejak.my_portfolio_rest.unit.util;

import com.itextpdf.text.ExceptionConverter;
import org.junit.jupiter.api.Test;
import pl.maciejak.my_portfolio_rest.util.PdfGenerator;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PdfGeneratorUnitTest extends TestsDataRepository {

    @Test
    void givenCorrectData_shouldGeneratePdf() {
        PdfGenerator pdfGenerator = new PdfGenerator();
        byte[] pdf = pdfGenerator.generatePdf(EXPECTED_REPORT);
        assertEquals(25523, pdf.length);
    }

    @Test
    void givenIncorrectData_shouldNotGeneratePdf() {
        PdfGenerator pdfGenerator = new PdfGenerator();
        ExceptionConverter exception = assertThrows(ExceptionConverter.class,
                () -> pdfGenerator.generatePdf(null));
        assertEquals("The document has no pages.", exception.getMessage());
    }
}