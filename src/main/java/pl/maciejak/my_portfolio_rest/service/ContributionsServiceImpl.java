package pl.maciejak.my_portfolio_rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;
import pl.maciejak.my_portfolio_rest.service.interfaces.ContributionsService;
import pl.maciejak.my_portfolio_rest.util.ContributionsAggregator;

@Service
@RequiredArgsConstructor
public class ContributionsServiceImpl implements ContributionsService {


    private final ContributionsAggregator contributionsAggregator;

    @Override
    public ResponseEntity<?> getContributions(YearRange range) {
        return contributionsAggregator.fetch(range);
    }


}