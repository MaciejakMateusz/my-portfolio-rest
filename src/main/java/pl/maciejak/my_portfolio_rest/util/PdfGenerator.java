package pl.maciejak.my_portfolio_rest.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class PdfGenerator {

    public byte[] generatePdf(String reportContent) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12, Font.NORMAL);
            document.add(new Paragraph(reportContent, font));

            document.close();
        } catch (DocumentException | IOException e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed", e);
        }

        return baos.toByteArray();
    }
}
