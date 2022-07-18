package ro.skyah.comparator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

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
            assertTrue(e.getMessage().contains("Field a was not found"));
        }
    }

    @Test
    @Disabled
    //toDo Fix error message
    public void checkMessageFromFailedMatchingBetweenHighDepthJsons() {
        String expected = "{\n" +
                "          \"@\": {\n" +
                "            \"instanceId1\": {\n" +
                "              \"requestParameters\": {\n" +
                "                \"txt1\": \"a of instanceId1\",\n" +
                "                \"txt2\": \"b of instanceId1\"\n" +
                "              },\n" +
                "              \"startDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\",\n" +
                "              \"!endDate\": \".*\",\n" +
                "              \"groupIds\": [\n" +
                "                \"gr1\",\n" +
                "                \"gr2\"\n" +
                "              ],\n" +
                "              \"version\": 0,\n" +
                "              \"!.*\": \".*\"\n" +
                "            },\n" +
                "            \"instanceId2\": {\n" +
                "              \"requestParameters\": {\n" +
                "                \"txt1\": \"c of instanceId2\",\n" +
                "                \"txt2\": \"d of instanceId2\"\n" +
                "              },\n" +
                "              \"startDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\",\n" +
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
                "          \"instanceId1\" : {\n" +
                "            \"requestParameters\" : {\n" +
                "              \"txt2\" : \"b of instanceId1\",\n" +
                "              \"txt1\" : \"a of instanceId1\"\n" +
                "            },\n" +
                "            \"version\" : 0,\n" +
                "            \"startDate\" : \"2022-07-18T09:37:34.189Z\",\n" +
                "            \"groupIds\" : [ \"gr2\", \"gr1\" ]\n" +
                "          },\n" +
                "          \"instanceId2\" : {\n" +
                "            \"requestParameters\" : {\n" +
                "              \"txt2\" : \"d of instanceId2\",\n" +
                "              \"txt1\" : \"c of instanceId2\"\n" +
                "            },\n" +
                "            \"version\" : 0,\n" +
                "            \"startDate\" : \"2022-07-18T09:37:34.342Z\",\n" +
                "            \"groupIds\" : [ \"gr2\", \"gr1\" ]\n" +
                "          }\n" +
                "        }}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
        } catch (AssertionError e) {
            System.out.println(e);
        }
    }

}
