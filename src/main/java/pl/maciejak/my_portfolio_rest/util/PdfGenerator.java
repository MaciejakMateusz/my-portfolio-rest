package pl.maciejak.my_portfolio_rest.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class PdfGenerator {

    public byte[] generatePdf(String reportContent) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.add(new Paragraph(reportContent, getFont()));
            document.close();
        } catch (DocumentException | IOException e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed", e);
        }

        return outputStream.toByteArray();
    }

    private static Font getFont() throws IOException, DocumentException {
        ClassPathResource fontResource = new ClassPathResource("fonts/DejaVuSans.ttf");
        BaseFont baseFont = BaseFont.createFont(
                fontResource.getURL().toExternalForm(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );
        return new Font(baseFont, 12, Font.NORMAL);
    }
}
