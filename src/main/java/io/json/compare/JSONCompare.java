package io.json.compare;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.matcher.JsonMatcher;
import io.json.compare.util.JsonUtils;
import org.junit.jupiter.api.AssertionFailureBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * @author fslev
 */

public class JSONCompare {

    private static final String ASSERTION_ERROR_HINT_MESSAGE = "Json matching by default uses regular expressions." + System.lineSeparator() +
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
        List<String> diffs = new JsonMatcher(expectedJson, actualJson,
                comparator == null ? new DefaultJsonComparator() : comparator, compareModes).match();
        if (!diffs.isEmpty()) {
            String defaultMessage = String.format("FOUND %s DIFFERENCE(S):" + System.lineSeparator() + "%s" + System.lineSeparator(),
                    diffs.size(), diffs.stream().map(diff ->
                            System.lineSeparator() + System.lineSeparator() + "_________________________DIFF__________________________" +
                                    System.lineSeparator() + diff).reduce(String::concat).get());
            if (comparator == null || comparator.getClass().equals(DefaultJsonComparator.class)) {
                defaultMessage += System.lineSeparator() + System.lineSeparator() + ASSERTION_ERROR_HINT_MESSAGE + System.lineSeparator();
            }
            AssertionFailureBuilder.assertionFailure().message(message == null ? defaultMessage : defaultMessage + System.lineSeparator() + message)
                    .expected(prettyPrint(expectedJson)).actual(prettyPrint(actualJson)).buildAndThrow();
        }
    }

    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes, String message) {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        List<String> diffs = new JsonMatcher(expectedJson, actualJson,
                comparator == null ? new DefaultJsonComparator() : comparator, compareModes).match();
        if (!diffs.isEmpty()) {
            return;
        }
        String defaultMessage = System.lineSeparator() + "JSONs are equal";
        AssertionFailureBuilder.assertionFailure().message(message == null ? defaultMessage : defaultMessage + System.lineSeparator() + message)
                .expected(prettyPrint(expectedJson)).actual(prettyPrint(actualJson))
                .includeValuesInMessage(false).buildAndThrow();
    }

    public static String prettyPrint(JsonNode jsonNode) {
        try {
            return JsonUtils.prettyPrint(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode toJson(Object obj) {
        try {
            return JsonUtils.toJson(obj);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Invalid JSON" + System.lineSeparator() + "%s" + System.lineSeparator(), e));
        }
    }
}
