package ro.skyah.comparator.issues;

import org.junit.Ignore;
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
                "}, {" +
                "  \"name\" : \"division2\",\n" +
                "  \"vlan\" : \"117\"" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }


    @Test
    public void testIssue1a() {
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
                "  \"a\" : \"division2\",\n" +
                "  \"b\" : \"114\"\n" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testIssue1a_negative() {
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
                "  \"a\" : \"division2\",\n" +
                "  \"vlan\" : \"114\"\n" +
                "}]";
        JSONCompare.assertNotEquals(expected, actual);
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
    @Ignore
    public void testIssue2() {
        String expected = "[" +
                "{\n" +
                "  \"!name\" : \"division1\",\n" +
                "  \"!vlan\" : \"115\"\n" +
                "}]";
        String actual = "[{\n" +
                "  \"name\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}]";
        JSONCompare.assertEquals(expected, actual);
    }
}
