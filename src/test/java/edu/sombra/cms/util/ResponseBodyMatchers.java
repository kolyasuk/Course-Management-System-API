package edu.sombra.cms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sombra.cms.controller.advisor.ErrorResponse;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;


public class ResponseBodyMatchers {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(expectedObject);
        };
    }

    public ResultMatcher containsError(String expectedMessage) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            ErrorResponse errorResponse = objectMapper.readValue(json, ErrorResponse.class);

            assertThat(errorResponse.getMessage()).isEqualTo(expectedMessage);
        };
    }


    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }

}
