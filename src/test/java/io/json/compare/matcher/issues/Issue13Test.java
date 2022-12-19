package io.json.compare.matcher.issues;

import io.json.compare.CompareMode;
import io.json.compare.DefaultJsonComparator;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
            JSONCompare.assertMatches(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("unintentional regexes"));
        }
        try {
            JSONCompare.assertMatches(expected, actual, new DefaultJsonComparator(null));
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("unintentional regexes"));
            assertTrue(e.getMessage().contains("disabling case-sensitivity"));
        }
    }

    @Test
    // Check hint message is missing from failed comparison with custom comparator
    public void compareJsonWithUnintentionalRegexAndCustomComparator() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertMatches(expected, actual, new CustomComparator(null));
        } catch (AssertionError e) {
            assertFalse(e.getMessage().contains("unintentional regexes"));
            assertFalse(e.getMessage().contains("disabling case-sensitivity"));
        }
    }

    private static class CustomComparator extends DefaultJsonComparator {
        public CustomComparator(Set<CompareMode> compareModes) {
            super(compareModes);
        }
    }
}
