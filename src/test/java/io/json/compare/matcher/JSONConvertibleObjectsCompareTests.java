package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONConvertibleObjectsCompareTests {

    @Test
    public void compareMaps() {
        String expected = """
                {
                  "a": 1,
                  "b": [
                    4,
                    2,
                    "\\\\d+"
                  ]
                }
                """;
        Map<String, Object> actual = new HashMap<>();
        actual.put("a", 1);
        actual.put("b", Arrays.asList(1, 2, 3, 4));
        JSONCompare.compare(expected, actual).assertMatches();
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertNotMatches();
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches();

        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("a", "1");
        expectedMap.put("b", Arrays.asList(4, 2, "\\d+"));
        JSONCompare.compare(expectedMap, actual).assertMatches();
        JSONCompare.compare(expectedMap, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertNotMatches();
        JSONCompare.compare(expectedMap, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
        JSONCompare.compare(expectedMap, actual).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches();
    }

    @Test
    public void checkJsonConversionErrorMessage() {
        String expected = "{\"a\":1,\"b\":[4,2,d]}";
        Map<String, Object> actual = new HashMap<>();
        actual.put("a", 1);
        RuntimeException e = assertThrows(RuntimeException.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(e.getMessage().contains("Invalid JSON"));
        RuntimeException e1 = assertThrows(RuntimeException.class, () -> JSONCompare.compare("{\"a\":1}", "{\"a:1}").assertMatches());
        assertTrue(e1.getMessage().contains("Invalid JSON"));
        RuntimeException e2 = assertThrows(RuntimeException.class, () -> JSONCompare.compare("{\"a\":1}", "{\"a:2}").assertNotMatches());
        assertTrue(e2.getMessage().contains("Invalid JSON"));
    }

    @Test
    public void compareNumbers() {
        BigDecimal expectedBigDec = new BigDecimal(20000);
        BigDecimal actualBigDec = new BigDecimal(20000L);
        JSONCompare.compare(expectedBigDec, actualBigDec).assertMatches();
        BigInteger expectedBigInt = new BigInteger("20000000");
        BigInteger actualBigInt = new BigInteger("20000000");
        JSONCompare.compare(expectedBigInt, actualBigInt).assertMatches();
        Double expectedDouble = 0.12454543D;
        Double actualDouble = 0.12454543D;
        JSONCompare.compare(expectedDouble, actualDouble).assertMatches();
    }
}
