package io.json.compare.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonPrettyPrintlTest {

    @Test
    public void testPrettyPrint() {
        String expected = "{\n" +
                "  \"a\" : \"test\"\n" +
                "}";
        assertEquals(expected, JSONCompare.prettyPrint(new ObjectMapper().createObjectNode().set("a", new TextNode("test"))));
    }

    @Test
    public void testNullPrettyPrint() {
        assertEquals("null", JSONCompare.prettyPrint(null));
    }
}
