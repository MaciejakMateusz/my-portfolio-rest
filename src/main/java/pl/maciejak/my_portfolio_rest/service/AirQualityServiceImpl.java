package pl.maciejak.my_portfolio_rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.maciejak.my_portfolio_rest.service.interfaces.AirQualityService;

import java.io.IOException;
import java.util.*;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    @Value("${openaq.key}")
    private String openaqKey;

    @Value("${openaq.url}")
    private String openaqUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AirQualityServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ResponseEntity<?> getCountries() {
        String url = String.format("%s/countries?limit=200", openaqUrl);
        return executeGetRequest(url);
    }

    @Override
    public ResponseEntity<?> getLocations(Integer countryId) {
        String url = String.format(
                "%s/locations?parameter=pm10,pm25,co,no2,o3,so2&countries_id=%d",
                openaqUrl,
                countryId
        );
        return executeGetRequest(url);
    }

    @Override
    public ResponseEntity<?> getMeasurements(Map<String, String> requestParams) {
        String sensorIds = requestParams.getOrDefault("sensorIds", "");
        String dateFrom = requestParams.getOrDefault("dateFrom", "");
        String dateTo = requestParams.getOrDefault("dateTo", "");

        if (Objects.isNull(sensorIds) || sensorIds.isBlank()) {
            return ResponseEntity.badRequest().body("Missing sensorIds parameter");
        }

        String[] ids = sensorIds.split(",");
        List<Map<String, Object>> chartData = new ArrayList<>();

        for (String id : ids) {
            String url = String.format(
                    "%s/sensors/%s/days?date_from=%s&date_to=%s",
                    openaqUrl, id.trim(), dateFrom, dateTo
            );
            ResponseEntity<String> response = executeGetRequest(url);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
            try {
                chartData.addAll(parseSensorData(response.getBody()));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error parsing OpenAQ response: " + e.getMessage());
            }
        }
        return ResponseEntity.ok(chartData);
    }

    private ResponseEntity<String> executeGetRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", openaqKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    private List<Map<String, Object>> parseSensorData(String responseBody) throws IOException {
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode resultsNode = root.path("results");

        if (resultsNode.isMissingNode() || !resultsNode.isArray()) {
            throw new IOException("Unexpected JSON structure");
        }

        Map<String, List<Map<String, Object>>> parameterDataMap = new HashMap<>();
        int indexCounter = 0;

        createDataPoints(resultsNode, indexCounter, parameterDataMap);
        return getPreparedSensorData(parameterDataMap);
    }

    private static void createDataPoints(JsonNode resultsNode, int indexCounter, Map<String, List<Map<String, Object>>> parameterDataMap) {
        for (JsonNode item : resultsNode) {
            String parameterName = item.path("parameter").path("name").asText();
            if (parameterName.isBlank()) {
                parameterName = item.path("parameter").path("id").asText("Unknown Parameter");
            }
            double value = item.path("value").asDouble(0.0);

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", ++indexCounter);
            dataPoint.put("y", value);

            parameterDataMap
                    .computeIfAbsent(parameterName, k -> new ArrayList<>())
                    .add(dataPoint);
        }
    }

    private static List<Map<String, Object>> getPreparedSensorData(Map<String, List<Map<String, Object>>> parameterDataMap) {
        List<Map<String, Object>> sensorData = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : parameterDataMap.entrySet()) {
            Map<String, Object> singleLine = new HashMap<>();
            singleLine.put("id", entry.getKey());
            singleLine.put("data", entry.getValue());
            sensorData.add(singleLine);
        }
        return sensorData;
    }
}