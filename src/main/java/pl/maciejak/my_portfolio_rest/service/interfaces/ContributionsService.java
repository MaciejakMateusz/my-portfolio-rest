package pl.maciejak.my_portfolio_rest.service.interfaces;

import org.springframework.http.ResponseEntity;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;

public interface ContributionsService {
    ResponseEntity<?> getContributions(YearRange range);
}
