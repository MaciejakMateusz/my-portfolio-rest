package pl.maciejak.my_portfolio_rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;
import pl.maciejak.my_portfolio_rest.service.interfaces.PortfolioService;
import pl.maciejak.my_portfolio_rest.util.ContributionsAggregator;

import java.util.List;

@Service
public class PortfolioServiceImp implements PortfolioService {


    private final ContributionsAggregator contributionsAggregator;

    public PortfolioServiceImp(ContributionsAggregator contributionsAggregator) {
        this.contributionsAggregator = contributionsAggregator;
    }

    @Override
    public List<?> get() {
        return List.of("emem", "emem", "emem", "emem", "emem", "emem", "emem");
    }

    @Override
    public ResponseEntity<?> getContributions(YearRange range) {
        return contributionsAggregator.fetch(range);
    }


}