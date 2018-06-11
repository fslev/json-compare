package ro.engineering.comparator;

import org.junit.Test;

public class JSONNullCompareTests {

    @Test
    public void compareWithNullValue() {
        String expected = "{\"a\":null}";
        String actual = "{\"a\":null}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithNullTextValue() {
        String expected = "{\"a\":\"null\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithRegex() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\":null}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
