package ro.engineering.comparator;

import org.junit.Test;

public class JSONNullCompareTests {

    @Test
    public void compareWithNullValue() {
        String expected = "{\"a\":null}";
        String actual = "{\"a\":null}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithNullTextValue() {
        String expected = "{\"a\":\"null\"}";
        String actual = "{\"a\":\"null\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithNullTextValue_negative() {
        String expected = "{\"a\":\"null\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithRegex() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\":\"null\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithRegex_negative() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareByNegativeUseCase() {
        String expected = "{\"a\":\"!null\"}";
        String actual = "{\"a\":\"txt\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareByNegativeUseCase_negative() {
        String expected = "{\"a\":\"!null\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareArrays() {
        String expected = "[null]";
        String actual = "[null]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareArrays_negative() {
        String expected = "[null]";
        String actual = "[2]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareArraysWithExpectedNullAsString() {
        String expected = "[\"null\"]";
        String actual = "[\"null\"]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareArraysWithExpectedNullAsString_negative() {
        String expected = "[\"null\"]";
        String actual = "[null]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareArraysWithActualNullAsString_negative() {
        String expected = "[null]";
        String actual = "[\"null\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareArraysWithExpectedDoNotFindNull() {
        String expected = "[\"!null\"]";
        String actual = "[null]";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
