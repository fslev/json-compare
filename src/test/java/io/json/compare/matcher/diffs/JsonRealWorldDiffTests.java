package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonRealWorldDiffTests {

    @Test
    void compareJsonObjectsAndCheckForDifferences() {
        String expected = "{\n" +
                "  \"caught\": false,\n" +
                "  \"pain\": {\n" +
                "    \"range\": [\n" +
                "      \"bell\",\n" +
                "      \"blue\",\n" +
                "      -2059921070\n" +
                "    ],\n" +
                "    \"not_anyone\": -1760889549.4041045,\n" +
                "    \"flat\": -2099670336\n" +
                "  }\n" +
                "}";
        String actual = "{\n" +
                "  \"caught\": true,\n" +
                "  \"pain\": {\n" +
                "    \"range\": [\n" +
                "      \"bell\",\n" +
                "      \"red\",\n" +
                "      -2059921075\n" +
                "    ],\n" +
                "    \"anyone\": -1760889549.4041045,\n" +
                "    \"flat\": -2099670336\n" +
                "  },\n" +
                "  \"broad\": \"invented\"\n" +
                "}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$.caught\\E.*Expected value: false But got: true.*" +
                "\\Q$.pain.range[1]\\E was not found.*\"blue\".*" +
                "\\Q$.pain.range[2]\\E was not found.*-2059921070.*" +
                "\\Q$.pain.not_anyone\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }


}
