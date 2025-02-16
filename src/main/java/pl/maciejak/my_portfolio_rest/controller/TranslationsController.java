package pl.maciejak.my_portfolio_rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/translate")
public class TranslationsController {

    @PostMapping
    public ResponseEntity<?> translate(@RequestBody Map<String, String> params) {
        String origin = params.get("origin");
        String destination = params.get("destination");
        return ResponseEntity.ok(origin + "," + destination);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}