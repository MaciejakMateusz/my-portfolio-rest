package pl.maciejak.my_portfolio_rest.dto;

import java.math.BigDecimal;
import java.util.List;

public record MeasurementAnalysis(String reportDate,
                                  List<BigDecimal> measurements,
                                  long totalCount,
                                  BigDecimal average,
                                  BigDecimal lowerBound,
                                  BigDecimal upperBound,
                                  long outsideTolerance,
                                  long insideTolerance,
                                  BigDecimal productLength,
                                  long graterThanUpperBound,
                                  long smallerThanLowerBound,
                                  BigDecimal maxMeasurement,
                                  BigDecimal minMeasurement,
                                  BigDecimal difference,
                                  Iterable<BigDecimal> sortedMeasurements) {
}