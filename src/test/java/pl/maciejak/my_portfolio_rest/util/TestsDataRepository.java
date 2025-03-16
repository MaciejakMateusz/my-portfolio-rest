package pl.maciejak.my_portfolio_rest.util;

import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

import java.math.BigDecimal;
import java.util.List;

public class TestsDataRepository {

    protected static MeasurementsDTO getMeasurementsDTO() {
        return new MeasurementsDTO(BigDecimal.valueOf(60),
                BigDecimal.valueOf(-0.5),
                BigDecimal.valueOf(0.5),
                List.of(BigDecimal.valueOf(60.1),
                        BigDecimal.valueOf(60.5),
                        BigDecimal.valueOf(60.4),
                        BigDecimal.valueOf(59.4),
                        BigDecimal.valueOf(59.5),
                        BigDecimal.valueOf(59.9),
                        BigDecimal.valueOf(60),
                        BigDecimal.valueOf(60.9)));
    }
}
