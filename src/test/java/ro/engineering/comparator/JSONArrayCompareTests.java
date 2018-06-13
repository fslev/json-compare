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
    public void compareArraysWithDuplicatedElements() {
        String expected = "[2,2,4,4,4]";
        String actual = "[4,4,4,4,2,2]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareArraysWithDuplicatedElements_negative() {
        String expected = "[2,2,2,4,4,4]";
        String actual = "[4,4,4,4,2,2]";
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
    public void compareWithNoExtraElements() {
        String expected = "[1,\"test\",4,\"!.*\"]";
        String actual = "[4,1,\"test\"]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithNoExtraElements_negative() {
        String expected = "[1,\"test\",\"!.*\",4]";
        String actual = "[4,1,\"test\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithExtraElements() {
        String expected = "[1,\"test\",4,\".*\"]";
        String actual = "[4,1,\"test\",false]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithExtraElements_negative() {
        String expected = "[1,\"test\",4,\".*\"]";
        String actual = "[4,1,\"test\"]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void deepCompareJsonArray() {
        String expected = "[\"val1\",\"val2\",[10,[\"val3\"],10,false]]";
        String actual = "[\"val2\",\"val1\",[10,10,false,[\"val3\"]]]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void deepCompareJsonArray_negative() {
        String expected = "[\"val1\",\"val2\",[10,10,false,[\"val3\"]]]";
        String actual = "[\"val2\",\"val1\",[10,10,false,[\"notval3\"]]]";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
