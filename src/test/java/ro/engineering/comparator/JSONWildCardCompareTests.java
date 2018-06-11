package ro.engineering.comparator;

import org.junit.Test;

public class JSONWildCardCompareTests {

    @Test
    public void compareWithSimpleWildCard() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"some text\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithSimpleWildCard_negative() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"som text\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
