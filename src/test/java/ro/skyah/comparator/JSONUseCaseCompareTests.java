package ro.skyah.comparator;

import org.junit.jupiter.api.Test;

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

    @Test
    public void compareJsonObjectsViaFindAnyUseCase() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\": 1}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase_a() {
        String expected = "{\"a\":\"\\\\.*\"}";
        String actual = "{\"a\": 1}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase_negative() {
        String expected = "{\"a\":\"\\\\.*\"}";
        String actual = "{\"a\": [1]}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase_negative1() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"c\": \"0\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase1() {
        String expected = "{\".*\":\".*\"}";
        String actual = "{\"c\": [1]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase1_negative() {
        String expected = "{\".*\":\".*\"}";
        String actual = "[\"c\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase2() {
        String expected = "{\".*\":\".*\"}";
        String actual = "{\"c\": {\"a\": true}}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase3() {
        String expected = "{\"a\":[\".*\"]}";
        String actual = "{\"a\": [1]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaFindAnyUseCase3_negative() {
        String expected = "{\"a\":[\".*\"]}";
        String actual = "{\"a\": {\"b\":0}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase() {
        String expected = "[\".*\"]";
        String actual = "[1]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase_negative() {
        String expected = "[\".*\"]";
        String actual = "[]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase_negative1() {
        String expected = "[\".*\"]";
        String actual = "{\"a\":true}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase1() {
        String expected = "[\".*\"]";
        String actual = "[{\"a\":0}]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsViaDoNotFindAnyUseCase_negative() {
        String expected = "{\"!.*\":\".*\"}";
        String actual = "{\"c\": {\"a\": true}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase2() {
        String expected = "[1 , \".*\"]";
        String actual = "[{\"a\":0}, 1]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase2_negative() {
        String expected = "[1 , \".*\"]";
        String actual = "[1]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase2a() {
        String expected = "[1 , \"\\\\.*\"]";
        String actual = "[\"c\", 1]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaFindAnyUseCase2a_negative() {
        String expected = "[1 , \"\\\\.*\"]";
        String actual = "[{\"a\":0}, 1]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCase() {
        String expected = "[\"!.*\"]";
        String actual = "[]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCase_negative() {
        String expected = "[\"!.*\"]";
        String actual = "[{\"a\":0}]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCase1() {
        String expected = "[1,{\"a\":\".*\"},\"!.*\"]";
        String actual = "[{\"a\":0},1]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCase1_negative() {
        String expected = "[1,{\"a\":\".*\"},\"!.*\"]";
        String actual = "[{\"a\":0},1,true]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCaseEscaped() {
        String expected = "[1,2,\"\\\\Q!.*\\\\E\"]";
        String actual = "[2,\"!.*\",1]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonArraysViaDoNotFindAnyUseCaseEscaped_negative() {
        String expected = "[1,2,\"\\\\Q!.*\\\\E\"]";
        String actual = "[2,\"!.*\",1]";
        JSONCompare.assertEquals(expected, actual);
    }
}
