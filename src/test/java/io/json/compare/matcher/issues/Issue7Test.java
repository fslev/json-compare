package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue7Test {

    @Test
    public void matchUseCaseLiterals() {
        String expected = "{\"records\": [\".*\"]}";
        String actual = "{\"records\": [\".*\" ]}";
        JSONCompare.assertMatches(expected, actual);
        expected = "{\"records\": [\"\\\\!.*\"]}";
        actual = "{\"records\": [\"!.*\" ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void matchUseCaseLiterals_negative() {
        String expected = "{\"records\": [\"!.*\"]}";
        String actual = "{\"records\": [\"!.*\" ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasNoExtraElements() {
        String expected = "{\"records\": [\"!.*\"]}";
        String actual = "{\"records\": []}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoExtraElements1() {
        String expected = "{\"records\": [\"!.*\"]}";
        String actual = "{\"records\": [{\"a\":0}]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements() {
        String expected = "{\"records\": [\".*\"]}";
        String actual = "{\"records\": [1,2]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements1() {
        String expected = "{\"records\": [\".*\"]}";
        String actual = "{\"records\": [{\"a\":0}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements2() {
        String expected =
                "{\"records\":[\".*\"]}";
        String actual = "{\"records\":[{\"a\":0}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements3() {
        String expected =
                "{\"records\":[\".+\"]}";
        String actual = "{\"records\":[false]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements4() {
        String expected = "{\"records\": [[1], false, \".*\"]}";
        String actual = "{\"records\": [[1], {\"a\":0}, false]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasExtraElements4_negative() {
        String expected = "{\"records\": [{\"a\":0}, [1], false, \".+\"]}";
        String actual = "{\"records\": [[1], {\"a\":0}, false]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoExtraElements3_negative() {
        String expected =
                "{\"records\":[\".+\"]}";
        String actual = "{\"records\":[]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [\"c\"]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [\"b\"]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasJsonObjectElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [{\".*\": \".*\"}]}";
        String actual = "{\"records\": [{\"a\": 0}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue_negative1() {
        String expected = "{\"records\": [{\"!.*\": \".*\"}]}";
        String actual = "{\"records\": [\"a\", [true]]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasAJsonObjectElementThatMatchesTheExpectedJsonObjectValue() {
        String expected = "{\"records\": [{\"!.*\": \".*\"}]}";
        String actual = "{\"records\": [{\"a\":0}]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasAJsonObjectElementThatMatchesTheExpectedJsonObjectValue1() {
        String expected = "{\"records\": [{\"!.*\": \".*\"}]}";
        String actual = "{\"records\": [\"test\"]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue3() {
        String expected = "{\"records\": [{\"a\": \"!b\"}]}";
        String actual = "{\"records\": [{\"a\": \"c\"}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue4() {
        String expected = "{\"records\": [{\"a\": \"!b\"}]}";
        String actual = "{\"records\": [{\"b\": \"c\"}]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue5() {
        String expected = "{\"records\": [{\"!a\": \"b\"}]}";
        String actual = "{\"records\": [{\"x\": \"b\"}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue6() {
        String expected = "{\"records\": [{\"!a\": \"b\"}]}";
        String actual = "{\"records\": [{\"x\": \"c\"}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonObjectDoNotFindKeyHasValueMatch() {
        String expected = "{\"!a\": \"b\"}";
        String actual = "{\"x\": \"b\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonObjectDoNotFindKeyHasValueMatch1() {
        String expected = "{\"!a\": \"b\"}";
        String actual = "{\"x\": \"c\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue3_negative() {
        String expected = "{\"records\": [{\"a\": \"!b\"}]}";
        String actual = "{\"records\": [{\"a\": \"b\"}]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasNoTextElementThatMatchesTheExpectedTextValue1() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [{\"a\": 0}, \"c\"]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!b\"]}";
        String actual = "{\"records\": [{\"a\": 0}, \"b\"]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObject1() {
        String expected = "{\"records\": [ {\"!a\": 0} ]}";
        String actual = "{\"records\": [ \"b\" ]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObject_negative() {
        String expected = "{\"records\": [ {\"!a\": 0} ]}";
        String actual = "{\"records\": [ \"b\", {\"a\": 0} ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }


    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonArray() {
        String expected = "{\"records\": [ [\"!a\"] ]}";
        String actual = "{\"records\": [ \"b\" ]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonArrayElementThatMatchesTheExpectedTextValue() {
        String expected = "{\"records\": [ [\"!c\"] ]}";
        String actual = "{\"records\": [ [\"b\"] ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonArrayElementThatMatchesTheExpectedTextValue_negative() {
        String expected = "{\"records\": [\"!t.*\"]}";
        String actual = "{\"records\": [ [\"b\"], true ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void checkJsonArrayHasNoElementThatMatchesTheExpectedJsonObjectValue() {
        String expected = "{\"records\": [ {\"!b\":\"0\"} ]}";
        String actual = "{\"records\": [{\"a\":\"0\"}]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void checkJsonArrayHasNoJsonObjectElementThatMatchesTheExpectedJsonObjectValue_negative() {
        String expected = "{\"records\": [ {\"!b\":\"0\"} ]}";
        String actual = "{\"records\": [{\"a\":\"0\"}, [true], {\"b\":\"0\"} ]}";
        JSONCompare.assertMatches(expected, actual);
    }
}
