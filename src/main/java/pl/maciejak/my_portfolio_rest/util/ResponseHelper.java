package pl.maciejak.my_portfolio_rest.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * This component provides helper methods to build standardized ResponseEntity objects
 * for various scenarios (e.g., saving entities, retrieving data, handling errors, and redirects).
 * It encapsulates common patterns of exception handling and result packaging to streamline
 * controller code.
 */
@Component
@Slf4j
public class ResponseHelper {

    /**
     * Extracts field errors from the provided BindingResult.
     * <p>
     * This method should be called after checking {@code br.hasErrors()}.
     *
     * @param br the BindingResult containing field errors
     * @return a map where the keys are field names and the values are the corresponding error messages
     */
    public Map<String, String> getFieldErrors(BindingResult br) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : br.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return fieldErrors;
    }
}