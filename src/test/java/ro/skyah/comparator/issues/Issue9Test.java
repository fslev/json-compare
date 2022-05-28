package ro.skyah.comparator.issues;

import org.junit.jupiter.api.Test;
import ro.skyah.comparator.JSONCompare;

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
