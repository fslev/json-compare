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
}
