package pl.maciejak.my_portfolio_rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contribution {
    private String day;
    private int value;
}