package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class JSONNullCompareTests {

    @Test
    public void compareWithNullValue() {
        String expected = """
                {
                  "a": null
                }
                """;
        String actual = """
                {
                  "a": null
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithNullTextValue() {
        String expected = """
                {
                  "a": "null"
                }
                """;
        String actual = """
                {
                  "a": "null"
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithNullTextValue_negative() {
        String expected = """
                {
                  "a": "null"
                }
                """;
        String actual = """
                {
                  "a": null
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithRegex() {
        String expected = """
                {
                  "a": "null|text"
                }
                """;
        String actual = """
                {
                  "a": null
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithRegex_negative() {
        String expected = """
                {
                  "a": "false|text"
                }
                """;
        String actual = """
                {
                  "a": null
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareByNegativeUseCase() {
        String expected = """
                {
                  "a": "!null"
                }
                """;
        String actual = """
                {
                  "a": "txt"
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareByNegativeUseCase_negative() {
        String expected = """
                {
                  "a": "!null"
                }
                """;
        String actual = """
                {
                  "a": null
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareArrays() {
        String expected = """
                [
                  null
                ]
                """;
        String actual = """
                [
                  null
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareArrays_negative() {
        String expected = """
                [
                  null
                ]
                """;
        String actual = """
                [
                  2
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareArraysWithExpectedNullAsString() {
        String expected = """
                [
                  "null"
                ]
                """;
        String actual = """
                [
                  "null"
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareArraysWithExpectedNullAsString_negative() {
        String expected = """
                [
                  "null"
                ]
                """;
        String actual = """
                [
                  null
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareArraysWithActualNullAsString_negative() {
        String expected = """
                [
                  null
                ]
                """;
        String actual = """
                [
                  "null"
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareArraysWithExpectedDoNotFindNull() {
        String expected = """
                [
                  "!null"
                ]
                """;
        String actual = """
                [
                  null
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }
}
