package com.github.fslev.comparator;

import org.junit.Test;

public class JSONBooleanCompareTests {

    @Test
    public void compareSimple() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareSimple_negative() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":false}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithExpectedString() {
        String expected = "{\"a\":\"true\"}";
        String actual = "{\"a\":true}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithActualString() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":\"true\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithRegex() {
        String expected = "{\"a\":\"true|false\"}";
        String actual = "{\"a\":true}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithRegex_negative() {
        String expected = "{\"a\":\"false|text\"}";
        String actual = "{\"a\":true}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareViaDoNotMatchUseCase() {
        String expected = "{\"a\":\"!true\"}";
        String actual = "{\"a\":false}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareViaDoNotMatchUseCase_negative() {
        String expected = "{\"a\":\"!false\"}";
        String actual = "{\"a\":false}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
