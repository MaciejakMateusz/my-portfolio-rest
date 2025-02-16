package pl.maciejak.my_portfolio_rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;
import pl.maciejak.my_portfolio_rest.service.interfaces.ContributionsService;
import pl.maciejak.my_portfolio_rest.util.ContributionsAggregator;

@Service
public class ContributionsServiceImp implements ContributionsService {


    private final ContributionsAggregator contributionsAggregator;

    public ContributionsServiceImp(ContributionsAggregator contributionsAggregator) {
        this.contributionsAggregator = contributionsAggregator;
    }

    @Override
    public ResponseEntity<?> getContributions(YearRange range) {
        return contributionsAggregator.fetch(range);
    }


}