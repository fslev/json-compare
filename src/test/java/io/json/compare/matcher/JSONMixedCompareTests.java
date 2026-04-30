package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class JSONMixedCompareTests {

    @Test
    public void compareSimple() {
        String expected = """
                {
                  "a": "val1",
                  "b": "val2",
                  "c": [
                    1,
                    null,
                    true,
                    "text",
                    {
                      "c1": "some text"
                    }
                  ],
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "g": ""
                }
                """;
        String actual = """
                {
                  "b": "val2",
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "c": [
                    "text",
                    null,
                    true,
                    1,
                    {
                      "c2": "some other text",
                      "c1": "some text"
                    }
                  ],
                  "a": "val1",
                  "g": "",
                  "h": "text again"
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareSimple_negative() {
        String expected = """
                {
                  "a": "val1",
                  "b": "val2",
                  "c": [
                    1,
                    null,
                    true,
                    "text",
                    {
                      "c1": "some text"
                    }
                  ],
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "g": ""
                }
                """;
        String actual = """
                {
                  "b": "val2",
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "c": [
                    "text",
                    null,
                    true,
                    1,
                    {
                      "c2": "some other text",
                      "c1": "DIFFERS HERE"
                    }
                  ],
                  "a": "val1",
                  "g": "",
                  "h": "text again"
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareSimpleViaDoNotFindUseCase() {
        String expected = """
                {
                  "a": "val1",
                  "b": "val2",
                  "c": [
                    1,
                    null,
                    true,
                    "text",
                    {
                      "c1": "some text"
                    }
                  ],
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "!field": "DON'T CARE ABOUT THE VALUE HERE"
                }
                """;
        String actual = """
                {
                  "b": "val2",
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "c": [
                    "text",
                    null,
                    true,
                    1,
                    {
                      "c2": "some other text",
                      "c1": "some text"
                    }
                  ],
                  "a": "val1",
                  "g": "",
                  "h": "text again"
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareSimpleViaDoNotFindUseCase_negative() {
        String expected = """
                {
                  "a": "val1",
                  "b": "val2",
                  "c": [
                    1,
                    null,
                    true,
                    "text",
                    {
                      "c1": "some text"
                    }
                  ],
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "!h": "DON'T CARE ABOUT THE VALUE HERE"
                }
                """;
        String actual = """
                {
                  "b": "val2",
                  "d": true,
                  "e": 1024,
                  "f": null,
                  "c": [
                    "text",
                    null,
                    true,
                    1,
                    {
                      "c2": "some other text",
                      "c1": "some text"
                    }
                  ],
                  "a": "val1",
                  "g": "",
                  "h": "text again"
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }
}
