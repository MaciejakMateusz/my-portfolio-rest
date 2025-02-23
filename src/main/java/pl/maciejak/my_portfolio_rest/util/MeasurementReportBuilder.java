package pl.maciejak.my_portfolio_rest.util;

import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.dto.MeasurementStatistics;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MeasurementReportBuilder {

    public String buildReport(MeasurementsDTO measurementsDTO, MeasurementStatistics stats) {
        StringBuilder result = new StringBuilder();
        String formattedDate = getFormattedDate();

        result.append("Measurement session date: ").append(formattedDate).append("\n\n");

        result.append("All measurements(mm): ").append(measurementsDTO.measurements()).append("\n\n");

        result.append("Amount measured: ")
                .append(stats.totalCount())
                .append(" piece(s)\n");

        result.append("Average of all measurements: ")
                .append(String.format("%.2f", stats.average()))
                .append("mm\n\n");

        result.append("TOLERANCE DATA\n");
        result.append("Outside tolerance: ")
                .append(stats.outsideTolerance())
                .append(" piece(s)\n");

        result.append("Inside tolerance: ")
                .append(stats.insideTolerance())
                .append(" piece(s)\n");

        result.append("Bigger than ")
                .append(measurementsDTO.productLength())
                .append("mm: ")
                .append(stats.biggerThanProduct())
                .append(" piece(s)\n");

        result.append("Smaller than ")
                .append(stats.lowerBound())
                .append("mm: ")
                .append(stats.smallerThanLowerBound())
                .append(" piece(s)\n\n");

        result.append("Biggest measurement: ")
                .append(stats.maxMeasurement())
                .append("mm\n");

        result.append("Smallest measurement: ")
                .append(stats.minMeasurement())
                .append("mm\n");

        result.append("The difference between the smallest and the biggest measurement: ")
                .append(stats.difference())
                .append("mm\n\n");

        result.append("Measurements sorted ascending: ")
                .append(stats.sortedMeasurements());

        return result.toString();
    }

    private String getFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return now.format(formatter);
    }
}