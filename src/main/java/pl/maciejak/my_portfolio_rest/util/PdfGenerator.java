package pl.maciejak.my_portfolio_rest.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Slf4j
@Component
public class PdfGenerator {

    public byte[] generatePdf(String reportContent) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph(reportContent));
            document.close();
        } catch (DocumentException e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed", e);
        }

        return baos.toByteArray();
    }
}