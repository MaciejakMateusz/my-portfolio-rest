package pl.maciejak.my_portfolio_rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.maciejak.my_portfolio_rest.pojo.Contribution;
import pl.maciejak.my_portfolio_rest.pojo.YearRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContributionsAggregator {

    @Value("${github.login}")
    private String githubLogin;

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.graphql.url}")
    private String githubGraphqlUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ContributionsAggregator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Fetch contributions from GitHub using a GraphQL query. Only days with
     * contributionCount > 0 are added to the result.
     *
     * @param range a YearRange object containing the start and end dates
     * @return List of contributions
     */
    public ResponseEntity<?> fetch(YearRange range) {
        Map<String, String> requestBody = getRequestBody(range);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + githubToken);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(githubGraphqlUrl, entity, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.badRequest().body(response.getStatusCode());
        }

        return aggregateContributions(response);
    }

    private Map<String, String> getRequestBody(YearRange range) {
        String yearBegin = range.getYearBegin().toString();
        String yearEnd = range.getYearEnd().toString();

        String query = String.format(
                "query {" +
                        "  user(login: \"%s\") {" +
                        "    contributionsCollection(from: \"%s\", to: \"%s\") {" +
                        "      contributionCalendar {" +
                        "        weeks {" +
                        "          contributionDays {" +
                        "            date" +
                        "            contributionCount" +
                        "          }" +
                        "        }" +
                        "      }" +
                        "    }" +
                        "  }" +
                        "}",
                githubLogin, yearBegin, yearEnd
        );

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("query", query);
        return requestBody;
    }

    private ResponseEntity<?> aggregateContributions(ResponseEntity<String> response) {
        List<Contribution> contributions = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode weeks = root.path("data")
                    .path("user")
                    .path("contributionsCollection")
                    .path("contributionCalendar")
                    .path("weeks");

            if (weeks.isArray()) {
                for (JsonNode week : weeks) {
                    JsonNode days = week.path("contributionDays");
                    if (days.isArray()) {
                        for (JsonNode day : days) {
                            int count = day.path("contributionCount").asInt();
                            if (count > 0) {
                                String date = day.path("date").asText();
                                contributions.add(new Contribution(date, count));
                            }
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(contributions);
    }
}
