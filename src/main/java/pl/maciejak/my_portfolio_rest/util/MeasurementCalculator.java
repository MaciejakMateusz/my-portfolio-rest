package pl.maciejak.my_portfolio_rest.util;

import org.springframework.stereotype.Component;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MeasurementCalculator {

    public MeasurementAnalysis calculate(MeasurementsDTO measurementsDTO) {
        Double productLength = measurementsDTO.productLength();
        Double posTolerance = measurementsDTO.posTolerance();
        Double negTolerance = measurementsDTO.negTolerance();
        List<Double> measurements = measurementsDTO.measurements();

        int totalCount = measurements.size();
        double average = measurements.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double lowerBound = productLength - negTolerance;
        double upperBound = productLength + posTolerance;

        long outsideTolerance = measurements.stream()
                .filter(m -> m < lowerBound || m > upperBound)
                .count();
        long insideTolerance = totalCount - outsideTolerance;

        long biggerThanProduct = measurements.stream()
                .filter(m -> m > productLength)
                .count();
        long smallerThanLowerBound = measurements.stream()
                .filter(m -> m < lowerBound)
                .count();

        Double maxMeasurement = measurements.stream()
                .max(Double::compareTo)
                .orElse(0.0);
        Double minMeasurement = measurements.stream()
                .min(Double::compareTo)
                .orElse(0.0);
        double difference = maxMeasurement - minMeasurement;

        List<Double> sortedMeasurements = new ArrayList<>(measurements);
        Collections.sort(sortedMeasurements);

        return new MeasurementAnalysis(
                totalCount,
                average,
                lowerBound,
                upperBound,
                outsideTolerance,
                insideTolerance,
                biggerThanProduct,
                smallerThanLowerBound,
                maxMeasurement,
                minMeasurement,
                difference,
                sortedMeasurements
        );
    }
}