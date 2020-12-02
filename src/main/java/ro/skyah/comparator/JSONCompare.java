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

import static org.junit.Assert.fail;

/**
 * @author fslev
 */

public class JSONCompare {

    public static void assertEquals(String expected, String actual, CompareMode... compareModes) {
        assertEquals(null, expected, actual, compareModes);
    }

    public static void assertEquals(String expected, String actual, JsonComparator comparator,
                                    CompareMode... compareModes) {
        assertEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String expected, String actual,
                                       CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, compareModes);
    }

    public static void assertNotEquals(String expected, String actual, JsonComparator comparator,
                                       CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual,
                                    CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, expectedJson, actualJson, null, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual,
                                    JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, expectedJson, actualJson, comparator, compareModes);
    }

    public static void assertEquals(String message, String expected, JsonNode actual,
                                    CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertEquals(message, expectedJson, actual, null, compareModes);
    }

    public static void assertEquals(String message, String expected, JsonComparator comparator,
                                    JsonNode actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertEquals(message, expectedJson, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String message, String expected, JsonNode actual,
                                       CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertNotEquals(message, expectedJson, actual, null, compareModes);
    }

    public static void assertNotEquals(String message, String expected, JsonNode actual,
                                       JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        assertNotEquals(message, expectedJson, actual, comparator, compareModes);
    }

    public static void assertNotEquals(String message, String expected, String actual,
                                       CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertNotEquals(message, expectedJson, actualJson, null, compareModes);
    }

    public static void assertNotEquals(String message, String expected, String actual,
                                       JsonComparator comparator, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertNotEquals(message, expectedJson, actualJson, comparator, compareModes);
    }

    public static void assertEquals(JsonNode expected, JsonNode actual,
                                    CompareMode... compareModes) {
        assertEquals(null, expected, actual, null, compareModes);
    }

    public static void assertNotEquals(JsonNode expected, JsonNode actual,
                                       CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, null, compareModes);
    }

    public static void assertEquals(JsonNode expected, JsonNode actual, JsonComparator comparator,
                                    CompareMode... compareModes) {
        assertEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertNotEquals(JsonNode expected, JsonNode actual,
                                       JsonComparator comparator, CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, comparator, compareModes);
    }

    public static void assertEquals(String message, JsonNode expected, JsonNode actual,
                                    JsonComparator comparator, CompareMode... compareModes) {
        try {
            new JsonMatcher(expected, actual,
                    comparator == null ? new DefaultJsonComparator() : comparator,
                    new HashSet<>(Arrays.asList(compareModes))).matches();
        } catch (MatcherException e) {
            String defaultMessage = String.format("%s\nExpected:\n%s\nBut got:\n%s ", e.getMessage(),
                    prettyPrint(expected), prettyPrint(actual));
            fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
        }
    }

    public static void assertNotEquals(String message, JsonNode expectedJson, JsonNode actualJson,
                                       JsonComparator comparator, CompareMode... compareModes) {
        try {
            new JsonMatcher(expectedJson, actualJson,
                    comparator == null ? new DefaultJsonComparator() : comparator,
                    new HashSet<>(Arrays.asList(compareModes))).matches();
        } catch (MatcherException e) {
            return;
        }
        String defaultMessage = "JSONs are equal";
        fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
    }

    private static JsonNode getJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            fail(String.format("Not a JSON:\n%s", MessageUtil.cropM(json)));
        }
        return jsonNode;
    }

    public static String prettyPrint(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
