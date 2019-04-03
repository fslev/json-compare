package ro.skyah.comparator.issues;

import org.junit.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue7Test {

    @Test
    public void matchUseCaseLiterals() {
        String expected = "{\"records\": [\".*\"]}";
        String actual = "{\"records\": [\".*\" ]}";
        JSONCompare.assertEquals(expected, actual);
        expected = "{\"records\": [\"\\!.*\"]}";
        actual = "{\"records\": [\"!.*\" ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void matchUseCaseLiterals_negative() {
        String expected = "{\"records\": [\"!.*\"]}";
        String actual = "{\"records\": [\"!.*\" ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoExtraElements() {
        String expected = "{\"records\": [\"!.*\"]}";
        String actual = "{\"records\": []}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoExtraElements_negative() {
        String expected =
                "{\"records\":[\"!.*\"]}";
        String actual = "{\"records\":[{\"a\":0}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [\"c\"]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [\"b\"]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasJsonObjectElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [{\".*\": \".*\"}]}";
        String actual = "{\"records\": [{\"a\": 0}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue_negative1() {
        String expected = "{\"records\": [{\"!.*\": \".*\"}]}";
        String actual = "{\"records\": [\"a\", [true]]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue2() {
        String expected = "{\"records\": [{\"!.*\": \".*\"}]}";
        String actual = "{\"records\": [{}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue3() {
        String expected = "{\"records\": [{\"a\": \"!b\"}]}";
        String actual = "{\"records\": [{\"a\": \"c\"}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue3_negative() {
        String expected = "{\"records\": [{\"a\": \"!b\"}]}";
        String actual = "{\"records\": [{\"a\": \"b\"}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue1() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [{\"a\": 0}, \"c\"]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [{\"a\": 0}, \"b\"]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObject() {
        String expected = "{\"records\": [ {\"!a\": 0} ]}";
        String actual = "{\"records\": [ \"b\" ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObject_negative() {
        String expected = "{\"records\": [ {\"!a\": 0} ]}";
        String actual = "{\"records\": [ \"b\", {\"a\": 0} ]}";
        JSONCompare.assertEquals(expected, actual);
    }


    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonArray() {
        String expected = "{\"records\": [ [\"!a\"] ]}";
        String actual = "{\"records\": [ \"b\" ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonArrayElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [ [\"!c\"] ]}";
        String actual = "{\"records\": [ [\"b\"] ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoJsonArrayElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!t.*\"]}";
        String actual = "{\"records\": [ [\"b\"], true ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObjectValue() {
        String expected = "{\"records\": [ {\"!b\":\"0\"} ]}";
        String actual = "{\"records\": [{\"a\":\"0\"}]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedJsonObjectValue_negative() {
        String expected = "{\"records\": [ {\"!b\":\"0\"} ]}";
        String actual = "{\"records\": [{\"a\":\"0\"}, [true], {\"b\":\"0\"} ]}";
        JSONCompare.assertEquals(expected, actual);
    }
}
