package com.github.fslev.comparator;

import com.github.fslev.comparator.matcher.JsonMatcher;
import com.github.fslev.comparator.matcher.MatcherException;
import com.github.fslev.util.StringUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONCompare {

    public static void assertEquals(String expected, String actual, CompareMode... compareModes) {
        assertEquals(null, expected, actual, compareModes);
    }

    public static void assertNotEquals(String expected, String actual,
            CompareMode... compareModes) {
        assertNotEquals(null, expected, actual, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual,
            CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, expectedJson, actualJson, compareModes);
    }

    public static void assertNotEquals(String message, String expected, String actual,
            CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertNotEquals(message, expectedJson, actualJson, compareModes);
    }

    public static void assertEquals(String message, JsonNode expected, JsonNode actual,
            CompareMode... compareModes) {
        try {
            new JsonMatcher(expected, actual, new HashSet<CompareMode>(Arrays.asList(compareModes)))
                    .matches();
        } catch (MatcherException e) {
            fail(message == null ? String.format("%s\nExpected:\n%s\nBut got:\n%s ",
                    e.getMessage(), StringUtil.cropLarge(prettyPrint(expected)),
                    StringUtil.cropLarge(prettyPrint(actual))) : message);
        }
    }

    public static void assertNotEquals(String message, JsonNode expectedJson, JsonNode actualJson,
            CompareMode... compareModes) {
        try {
            new JsonMatcher(expectedJson, actualJson,
                    new HashSet<CompareMode>(Arrays.asList(compareModes))).matches();
        } catch (MatcherException e) {
            return;
        }
        fail(message == null ? "JSONs are equal" : message);
    }

    private static JsonNode getJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            fail(String.format("Not a JSON:\n%s", StringUtil.cropMedium(json)));
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