package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue13Test {

    @Test
    public void testComparisonOfJsonWithUnintendedRegex() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        JSONCompare.assertEquals(expected, actual);
    }
}
