package pl.maciejak.my_portfolio_rest.dto;

import java.util.List;

public record MeasurementsDTO(Double productLength,
                              Double negTolerance,
                              Double posTolerance,
                              List<Double> measurements) {
}
