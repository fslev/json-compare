package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonPathDiffTests {

    @Test
    public void checkMultipleJsonPathDifferences() {
        String expected = "{\"#($.a.length())\":3,\"b\":\"val1\",\"x\":{\"x1\":{\"y11\":{\"#($.www)\":\"lorem1\"}}},\"z\":[{\"#($.length())\":1}]," +
                "\"u\":{\"#($.u1)\":{\"u11\":20209}}}";
        String actual = "{\"z\":[2,3,4],\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]," +
                "\"u\":{\"u1\":{\"u11\":20000}}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "\\QJson path '$.a.length()' -> Expected json path result\\E.*" +
                "3.*But got.*2.*" +
                "b ->.*Expected value: \"val1\" But got: \"val2\".*" +
                "x -> x1 -> y11 -> \\QJson path '$.www'\\E -> No results for path.*" +
                "z -> \\QJson path '$.length()' -> Expected json path result\\E.*" +
                "1.*But got.*3.*" +
                "u -> \\QJson path '$.u1' -> Expected json path result\\E.*" +
                "u11 ->.*Expected value: 20209 But got: 20000.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\"#($.a.length())\":2,\"b\":\"val2\",\"x\":{\"x1\":{\"y11\":{\"#($.a)\":\"lorem2\"}}}}";
        String actual1 = "{\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    public void checkMultipleJsonPathDifferencesFromArray() {
        String expected = "[false, {\"#($.length())\":1}, \"b\",{\"x\":{\"#($.length())\":2}}]";
        String actual = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\QJson path '$.length()' -> Expected json path result\\E.*" +
                "1.*But got.*4.*" +
                "Expected element from position 4 was NOT FOUND:.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "[false, {\"#($.length())\":1}, \"b\",{\"x\":{\"#($.length())\":3}}]";
        String actual1 = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1));
        assertTrue(error1.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\QJson path '$.length()' -> Expected json path result\\E.*" +
                "1.*But got.*4.*"));
        JSONCompare.assertNotMatches(expected1, actual1);

        String expected2 = "[false, {\"#($.length())\":4}, \"b\",{\"x\":{\"#($.length())\":3}}]";
        String actual2 = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        JSONCompare.assertMatches(expected2, actual2);
    }
}
