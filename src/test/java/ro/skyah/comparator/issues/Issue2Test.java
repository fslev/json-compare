package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.JSONCompare;

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
        JSONCompare.assertEquals(expected, actual);
    }
}
