package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonRealWorldDiffTests {

    @Test
    void compareJsonObjectsAndCheckForDifferences() {
        String expected = """
                {
                  "caught": false,
                  "pain": {
                    "range": [
                      "bell",
                      "blue",
                      -2059921070
                    ],
                    "not_anyone": -1760889549.4041045,
                    "flat": -2099670336
                  }
                }
                """;
        String actual = """
                        {
                          "caught": true,
                          "pain": {
                            "range": [
                              "bell",
                              "red",
                              -2059921075
                            ],
                            "anyone": -1760889549.4041045,
                            "flat": -2099670336
                          },
                          "broad": "invented"
                        }
                """;

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$.caught\\E.*Expected value: false But got: true.*" +
                "\\Q$.pain.range[1]\\E was not found.*\"blue\".*" +
                "\\Q$.pain.range[2]\\E was not found.*-2059921070.*" +
                "\\Q$.pain.not_anyone\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }


}
