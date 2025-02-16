package pl.maciejak.my_portfolio_rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearRange {
    private Instant yearBegin;
    private Instant yearEnd;
}