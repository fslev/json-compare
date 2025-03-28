package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonPathDiffTests {

    @Test
    void checkMultipleJsonPathDifferences() {
        String expected = "{\"#($.a.length())\":3,\"b\":\"val1\",\"x\":{\"x1\":{\"y11\":{\"#($.www)\":\"lorem1\"}}},\"z\":[{\"#($.length())\":1}]," +
                "\"u\":{\"#($.u1)\":{\"u11\":20209}}}";
        String actual = "{\"z\":[2,3,4],\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]," +
                "\"u\":{\"u1\":{\"u11\":20000}}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "\\Q$.#($.a.length())\\E.*Expected json path result.*" +
                "3.*But got.*2.*" +
                "\\Q$.b\\E.*Expected value: \"val1\" But got: \"val2\".*" +
                "\\Q$.x.x1.y11.#($.www)\\E.*No results for path.*" +
                "\\Q$.z.#($.length())\\E.*Expected json path result.*" +
                "1.*But got.*3.*" +
                "\\Qu.#($.u1).u11\\E.*Expected value: 20209 But got: 20000.*Expected json path result.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\"#($.a.length())\":2,\"b\":\"val2\",\"x\":{\"x1\":{\"y11\":{\"#($.a)\":\"lorem2\"}}}," +
                "\"u\":{\"#($.u1)\":{\"u11\":20000}}}";
        String actual1 = "{\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]," +
                "\"u\":{\"u1\":{\"u11\":20000}}}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    void checkMultipleJsonPathDifferencesFromArray() {
        String expected = "[false, {\"#($.length())\":1}, \"b\",{\"x\":{\"#($.length())\":2}}]";
        String actual = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.#($.length())\\E.*Expected json path result.*" +
                "1.*But got.*4.*" +
                "\\Q$[3]\\E was not found:.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "[false, {\"#($.length())\":1}, \"b\",{\"x\":{\"#($.length())\":3}}]";
        String actual1 = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1));
        assertTrue(error1.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.#($.length())\\E.*Expected json path result.*" +
                "1.*But got.*4.*"));
        JSONCompare.assertNotMatches(expected1, actual1);

        String expected2 = "[false, {\"#($.length())\":4}, \"b\",{\"x\":{\"#($.length())\":3}}]";
        String actual2 = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        JSONCompare.assertMatches(expected2, actual2);
    }

    @Test
    void checkDoNotMatchAnyAndJsonPathsFromArray() {
        String expected = "[false, {\"#($.length())\":1}, \"b\",{\"x\":{\"#($.length())\":2}}, \"!.*\"]";
        String actual = "[\"b\",false,{\"x\":[1,2,5]}, {\"w\":\"yyyy\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$.#($.length())\\E.*" +
                "1.*But got.*4.*" +
                "\\Q$[3]\\E was not found:.*" +
                "\\Q$[4]\\E -> Expected condition \"!.*\" was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "[false, {\"#($.length())\":3}, \"b\",{\"x\":{\"#($.length())\":3}}, \"!.*\"]";
        String actual1 = "[\"b\",false,{\"x\":[1,2,5]}]";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    void checkDoNotMatchAnyAndJsonPathsFromObject() {
        String expected = "{\"#($.length())\":2, \"b\":\"val1\", \"!.*\":\".*\"}";
        String actual = "{\"b\":\"val1\", \"a\":\"val2\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);


        String expected1 = "{\"#($.length())\":2, \"b\":\"val1\", \"!name\":\".*\", \"!.*\":\".*\"}";
        String actual1 = "{\"b\":\"val1\", \"a\":\"val2\"}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected1, actual1);

        expected1 = "{\"#($.length())\":1, \"b\":\"val1\", \"!name\":\".*\", \"!.*\":\".*\"}";
        actual1 = "{\"b\":\"val1\"}";
        JSONCompare.assertMatches(expected1, actual1);

        expected1 = "{\"#($.length())\":2,  \"a\":\"val2\", \"b\":\"val1\", \"!name\":\".*\", \"!.*\":\".*\"}";
        actual1 = "{\"b\":\"val1\", \"a\":\"val2\"}";
        JSONCompare.assertMatches(expected1, actual1);

        expected1 = "{\"#($.length())\":0, \"!name\":\".*\", \"!.*\":\".*\"}";
        actual1 = "{}";
        JSONCompare.assertMatches(expected1, actual1);
    }
}
