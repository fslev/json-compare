package ro.skyah.comparator.issues;

import ro.skyah.comparator.JSONCompare;
import ro.skyah.comparator.JsonComparator;
import org.junit.Test;

/**
 * https://github.com/fslev/json-compare/issues/2
 */
public class Issue2Test {

    @Test
    public void testIssue() {
        String expected =
                "{\"field\":\"value wi(th parentheses\"}";
        String actual =
                "{\"field\":\"value wi(th parentheses\"}";
        JSONCompare.assertEquals(expected, actual);
    }
}
