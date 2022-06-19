package ro.skyah.comparator;

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

}
