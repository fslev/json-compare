package io.json.compare.matcher.readme;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
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
}
