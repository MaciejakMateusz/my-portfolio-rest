package pl.maciejak.my_portfolio_rest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Utility class designed to simplify making HTTP requests to REST API in test environment.
 */

@Component
public class ApiRequestUtils {

    protected final ObjectMapper objectMapper;

    protected final MockMvc mockMvc;

    /**
     * Constructor to initialize the ApiRequestUtils with a MockMvc instance.
     *
     * @param mockMvc The MockMvc instance to be used for making requests.
     */
    public ApiRequestUtils(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = prepObjMapper();
    }

    /**
     * Sends a POST HTTP request to the specified endpoint URL with the provided object as the request body.
     * Expects a certain result based on the provided ResultMatcher.
     * Retrieves and returns the response body as a Map of String keys to Object values.
     *
     * @param endpointUrl The URL endpoint to send the POST request to.
     * @param object      The object to be serialized and sent as the request body.
     * @param matcher     The ResultMatcher to apply to the response.
     * @param <T>         The type of the object being sent in the request body.
     * @return A Map representing the response body, with String keys and Object values.
     * @throws Exception If there are any errors during the request or response handling.
     */
    public <T> Map<String, Object> postAndReturnResponseBody(String endpointUrl,
                                                             T object,
                                                             ResultMatcher matcher) throws Exception {
        String jsonRequest;
        if (!(object instanceof String)) {
            jsonRequest = objectMapper.writeValueAsString(object);
        } else {
            jsonRequest = (String) object;
        }

        ResultActions resultActions = mockMvc.perform(post(endpointUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "pl"))
                .andExpect(matcher)
                .andDo(print());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
        };
        return objectMapper.readValue(responseBody, typeReference);
    }

    /**
     * Prepares and returns an ObjectMapper instance with necessary modules registered.
     *
     * @return A prepared ObjectMapper.
     */
    private ObjectMapper prepObjMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(TreeSet.class);
        return objectMapper;
    }
}