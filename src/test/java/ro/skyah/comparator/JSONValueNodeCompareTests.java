package ro.skyah.comparator;

import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Test;

public class JSONValueNodeCompareTests {

    @Test
    public void compareTextNodes() {
        TextNode expected = new TextNode("some val");
        TextNode actual = new TextNode("some val");
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareStringWithTextNode() {
        String expectedAsString = "\"some val\"";
        TextNode actual = new TextNode("some val");
        JSONCompare.assertEquals(null, expectedAsString, actual);
    }

    @Test(expected = AssertionError.class)
    public void compareUnquotedStringWithTextNodeFails() {
        String expectedAsString = "some val";
        TextNode actual = new TextNode("some val");
        JSONCompare.assertEquals(null, expectedAsString, actual);
    }

    @Test
    public void compareIntNodes() {
        IntNode expected = new IntNode(1000000);
        IntNode actual = new IntNode(1000000);
        JSONCompare.assertEquals(expected, actual);
    }
}
