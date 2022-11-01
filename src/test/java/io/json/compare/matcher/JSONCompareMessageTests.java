package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONCompareMessageTests {

    @Test
    public void checkMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "JSONs do match");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_1() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.assertMatches(expected, actual, null, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "JSONs do match");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative() {
        String expected = "{\"a\":true,\"b\":0}";
        String actual = "{\"a\":true,\"b\":0}";
        try {
            JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "JSONs do match");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative1() {
        String expected = "{\"a\":true,\"b\":0}";
        String actual = "{\"a\":true,\"b\":0}";
        try {
            JSONCompare.assertNotMatches(expected, actual, null, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "JSONs do match");
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkDefaultMessageFromJSONsWhichDoNotMatch() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true}";
        try {
            JSONCompare.assertNotMatches(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkNullMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Field 'a' was NOT FOUND"));
        }
    }

    @Test
    public void checkMessageFromFailedMatchingBetweenHighDepthJsons() {
        String expected = "{\n" +
                "          \"@\": {\n" +
                "            \"instanceId2\": {\n" +
                "              \"!endDate\": \".*\",\n" +
                "              \"groupIds\": [\n" +
                "                \"gr1\",\n" +
                "                \"gr2\"\n" +
                "              ]\n" +
                "            },\n" +
                "            \"version\": 0,\n" +
                "            \"!.*\": \".*\"\n" +
                "          }}";
        String actual = "{\n" +
                "        \"@\" : {\n" +
                "          \"instanceId2\" : {\n" +
                "            \"version\" : 0,\n" +
                "            \"groupIds\" : [ \"gr2\", \"gr1\" ]\n" +
                "          }\n" +
                "        }}";

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "@ -> instanceId2 -> Actual JSON OBJECT has extra fields.*" +
                "@ -> Field 'version' was NOT FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

}
