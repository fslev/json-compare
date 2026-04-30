package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONCompareMessageTests {

    @Test
    public void checkMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_1() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.compare(expected, actual).comparator(null).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative() {
        String expected = "{\"a\":true,\"b\":0}";
        String actual = "{\"a\":true,\"b\":0}";
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative1() {
        String expected = "{\"a\":true,\"b\":0}";
        String actual = "{\"a\":true,\"b\":0}";
        try {
            JSONCompare.compare(expected, actual).comparator(null).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkDefaultMessageFromJSONsWhichDoNotMatch() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true}";
        try {
            JSONCompare.compare(expected, actual).assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkNullMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("$.a was not found"));
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

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.@.instanceId2\\E -> Actual JSON OBJECT has extra fields.*" +
                "\\Q$.@.version\\E was not found.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertNotMatches();
    }

}
