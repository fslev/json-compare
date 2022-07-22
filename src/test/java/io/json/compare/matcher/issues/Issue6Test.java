package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        JSONCompare.assertMatches(expected, actual);
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
        JSONCompare.assertMatches(expected, actual);
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
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
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
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }


    @Test
    public void testIssue2() {
        String expected = "[" +
                "{\n" +
                "  \"!name\" : \"division1\",\n" +
                "  \"!vlan\" : \"115\"\n" +
                "}]";
        String actual = "[{\n" +
                "  \"names\" : \"division1\",\n" +
                "  \"vlan\" : \"116\"\n" +
                "}]";
        JSONCompare.assertNotMatches(expected, actual);
    }
}
