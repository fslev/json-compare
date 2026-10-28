package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONValueToNodeCompareTests {

    @Test
    void defaultComparatorDoesNotMatchTextWithObjectOrArray() {
        JSONCompare.compare("{\"a\":\"\\\\{.*\"}", "{\"a\":{\"b\":1}}").assertNotMatches();
        JSONCompare.compare("{\"a\":\"\\\\[.*\"}", "{\"a\":[1]}").assertNotMatches();
    }

    @Test
    void customComparatorCanMatchTextWithObjectAndArray() {
        NodeAcceptingComparator comparator = new NodeAcceptingComparator();
        JSONCompare.compare("{\"a\":\"any\",\"b\":[\"any\"]}", "{\"a\":{\"x\":1},\"b\":[[1,2]]}")
                .comparator(comparator).assertMatches();
        assertEquals(List.of("{\"x\":1}", "[1,2]"), comparator.acceptedNodes);
    }

    @Test
    void customComparatorCanRejectTextWithObject() {
        JSONCompare.compare("{\"a\":\"other\"}", "{\"a\":{\"x\":1}}")
                .comparator(new NodeAcceptingComparator()).assertNotMatches();
    }

    private static class NodeAcceptingComparator implements JsonComparator {
        private final List<String> acceptedNodes = new ArrayList<>();

        @Override
        public boolean compareValues(Object expected, Object actual) {
            return expected.equals(actual);
        }

        @Override
        public boolean compareFields(String expected, String actual) {
            return expected.equals(actual);
        }

        @Override
        public boolean compareValueToNode(String expected, JsonNode actual) {
            if (!expected.equals("any")) {
                return false;
            }
            acceptedNodes.add(actual.toString());
            return true;
        }
    }
}
