package ro.skyah.comparator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.skyah.comparator.matcher.JsonMatcher;
import ro.skyah.comparator.matcher.MatcherException;
import ro.skyah.util.MessageUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author fslev
 */

public class JSONCompare {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    private static final String ASSERTION_ERROR_HINT_MESSAGE = "Hint: By default, json matching uses regular expressions.\n" +
            "If expected json contains unintentional regexes, then quote them between \\Q and \\E delimiters or use a custom comparator.";

    public static void assertEquals(String expected, String actual, CompareMode... compareModes) {
        assertEquals(null, expected, actual, compareModes);
    }

    public static void assertEquals(String expected, String actual, JsonComparator comparator, CompareMode... compareModes) {
        assertEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String expected, String actual, CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, compareModes);
    }

    public static void assertNotEquals(String expected, String actual, JsonComparator comparator, CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, expectedJson, actualJson, null, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual, JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, expectedJson, actualJson, comparator, compareModes);
    }

    public static void assertEquals(String message, String expected, JsonNode actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertEquals(message, expectedJson, actual, null, compareModes);
    }

    public static void assertEquals(String message, String expected, JsonComparator comparator, JsonNode actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertEquals(message, expectedJson, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String message, String expected, JsonNode actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertNotEquals(message, expectedJson, actual, null, compareModes);
    }

    public static void assertNotEquals(String message, String expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertNotEquals(message, expectedJson, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String message, String expected, String actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertNotEquals(message, expectedJson, actualJson, null, compareModes);
    }

    public static void assertNotEquals(String message, String expected, String actual, JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertNotEquals(message, expectedJson, actualJson, comparator, compareModes);
    }

    public static void assertEquals(JsonNode expected, JsonNode actual, CompareMode... compareModes) {
        assertEquals(null, expected, actual, null, compareModes);
    }

    public static void assertNotEquals(JsonNode expected, JsonNode actual, CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, null, compareModes);
    }

    public static void assertEquals(JsonNode expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        assertEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertNotEquals(JsonNode expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertEquals(String message, JsonNode expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        try {
            new JsonMatcher(expected, actual,
                    comparator == null ? new DefaultJsonComparator() : comparator,
                    new HashSet<>(Arrays.asList(compareModes))).match();
        } catch (MatcherException e) {
            String defaultMessage = String.format("%s\nExpected:\n%s\nBut got:\n%s\n\n%s", e.getMessage(),
                    prettyPrint(expected), MessageUtil.cropL(prettyPrint(actual)), ASSERTION_ERROR_HINT_MESSAGE);
            fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
        }
    }

    public static void assertNotEquals(String message, JsonNode expectedJson, JsonNode actualJson, JsonComparator comparator, CompareMode... compareModes) {
        try {
            new JsonMatcher(expectedJson, actualJson,
                    comparator == null ? new DefaultJsonComparator() : comparator,
                    new HashSet<>(Arrays.asList(compareModes))).match();
        } catch (MatcherException e) {
            return;
        }
        String defaultMessage = "JSONs are equal";
        fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
    }

    public static String prettyPrint(JsonNode jsonNode) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode getJson(String json) {
        JsonNode jsonNode = null;
        try {
            jsonNode = MAPPER.readTree(json);
        } catch (IOException e) {
            fail(String.format("Not a JSON:\n%s", MessageUtil.cropL(json)));
        }
        return jsonNode;
    }
}
