package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonValueDiffTests {

    @Test
    public void compareNulls() {
        String expected = "{\"a\":null}";
        String actual = "{\"a\":\"null\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*a ->.*Expected null:  But got: \"null\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareBooleans() {
        String expected = "{\"a\":false}";
        String actual = "{\"a\":\"false\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*a ->.*Expected boolean: false But got: \"false\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareNumbers() {
        String expected = "{\"a\":2}";
        String actual = "{\"a\":\"2\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*a ->.*Expected number: 2 But got: \"2\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareAll() {
        String expected = "{\"a\":null,\"b\":1,\"c\":false,\"d\":\"false\",\"e\":\"text\",\"f\":13432.543,\"f1\":13432.543}";
        String actual = "{\"a\":\"null\",\"b\":\"1\",\"c\":\"false\",\"d\":false,\"e\":false,\"f\":13432.543,\"f1\":\"13432.543\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "a ->.*Expected null:  But got: \"null\".*" +
                "b ->.*Expected number: 1 But got: \"1\".*" +
                "c ->.*Expected boolean: false But got: \"false\".*" +
                "e ->.*Expected value: \"text\" But got: false.*" +
                "f1 ->.*Expected number: 13432.543 But got: \"13432.543\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareAll_positive() {
        String expected = "{\"a\":null,\"b\":1,\"c\":false,\"d\":\"false\",\"e\":\"text\",\"f\":13432.543,\"f1\":13430.143}";
        String actual = "{\"a\":null,\"b\":1,\"c\":false,\"d\":false,\"e\":\"text\",\"f\":13432.543,\"f1\":13430.143}";
        JSONCompare.assertMatches(expected, actual);
    }
}
