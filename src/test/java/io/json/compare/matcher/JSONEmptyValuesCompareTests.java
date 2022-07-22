package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class JSONEmptyValuesCompareTests {

    @Test
    public void compareWithEmptyValue() {
        String expected = "{\"a\":\"\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithEmptyValue_negative() {
        String expected = "{\"a\":\"not empty\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareEmptyWithEmptyValue_negative() {
        String expected = "{\"a\":\"!\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareEmptyAndNullValue_negative() {
        String expected = "{\"a\":\"\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotMatches(expected, actual);
    }
}
