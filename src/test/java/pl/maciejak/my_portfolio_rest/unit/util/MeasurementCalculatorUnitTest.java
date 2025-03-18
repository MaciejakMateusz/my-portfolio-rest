package pl.maciejak.my_portfolio_rest.unit.util;

import org.junit.jupiter.api.Test;
import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;
import pl.maciejak.my_portfolio_rest.util.MeasurementCalculator;
import pl.maciejak.my_portfolio_rest.util.TestsDataRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementCalculatorUnitTest extends TestsDataRepository {

    private final MeasurementCalculator calculator = new MeasurementCalculator();

    @Test
    void calculate_ShouldReturnCorrectAnalysis() {
        MeasurementsDTO measurementsDTO = getMeasurementsDTO();
        MeasurementAnalysis result = calculator.calculate(measurementsDTO);
        assertAnalysisResult(result);
    }

    private static void assertAnalysisResult(MeasurementAnalysis result) {
        assertEquals(8, result.totalCount());
        assertEquals(round(59.5), result.lowerBound());
        assertEquals(round(60.5), result.upperBound());
        assertEquals(round(60.1), result.average());
        assertEquals(2, result.outsideTolerance());
        assertEquals(6, result.insideTolerance());
        assertEquals(1, result.smallerThanLowerBound());
        assertEquals(1, result.greaterThanUpperBound());
        assertEquals(round(60.9), result.maxMeasurement());
        assertEquals(round(59.4), result.minMeasurement());
        assertEquals(round(1.5), result.difference());

        List<BigDecimal> expectedSortedMeasurements = Arrays.asList(
                BigDecimal.valueOf(59.4),
                BigDecimal.valueOf(59.5),
                BigDecimal.valueOf(59.9),
                BigDecimal.valueOf(60),
                BigDecimal.valueOf(60.1),
                BigDecimal.valueOf(60.4),
                BigDecimal.valueOf(60.5),
                BigDecimal.valueOf(60.9)
        );

        assertEquals(expectedSortedMeasurements, result.sortedMeasurements());
    }

    private static BigDecimal round(Double value) {
        return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
    }
}