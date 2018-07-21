package ro.skyah.comparator;

import org.junit.Test;

public class JSONofDifferentTypesCompareTests {

    @Test
    public void compareObjectWithArray() {
        String expected = "{\"a\":\"!test\"}";
        String actual = "[\"a\",\"b\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareArrayWithObject() {
        String expected = "[\"a\",\"b\"]";
        String actual = "{\"a\":\"!test\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void compareStrangeValues() {
        String expected = "\"1\"Fds\"\"";
        String actual = "1";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
