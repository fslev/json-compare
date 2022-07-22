package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONValueNodeCompareTests {

    @Test
    public void compareTextNodes() {
        TextNode expected = new TextNode("some val");
        TextNode actual = new TextNode("some val");
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareEmptyTextNodes() {
        TextNode expected = new TextNode("");
        TextNode actual = new TextNode("");
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareMissingNodes() {
        JsonNode expected = new ObjectMapper().missingNode();
        JsonNode actual = new ObjectMapper().missingNode();
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareMissingNodes_negative() {
        JsonNode expected = new ObjectMapper().missingNode();
        JsonNode actual = new TextNode("");
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }

    @Test
    public void compareStringWithTextNode() {
        String expectedAsString = "\"some val\"";
        TextNode actual = new TextNode("some val");
        JSONCompare.assertMatches(expectedAsString, actual, null, null, null);
    }

    @Test
    public void compareUnquotedStringWithTextNodeFails() {
        String expectedAsString = "some val";
        TextNode actual = new TextNode("some val");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> JSONCompare.assertMatches(expectedAsString, actual));
        assertTrue(exception.getMessage().contains("Invalid JSON"));
    }

    @Test
    public void compareIntNodes() {
        IntNode expected = new IntNode(1000000);
        IntNode actual = new IntNode(1000000);
        JSONCompare.assertMatches(expected, actual);
    }
}
