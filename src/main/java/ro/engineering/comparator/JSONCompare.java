package ro.engineering.comparator;

import ro.engineering.comparator.matcher.JsonMatcher;
import ro.engineering.comparator.matcher.MatcherException;
import ro.engineering.util.StringUtil;
import java.io.IOException;
import static org.junit.Assert.fail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONCompare {

    public static void assertEquals(String expectedJson, String actualJson) {
        JsonNode expected = getJson(expectedJson);
        JsonNode actual = getJson(actualJson);
        try {
            new JsonMatcher(expected, actual).matches();
        } catch (MatcherException e) {
            fail(e.getMessage() + "\nExpected:\n" + StringUtil.cropLarge(prettyPrint(expected))
                    + "\nBut got:\n" + StringUtil.cropLarge(prettyPrint(actual)));
        }
    }

    public static void assertNotEquals(String expectedJson, String actualJson) {
        try {
            assertEquals(expectedJson, actualJson);
        } catch (AssertionError e) {
            return;
        }
        fail("JSONs are equal");
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

    private static String prettyPrint(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
