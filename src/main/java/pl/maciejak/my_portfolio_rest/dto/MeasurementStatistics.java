package pl.maciejak.my_portfolio_rest.dto;

public record MeasurementStatistics(int totalCount,
                                    double average,
                                    double lowerBound,
                                    double upperBound,
                                    long outsideTolerance,
                                    long insideTolerance,
                                    long biggerThanProduct,
                                    long smallerThanLowerBound,
                                    double maxMeasurement,
                                    double minMeasurement,
                                    double difference,
                                    Iterable<Double> sortedMeasurements) {
}