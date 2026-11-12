package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONCompareMessageTests {

    @Test
    public void checkMessageFromJSONObjectFailedCompare() {
        String expected = """
                {
                  "a": true
                }
                """;
        String actual = """
                {
                  "ab": true
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_1() {
        String expected = """
                {
                  "a": true
                }
                """;
        String actual = """
                {
                  "ab": true
                }
                """;
        try {
            JSONCompare.compare(expected, actual).comparator(null).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative() {
        String expected = """
                {
                  "a": true,
                  "b": 0
                }
                """;
        String actual = """
                {
                  "a": true,
                  "b": 0
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkMessageFromJSONObjectFailedCompare_negative1() {
        String expected = """
                {
                  "a": true,
                  "b": 0
                }
                """;
        String actual = """
                {
                  "a": true,
                  "b": 0
                }
                """;
        try {
            JSONCompare.compare(expected, actual).comparator(null).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).message("JSONs do match").assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs do match") && e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkDefaultMessageFromJSONsWhichDoNotMatch() {
        String expected = """
                {
                  "a": true
                }
                """;
        String actual = """
                {
                  "a": true
                }
                """;
        try {
            JSONCompare.compare(expected, actual).assertNotMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs are equal"));
        }
    }

    @Test
    public void checkNullMessageFromJSONObjectFailedCompare() {
        String expected = """
                {
                  "a": true
                }
                """;
        String actual = """
                {
                  "ab": true
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("$.a was not found"));
        }
    }

    @Test
    public void checkMessageFromFailedMatchingBetweenHighDepthJsons() {
        String expected = """
                {
                  "@": {
                    "instanceId2": {
                      "!endDate": ".*",
                      "groupIds": [
                        "gr1",
                        "gr2"
                      ]
                    },
                    "version": 0,
                    "!.*": ".*"
                  }
                }
                """;
        String actual = """
                {
                  "@": {
                    "instanceId2": {
                      "version": 0,
                      "groupIds": [
                        "gr2",
                        "gr1"
                      ]
                    }
                  }
                }
                """;

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.@.instanceId2\\E: Actual JSON OBJECT has extra fields.*" +
                "\\Q$.@.version\\E was not found.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertNotMatches();
    }

    @Test
    public void failureMessageHasNoMarkdownSyntax() {
        String expected = """
                {
                  "type": {"x": 1},
                  "missing": 1,
                  "value": "abc",
                  "#($.nopath)": 1,
                  "users": [{"id": 1, "name": "Alice"}],
                  "tags": ["a", ".*"],
                  "nums": [1]
                }
                """;
        String actual = """
                {
                  "type": [1],
                  "value": "xyz",
                  "users": [{"id": 1, "name": "Bob"}],
                  "tags": ["a"],
                  "nums": [1, 2],
                  "extra": true
                }
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual)
                .modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        String message = error.getMessage();
        assertTrue(message.contains("==== DIFF 1 OF "), message);
        assertTrue(message.contains("EXPECTED JSON:") && message.contains("ACTUAL JSON:"), message);
        assertTrue(message.contains("Closest unmatched ACTUAL element"), message);
        for (String token : new String[]{"->", "__", "  - ", "<", ">"}) {
            assertFalse(message.contains(token), "Markdown token '" + token + "' in:\n" + message);
        }
    }

}
