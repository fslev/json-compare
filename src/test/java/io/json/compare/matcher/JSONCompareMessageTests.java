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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertTrue(message.contains("Closest unmatched ACTUAL element"), message);
        // JUnit's " ==> expected: <...> but was: <...>" suffix is kept on purpose, for IDE diff links
        int suffix = message.indexOf(" ==> expected: <{");
        assertTrue(suffix > 0 && message.contains("}> but was: <{"), message);
        String libraryText = message.substring(0, suffix);
        for (String token : new String[]{"->", "__", "  - ", "<", ">"}) {
            assertFalse(libraryText.contains(token), "Markdown token '" + token + "' in:\n" + libraryText);
        }
    }

    /**
     * The patterns Cucumber's TeamCity formatter (io.cucumber:teamcity-formatter 0.3.1, used by IntelliJ for
     * Cucumber 7+) tries in this order on the trimmed message to give the IDE its "Click to see difference" link.
     */
    private static final Pattern[] CUCUMBER_TEAMCITY_COMPARE_PATTERNS = {
            Pattern.compile("expected: (.*)(?:\r\n|\r|\n) {5}but: was (.*)$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
            Pattern.compile("expected: (.*)(?:\r\n|\r|\n) but was: (.*)$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
            Pattern.compile("expected: <(.*)> but was: <(.*)>$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
            Pattern.compile("expected:\\s?<(.*)> but was:\\s?<(.*)>$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
            Pattern.compile("expected \\[(.*)] but found \\[(.*)]$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE)};

    private static Matcher cucumberTeamCityComparison(String message) {
        for (Pattern pattern : CUCUMBER_TEAMCITY_COMPARE_PATTERNS) {
            Matcher matcher = pattern.matcher(message.trim());
            if (matcher.find()) {
                return matcher;
            }
        }
        return null;
    }

    @Test
    public void checkMessageGivesBothJsonsToIdeDiffPatterns() throws IOException {
        String expected = "{\"a\": 1, \"b\": [\"x\"]}";
        String actual = "{\"a\": 2, \"b\": [\"y\"]}";
        AssertionError error = assertThrows(AssertionError.class,
                () -> JSONCompare.compare(expected, actual).message("Custom message").assertMatches());
        String message = error.getMessage();
        Matcher comparison = cucumberTeamCityComparison(message);
        assertNotNull(comparison, message);
        assertEquals(JSONCompare.prettyPrint(JsonUtils.toJson(expected)), comparison.group(1));
        assertEquals(JSONCompare.prettyPrint(JsonUtils.toJson(actual)), comparison.group(2));
    }

}
