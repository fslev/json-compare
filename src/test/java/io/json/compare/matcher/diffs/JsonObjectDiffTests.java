package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class JsonObjectDiffTests {

    @Test
    public void compareWithNumbers() {
        String expected = "{\"b\":1,\"a\":2}";
        String actual = "{\"a\":2,\"b\":1}";
        JSONCompare.assertMatches(expected, actual);
    }
}
