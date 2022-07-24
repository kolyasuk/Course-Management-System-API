package edu.sombra.cms.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionMatcher {

    public static void containsException(String expectedMessage, Exception exception) {
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
