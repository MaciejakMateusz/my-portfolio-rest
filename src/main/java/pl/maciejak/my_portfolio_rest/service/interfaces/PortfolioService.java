package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;

import java.util.List;

public interface PortfolioService {
    List<?> get();

    ResponseEntity<?> getContributions(YearRange range);
}
