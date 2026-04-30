package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONofDifferentTypesCompareTests {

    @Test
    public void compareObjectWithArray() {
        String expected = """
                {
                  "a": "!test"
                }
                """;
        String actual = """
                [
                  "a",
                  "b"
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareArrayWithObject() {
        String expected = """
                [
                  "a",
                  "b"
                ]
                """;
        String actual = """
                {
                  "a": "!test"
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareStrangeValues() {
        String expected = "\"1\"Fds\"\"";
        String actual = "1";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> JSONCompare.compare(expected, actual).assertNotMatches());
        assertTrue(exception.getMessage().contains("Invalid JSON"));
    }
}
