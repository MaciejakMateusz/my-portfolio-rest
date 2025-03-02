package pl.maciejak.my_portfolio_rest.util;

import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

@Component
public class MeasurementReportBuilder {

    public String buildReport(MeasurementsDTO measurementsDTO, MeasurementAnalysis analysis) {
        StringBuilder result = new StringBuilder();
        String formattedDate = analysis.reportDate();

        result.append("Measurement session date: ").append(formattedDate).append("\n\n");

        result.append("All measurements (mm): ").append(measurementsDTO.measurements()).append("\n\n");

        result.append("Amount measured: ")
                .append(analysis.totalCount())
                .append(" piece(s)\n");

        result.append("Average of all measurements: ")
                .append(String.format("%.2f", analysis.average()))
                .append("mm\n\n");

        result.append("Outside tolerance: ")
                .append(analysis.outsideTolerance())
                .append(" piece(s)\n");

        result.append("Inside tolerance: ")
                .append(analysis.insideTolerance())
                .append(" piece(s)\n");

        result.append("Greater than ")
                .append(analysis.upperBound())
                .append("mm: ")
                .append(analysis.graterThanUpperBound())
                .append(" piece(s)\n");

        result.append("Smaller than ")
                .append(analysis.lowerBound())
                .append("mm: ")
                .append(analysis.smallerThanLowerBound())
                .append(" piece(s)\n\n");

        result.append("Greatest measurement: ")
                .append(analysis.maxMeasurement())
                .append("mm\n");

        result.append("Smallest measurement: ")
                .append(analysis.minMeasurement())
                .append("mm\n");

        result.append("The difference between the smallest and the biggest measurement: ")
                .append(analysis.difference())
                .append("mm\n\n");

        result.append("Measurements sorted ascending: ")
                .append(analysis.sortedMeasurements());

        return result.toString();
    }


}