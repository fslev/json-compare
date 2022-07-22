package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue8Test {

    @Test
    public void testJsonObjectMatchUseCase() {
        String expected = "{\"records\":[ {\"a\":0} ]}";
        String actual = "{\"records\":[ true, {\"a\":0} ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testJsonObjectMatchUseCase_negative() {
        String expected = "{\"records\":[ {\"a\":0} ]}";
        String actual = "{\"records\":[ {\"a\":1} ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void testJsonArrayMatchUseCase() {
        String expected = "{\"records\":[ 1,true, \"b\", {\"a\": 0}, [2] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, [2], 1 ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testJsonArrayMatchUseCase_negative() {
        String expected = "{\"records\":[ 1,true, \"b\", {\"a\": 0}, [2] ]}";
        String actual = "{\"records\":[  \"b\", {\"b\": 0}, true, [2], 1 ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void testJsonObjectDoNotMatchUseCase() {
        String expected = "{\"records\":[ {\"!a\":0} ]}";
        String actual = "{\"records\":[ true, {\"b\":0} ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testJsonObjectDoNotMatchUseCase_negative() {
        String expected = "{\"records\":[ {\"!a\":0} ]}";
        String actual = "{\"records\":[ {\"a\":1} ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void testJsonArrayDoNotMatchUseCase() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, [2], 1 ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testJsonArrayDoNotMatchUseCase1() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, 1 ]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void testJsonArrayDoNotMatchUseCase_negative1() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, [\"c\"], true, 1 ]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void testDoNotMatchUseCaseCompareEscaped() {
        String expected = "{\"\\\\!records\":0}";
        String actual = "{\"!records\":0}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testDoNotMatchUseCaseCompareEscaped1() {
        String expected = "{\"\\\\Q\\\\!records\\\\E\":0}";
        String actual = "{\"\\\\!records\":0}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testDoNotMatchAnyUseCase() {
        String expected = "{\"records\":[  \"!.*\" ]}";
        String actual = "{\"records\":[  ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testDoNotMatchAnyUseCase_negative() {
        String expected = "{\"records\":[  \"!.*\" ]}";
        String actual = "{\"records\":{}}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void testDoNotMatchAnyUseCase_negative_A() {
        String expected = "{\"records\":[  \"!.*\" ]}";
        String actual = "{\"records\": [true]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

}
