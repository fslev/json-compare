package ro.engineering.comparator;

import ro.engineering.comparator.matcher.JsonMatcher;
import ro.engineering.comparator.matcher.MatcherException;
import ro.engineering.util.StringUtil;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONCompare {

    public static void assertEquals(String expectedJson, String actualJson) {
        JsonNode expected = getJson(expectedJson);
        JsonNode actual = getJson(actualJson);
        assertEquals(expected, actual);
    }

    public static void assertNotEquals(String expectedJson, String actualJson) {
        JsonNode expected = getJson(expectedJson);
        JsonNode actual = getJson(actualJson);
        try {
            assertEquals(expected, actual);
        } catch (AssertionError e) {
            return;
        }
        fail("JSONs are equal");
    }

    public static void assertEquals(JsonNode expectedJson, JsonNode actualJson) {
        try {
            new JsonMatcher(expectedJson, actualJson).matches();
        } catch (MatcherException e) {
            fail(e.getMessage() + "\nExpected:\n" + StringUtil.cropLarge(prettyPrint(expectedJson))
                    + "\nBut got:\n" + StringUtil.cropLarge(prettyPrint(actualJson)));
        }
    }

    private static JsonNode getJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            fail("Not a JSON: " + StringUtil.cropMedium(json));
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
