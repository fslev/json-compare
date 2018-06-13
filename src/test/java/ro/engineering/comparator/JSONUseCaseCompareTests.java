package ro.engineering.comparator;

import org.junit.Test;

public class JSONUseCaseCompareTests {

    @Test
    public void compareViaDoNotFindUseCaseOnValue() {
        String expected = "{\"a\":\"!test\"}";
        String actual = "{\"a\":\"testing\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareViaDoNotFindUseCaseOn_negative() {
        String expected = "{\"a\":\"!test\"}";
        String actual = "{\"a\":\"test\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareViaDoNotFindUseCaseOnField() {
        String expected = "{\"!a\":\"value does not matter\"}";
        String actual = "{\"ab\":\"value does not matter\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareViaDoNotFindUseCaseOnField_negative() {
        String expected = "{\"!a\":\"value does not matter\"}";
        String actual = "{\"a\":\"value does not matter\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareLiteral() {
        String expected = "{\"a\":\"\\\\!some \\\\\\\\text\"}";
        String actual = "{\"a\":\"!some \\\\text\"}";
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
