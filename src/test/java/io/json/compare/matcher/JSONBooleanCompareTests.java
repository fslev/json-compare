package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class JSONBooleanCompareTests {

    @Test
    public void compareSimple() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareSimple_negative() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":false}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithExpectedString() {
        String expected = "{\"a\":\"true\"}";
        String actual = "{\"a\":true}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithActualString() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":\"true\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithRegex() {
        String expected = "{\"a\":\"true|false\"}";
        String actual = "{\"a\":true}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithRegex_negative() {
        String expected = "{\"a\":\"false|text\"}";
        String actual = "{\"a\":true}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareViaDoNotMatchUseCase() {
        String expected = "{\"a\":\"!true\"}";
        String actual = "{\"a\":false}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareViaDoNotMatchUseCase_negative() {
        String expected = "{\"a\":\"!false\"}";
        String actual = "{\"a\":false}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }
}
