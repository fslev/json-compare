package ro.skyah.comparator.issues;

import org.junit.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue10Test {

    @Test
    public void testMultipleNegationInJsonArray() {
        String expected = "{\"statuses\":[ \"!a.*\",\"!b.*\" ]}";
        String actual = "{\"statuses\":[\"abc\",\"cde\"]}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
