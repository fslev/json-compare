package io.json.compare.matcher.issues;

import org.junit.jupiter.api.Test;
import io.json.compare.JSONCompare;

public class Issue9Test {

    @Test
    public void testJsonsDoNotMatchNegativelook() {
        String expected = "{\"records\":[ {\"a\":\".((?!0).)*\"} ]}";
        String actual = "{\"records\":[ {\"b\":\"2\"}, {\"a\":\"s1s\"} ]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testJsonsDoNotMatchNegativelook_negative() {
        String expected = "{\"records\":[ {\"a\":\".((?!0).)*\"} ]}";
        String actual = "{\"records\":[ {\"b\":\"2\"}, {\"a\":\"s0s\"} ]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

}
