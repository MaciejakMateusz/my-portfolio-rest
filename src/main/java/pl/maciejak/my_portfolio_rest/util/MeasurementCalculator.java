package pl.maciejak.my_portfolio_rest.util;

import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MeasurementCalculator {

    public MeasurementAnalysis calculate(MeasurementsDTO measurementsDTO) {
        BigDecimal productLength = measurementsDTO.productLength();
        BigDecimal posTolerance = measurementsDTO.posTolerance();
        BigDecimal negTolerance = measurementsDTO.negTolerance();
        List<BigDecimal> measurements = measurementsDTO.measurements();

        long totalCount = measurements.size();
        BigDecimal sum = measurements.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = sum.divide(BigDecimal.valueOf(measurements.size()), RoundingMode.HALF_UP);

        BigDecimal lowerBound = productLength.add(negTolerance);
        BigDecimal upperBound = productLength.add(posTolerance);

        long outsideTolerance = (int) measurements.stream()
                .filter(m -> m.compareTo(lowerBound) < 0 || m.compareTo(upperBound) > 0)
                .count();
        long insideTolerance = totalCount - outsideTolerance;

        long greaterThanUpperBound = measurements.stream()
                .filter(m -> m.compareTo(upperBound) > 0)
                .count();
        long smallerThanLowerBound = measurements.stream()
                .filter(m -> m.compareTo(lowerBound) < 0)
                .count();

        BigDecimal maxMeasurement = measurements.stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal minMeasurement = measurements.stream()
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal difference = maxMeasurement.min(minMeasurement);

        List<BigDecimal> sortedMeasurements = new ArrayList<>(measurements);
        Collections.sort(sortedMeasurements);

        return new MeasurementAnalysis(
                getFormattedDate(),
                measurements,
                totalCount,
                round(average),
                round(lowerBound),
                round(upperBound),
                outsideTolerance,
                insideTolerance,
                round(productLength),
                greaterThanUpperBound,
                smallerThanLowerBound,
                round(maxMeasurement),
                round(minMeasurement),
                round(difference),
                sortedMeasurements
        );
    }

    private static String getFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private static BigDecimal round(BigDecimal value) {
        return value.setScale(3, RoundingMode.HALF_UP);
    }
}