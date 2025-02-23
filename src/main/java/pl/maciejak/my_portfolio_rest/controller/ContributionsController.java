package pl.maciejak.my_portfolio_rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;
import pl.maciejak.my_portfolio_rest.service.interfaces.ContributionsService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContributionsController {

    private final ContributionsService contributionsService;

    @PostMapping("/contributions")
    public ResponseEntity<?> contributions(@RequestBody YearRange range) {
        return contributionsService.getContributions(range);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}