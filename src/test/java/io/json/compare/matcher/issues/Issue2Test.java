package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/fslev/json-compare/issues/2
 */
public class Issue2Test {

    @Test
    public void testIssue() {
        String expected =
                "{\"field\":[\"value wi(th parentheses\", 4, 3]}";
        String actual =
                "{\"field\":[3, 4, \"value wi(th parentheses\"]}";
        JSONCompare.assertMatches(expected, actual);
    }
}
