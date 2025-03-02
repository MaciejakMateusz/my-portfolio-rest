package pl.maciejak.my_portfolio_rest.dto;

import java.math.BigDecimal;
import java.util.List;

public record MeasurementsDTO(BigDecimal productLength,
                              BigDecimal negTolerance,
                              BigDecimal posTolerance,
                              List<BigDecimal> measurements) {
}
