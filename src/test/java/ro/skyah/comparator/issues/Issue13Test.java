package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.JSONCompare;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Issue13Test {
    //Issue: https://github.com/fslev/json-compare/issues/28

    @Test
    // Check for hint message from failed comparison of jsons with unintentional regexes
    public void compareJsonWithUnintentionalRegex() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertEquals(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Hint"));
        }
    }
}
