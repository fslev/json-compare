package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JSONCompare;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Issue13Test {
    //Issue: https://github.com/fslev/json-compare/issues/28

    @Test
    // Check for hint message from failed comparison of jsons with unintentional regexes
    public void compareJsonWithUnintentionalRegexAndDefaultComparator() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertEquals(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Hint"));
        }
        try {
            JSONCompare.assertEquals(expected, actual, new DefaultJsonComparator());
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Hint"));
        }
    }

    @Test
    // Check hint message is missing from failed comparison with custom comparator
    public void compareJsonWithUnintentionalRegexAndCustomComparator() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertEquals(expected, actual, new CustomComparator());
        } catch (AssertionError e) {
            assertFalse(e.getMessage().contains("Hint"));
        }
    }

    private static class CustomComparator extends DefaultJsonComparator {

    }
}
