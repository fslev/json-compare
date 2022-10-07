package io.json.compare.matcher.readme;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadmeTests {

    @Test
    public void matchJsonConvertibleJavaObjects() {
        String expectedString = "{\"a\":1, \"b\": [4, 2, \"\\\\d+\"]}";
        String actualString = "{\"a\":1, \"b\":[4, 2, 5], \"c\":3}";
        JSONCompare.assertMatches(expectedString, actualString); // assertion passes

        // actual represented as Map
        Map<String, Object> actualMap = new HashMap<>();
        actualMap.put("a", 1);
        actualMap.put("b", Arrays.asList(4, 2, 5));
        actualMap.put("c", 3);
        JSONCompare.assertMatches(expectedString, actualMap); // assertion passes

        // Failed assertion
        String anotherActualString = "{\"a\":10, \"b\":[4, 20, 5], \"c\":3}";
        JSONCompare.assertNotMatches(expectedString, anotherActualString); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expectedString, anotherActualString));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "a ->.*Expected value: 1 But got: 10.*" +
                "b ->.*Expected element from position 2 was NOT FOUND.*"));
    }

    @Test
    public void checkJsonInclusion() {
        // Check JSON object inclusion
        String expected = "{\"b\":\"val1\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.assertMatches(expected, actual); // assertion passes

        // JSON objects MUST have same sizes
        String expected1 = "{\"b\":\"val1\"}";
        String actual1 = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.assertNotMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1,
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON OBJECT has extra fields.*"));
    }

    @Test
    public void checkJsonElementsOrder() {
        // JSON Array strict order is by default ignored
        String expected = "[1, 2, 3]";
        String actual = "[3, 2, 1, 5, 4]";
        JSONCompare.assertMatches(expected, actual); // assertion passes

        // Check JSON Array strict order
        String expected1 = "[1, 2, 3]";
        String actual1 = "[3, 2, 1, 5, 4]";
        JSONCompare.assertNotMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER))); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "JSON ARRAY elements differ at position 1.*" +
                "JSON ARRAY elements differ at position 3.*"));
    }
}
