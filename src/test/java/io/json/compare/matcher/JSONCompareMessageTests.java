package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(message.contains("EXPECTED: {") && message.contains("BUT WAS: {"), message);
        assertTrue(message.contains("Closest unmatched ACTUAL element"), message);
        for (String token : new String[]{"->", "__", "  - ", "<", ">"}) {
            assertFalse(message.contains(token), "Markdown token '" + token + "' in:\n" + message);
        }
    }

    /**
     * IntelliJ's Cucumber runner reads only the message text to offer its "Click to see difference" link,
     * using this pattern (case-insensitive, DOTALL, find).
     */
    private static final Pattern INTELLIJ_EXPECTED_BUT_WAS =
            Pattern.compile("expected: (.*)\\s+but was: (.*)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    @Test
    public void checkMessageGivesBothJsonsToIdeDiffPatterns() throws IOException {
        String expected = "{\"a\": 1, \"b\": [\"x\"]}";
        String actual = "{\"a\": 2, \"b\": [\"y\"]}";
        AssertionError error = assertThrows(AssertionError.class,
                () -> JSONCompare.compare(expected, actual).message("Custom message").assertMatches());
        String message = error.getMessage();
        Matcher matcher = INTELLIJ_EXPECTED_BUT_WAS.matcher(message);
        assertTrue(matcher.find(), message);
        assertEquals(JSONCompare.prettyPrint(JsonUtils.toJson(expected)), matcher.group(1).stripTrailing());
        assertEquals(JSONCompare.prettyPrint(JsonUtils.toJson(actual)), matcher.group(2).stripTrailing());
        assertEquals(matcher.group(1).length() - matcher.group(1).stripTrailing().length(),
                matcher.group(2).length() - matcher.group(2).stripTrailing().length(),
                "Both values must end the same way, or the IDE diff shows a false difference on the last line");
    }

}
