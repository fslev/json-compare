package ro.engineering.comparator;

import org.junit.Test;

public class JSONArrayCompareTests {

    @Test
    public void compareEmptyArrays() {
        String expected = "[]";
        String actual = "[]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareEmptyArrays_negative() {
        String expected = "[1]";
        String actual = "[]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareSimple() {
        String expected = "[\"val1\",\"val2\"]";
        String actual = "[\"val2\",\"val1\"]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareSimple_negative() {
        String expected = "[\"val1\",\"val2\"]";
        String actual = "[\"val2\",\"val3\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareDoNotFindElement() {
        String expected = "[\"!val1\",\"val2\"]";
        String actual = "[\"val2\",\"val2\"]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareDoNotFindElement_negative() {
        String expected = "[\"!val1\",\"val2\"]";
        String actual = "[\"val2\",\"val1\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithWithNoExtraElements() {
        String expected = "[1,\"test\",4,\"!.*\"]";
        String actual = "[4,1,\"test\"]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithWithNoExtraElements_negative() {
        String expected = "[1,\"test\",\"!.*\",4]";
        String actual = "[4,1,\"test\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
