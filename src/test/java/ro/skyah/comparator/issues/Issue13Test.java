package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue13Test {
    //Issue: https://github.com/fslev/json-compare/issues/28

    @Test
    public void testComparisonOfJsonWithUnintendedRegex() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        JSONCompare.assertEquals(expected, actual);
    }
}
