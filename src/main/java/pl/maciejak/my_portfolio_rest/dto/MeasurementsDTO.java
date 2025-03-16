package pl.maciejak.my_portfolio_rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record MeasurementsDTO(@NotNull @Min(1) BigDecimal productLength,
                              @NotNull @NegativeOrZero BigDecimal negTolerance,
                              @NotNull @PositiveOrZero BigDecimal posTolerance,
                              @NotEmpty @Size(min = 2) List<BigDecimal> measurements) {
}
