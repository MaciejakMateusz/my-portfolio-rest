package pl.maciejak.my_portfolio_rest.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

@Component
@RequiredArgsConstructor
public class MeasurementReportBuilder {

    private final MsgProvider msgProvider;

    public String buildReport(MeasurementsDTO measurementsDTO, MeasurementAnalysis analysis) {
        StringBuilder result = new StringBuilder();
        String formattedDate = analysis.reportDate();

        result.append(msgProvider.getLocalizedMsg("tolMeasure.sessionDate", null))
                .append(formattedDate)
                .append(" \n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.productData", null))
                .append("\n");
        result.append(msgProvider.getLocalizedMsg("tolMeasure.productLength", null))
                .append(analysis.productLength())
                .append(" mm\n");
        result.append(msgProvider.getLocalizedMsg("tolMeasure.positiveTolerance", null))
                .append(analysis.posTolerance())
                .append(" mm\n");
        result.append(msgProvider.getLocalizedMsg("tolMeasure.negativeTolerance", null))
                .append(analysis.negTolerance())
                .append(" mm\n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.measurementsData", null))
                .append("\n");
        result.append(msgProvider.getLocalizedMsg("tolMeasure.allMeasurements", null))
                .append(measurementsDTO.measurements())
                .append("\n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.amountMeasured", null))
                .append(analysis.totalCount())
                .append(msgProvider.getLocalizedMsg("tolMeasure.pieces", null))
                .append("\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.average", null))
                .append(String.format("%.2f", analysis.average()))
                .append(" mm\n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.outsideTolerance", null))
                .append(analysis.outsideTolerance())
                .append(msgProvider.getLocalizedMsg("tolMeasure.pieces", null))
                .append("\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.insideTolerance", null))
                .append(analysis.insideTolerance())
                .append(msgProvider.getLocalizedMsg("tolMeasure.pieces", null))
                .append("\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.greaterThan", null))
                .append(analysis.upperBound())
                .append(" mm: ")
                .append(analysis.graterThanUpperBound())
                .append(msgProvider.getLocalizedMsg("tolMeasure.pieces", null))
                .append("\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.smallerThan", null))
                .append(analysis.lowerBound())
                .append(" mm: ")
                .append(analysis.smallerThanLowerBound())
                .append(msgProvider.getLocalizedMsg("tolMeasure.pieces", null))
                .append("\n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.greatestMeasurement", null))
                .append(analysis.maxMeasurement())
                .append(" mm\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.smallestMeasurement", null))
                .append(analysis.minMeasurement())
                .append(" mm\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.difference", null))
                .append(analysis.difference())
                .append(" mm\n\n");

        result.append(msgProvider.getLocalizedMsg("tolMeasure.sortedMeasurements", null))
                .append(analysis.sortedMeasurements());

        return result.toString();
    }
}
