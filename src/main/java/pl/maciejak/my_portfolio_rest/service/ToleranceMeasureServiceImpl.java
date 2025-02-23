package pl.maciejak.my_portfolio_rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.dto.MeasurementStatistics;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.service.interfaces.ToleranceMeasureService;
import pl.maciejak.my_portfolio_rest.util.MeasurementCalculator;
import pl.maciejak.my_portfolio_rest.util.MeasurementReportBuilder;
import pl.maciejak.my_portfolio_rest.util.PdfGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToleranceMeasureServiceImpl implements ToleranceMeasureService {

    private final MeasurementCalculator measurementCalculator;
    private final MeasurementReportBuilder reportBuilder;
    private final PdfGenerator pdfGenerator;

    @Override
    public ResponseEntity<?> calculate(MeasurementsDTO measurementsDTO) {
        MeasurementStatistics stats = measurementCalculator.calculate(measurementsDTO);
        String reportContent = reportBuilder.buildReport(measurementsDTO, stats);
        byte[] pdfBytes = pdfGenerator.generatePdf(reportContent);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "measurement_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}