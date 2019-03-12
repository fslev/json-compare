package ro.skyah.comparator.issues;

import org.junit.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue6Test {

    @Test
    public void testIssue1() {
        String expected = "[" +
                "{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}," +
                "{\n" +
                "  \"name\" : \"!division1\",\n" +
                "  \"vlan\" : \"!115\"\n" +
                "}]";
        String actual = "[{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testIssue1_negative() {
        String expected = "[{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}," +
                "{\n" +
                "  \"!name\" : \"division1\",\n" +
                "  \"!vlan\" : \"115\"\n" +
                "}]";
        String actual = "[{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}," +
                "{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"115\"\n" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testIssue2() {
        String expected = "[" +
                "{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"!115\"\n" +
                "}]";
        String actual = "[{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }
}
