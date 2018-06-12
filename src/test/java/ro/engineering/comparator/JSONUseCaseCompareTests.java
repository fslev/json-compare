package ro.engineering.comparator;

import org.junit.Test;

public class JSONUseCaseCompareTests {

    @Test
    public void compareLiteral() {
        String expected = "{\"a\":\"\\\\!some \\\\\\\\!text\"}";
        String actual = "{\"a\":\"!some \\\\!text\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareLiteral_negative() {
        String expected = "{\"a\":\"\\\\\\\\!some text\"}";
        String actual = "{\"a\":\"!some text\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareMultipleEscapedLiteral() {
        String expected = "{\"a\":\"\\\\\\\\!some text\"}";
        String actual = "{\"a\":\"\\\\!some text\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareMultipleEscapedLiteral_negative() {
        String expected = "{\"a\":\"\\\\!some text\"}";
        String actual = "{\"a\":\"\\\\!some text\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
