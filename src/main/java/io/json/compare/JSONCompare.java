package io.json.compare;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.json.compare.matcher.JsonMatcher;
import io.json.compare.matcher.MatcherException;
import io.json.compare.util.MessageUtil;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author fslev
 */

public class JSONCompare {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    private static final String ASSERTION_ERROR_HINT_MESSAGE = "Json matching by default uses regular expressions.\n" +
            "In case expected json contains any unintentional regexes, then quote them between \\Q and \\E delimiters or use a custom comparator.";

    public static void assertMatches(Object expected, Object actual) {
        assertMatches(expected, actual, null, null, null);
    }

    public static void assertNotMatches(Object expected, Object actual) {
        assertNotMatches(expected, actual, null, null, null);
    }

    public static void assertMatches(Object expected, Object actual, Set<CompareMode> compareModes) {
        assertMatches(expected, actual, null, compareModes);
    }

    public static void assertNotMatches(Object expected, Object actual, Set<CompareMode> compareModes) {
        assertNotMatches(expected, actual, null, compareModes);
    }

    public static void assertMatches(Object expected, Object actual, JsonComparator comparator) {
        assertMatches(expected, actual, comparator, null);
    }

    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator) {
        assertNotMatches(expected, actual, comparator, null);
    }

    public static void assertMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        assertMatches(expected, actual, comparator, compareModes, null);
    }

    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        assertNotMatches(expected, actual, comparator, compareModes, null);
    }

    public static void assertMatches(Object expected, Object actual, Set<CompareMode> compareModes, String message) {
        assertMatches(expected, actual, null, compareModes, message);
    }

    public static void assertNotMatches(Object expected, Object actual, Set<CompareMode> compareModes, String message) {
        assertNotMatches(expected, actual, null, compareModes, message);
    }

    public static void assertMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes, String message) {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        try {
            new JsonMatcher(expectedJson, actualJson,
                    comparator == null ? new DefaultJsonComparator() : comparator, compareModes).match();
        } catch (MatcherException e) {
            String defaultMessage = String.format("%s\nExpected:\n%s\nBut got:\n%s", e.getMessage(),
                    prettyPrint(expectedJson), MessageUtil.cropL(prettyPrint(actualJson)));
            if (comparator == null || comparator.getClass().equals(DefaultJsonComparator.class)) {
                defaultMessage += "\n\n" + ASSERTION_ERROR_HINT_MESSAGE + "\n";
            }
            fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
        }
    }

    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes, String message) {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        try {
            new JsonMatcher(expectedJson, actualJson,
                    comparator == null ? new DefaultJsonComparator() : comparator, compareModes).match();
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

    private static JsonNode toJson(Object obj) {
        JsonNode jsonNode = null;
        try {
            jsonNode = obj instanceof JsonNode ? (JsonNode) obj :
                    (obj instanceof String) ? MAPPER.readTree(obj.toString()) : MAPPER.convertValue(obj, JsonNode.class);
        } catch (IOException e) {
            fail(String.format("Cannot convert to JSON:\n%s", MessageUtil.cropL(obj.toString())));
        }
        return jsonNode;
    }
}
