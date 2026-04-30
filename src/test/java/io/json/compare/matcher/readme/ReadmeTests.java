package io.json.compare.matcher.readme;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadmeTests {

    @Test
    void matchJsonConvertibleJavaObjects() {
        String expectedString = """
                    {
                      "string": "I'm on a seafood diet. I see food and I eat it!",
                      "number": "\\\\d+.\\\\d+",
                      "object": {
                        "pun": "\\\\QWhy don't skeletons fight each other? They don't have the guts!\\\\E"
                      },
                      "array": [".*", "\\\\d+", true, null],
                      "boolean": "true|false"
                    }
                """;
        String actualString = """
                    {
                      "string": "I'm on a seafood diet. I see food and I eat it!",
                      "number": 0.99,
                      "object": {
                        "pun": "Why don't skeletons fight each other? They don't have the guts!"
                      },
                      "array": ["pancake", 18, true, null],
                      "boolean": true
                    }
                """;
        JSONCompare.compare(expectedString, actualString).assertMatches(); // assertion passes

        String anotherExpectedString = "{\"a\":1, \"b\": [4, \"ipsum\", \"\\\\d+\"]}";

        // actual represented as Map
        Map<String, Object> actualMap = new HashMap<>();
        actualMap.put("a", 1);
        actualMap.put("b", Arrays.asList("ipsum", 4, 5));
        actualMap.put("c", true);
        JSONCompare.compare(anotherExpectedString, actualMap).assertMatches(); // assertion passes

        // Failed assertion
        String anotherActualString = "{\"a\":2, \"b\":[4, \"lorem\", 5], \"c\":true}";
        JSONCompare.compare(anotherExpectedString, anotherActualString).assertNotMatches(); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(anotherExpectedString, anotherActualString).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.a\\E.*Expected value: 1 But got: 2.*" +
                "\\Q$.b[1]\\E was not found.*"));
    }

    @Test
    void checkJsonInclusion() {
        // Check JSON object inclusion
        String expected = "{\"b\":\"val1\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes

        // JSON objects MUST have same sizes
        String expected1 = "{\"b\":\"val1\"}";
        String actual1 = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertNotMatches(); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON OBJECT has extra fields.*"));
    }

    @Test
    void checkJsonElementsOrder() {
        // JSON Array strict order is by default ignored
        String expected = "[\"lorem\", 2, false]";
        String actual = "[false, 2, \"lorem\", 5, 4]";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes

        // Check JSON Array strict order
        String expected1 = "[\"lorem\", 2, false]";
        String actual1 = "[false, 2, \"lorem\", 5, 4]";
        JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertNotMatches(); // assertion passes
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$[0]\\E.*" +
                "\\Q$[2]\\E.*"));
    }

    @Test
    void matchJsonRegexValues() {
        String expected = "{\"a\": \".*me.*\"}";
        String actual = "{\"a\": \"some text\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonRegexFields() {
        String expected = "{\".*oba.*\": \"some value\"}";
        String actual = "{\"foobar\": \"some value\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonRegexQuote() {
        String expected = "{\"a\":\"\\\\Qd+\\\\E\"}";
        String actual = "{\"a\":\"d+\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonRegexCustomComparator() {
        String expected = "{\"a\": \"\\\\d+\"}";
        String actual = "{\"a\": \"\\\\d+\"}";
        JSONCompare.compare(expected, actual).comparator(new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        }).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonTweaksDoNotMatchValues() {
        String expected = "{\"a\": \"!test\"}";
        String actual = "{\"a\": \"testing\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonTweaksDoNotMatchFields() {
        String expected = "{\"!a\": \"value does not matter\"}";
        String actual = "{\"b\": \"of course value does not matter\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonTweaksNegativeLookaround() {
        String expected = "{\"(?!lorem.*).*\": \"valorem\"}";
        String actual = "{\"ipsum\": \"valorem\"}";
        JSONCompare.compare(expected, actual).assertMatches(); // assertion passes
    }

    @Test
    void matchJsonTweaksDoNotMatchAnyObjectFields() {
        String expected = "{\"b\": \"val1\", \"!.*\": \".*\"}";
        String actual = "{\"a\": \"val2\", \"b\": \"val1\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void matchJsonTweaksDoNotMatchAnyArray() {
        String expected = "[false, \"test\", 4, \"!.*\"]";
        String actual = "[4, false, \"test\", 1]";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void matchJsonTweaksMatchAnyObjectFields() {
        String expected = "{\"b\": \"val1\", \".*\": \".*\"}";
        String actual = "{\"b\": \"val1\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void matchJsonTweaksMatchAnyArray() {
        String expected = "[false, \"test\", 4, \".*\"]";
        String actual = "[4, false, \"test\"]";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void matchJsonTweaksJsonPath() {
        String expected = "{\"#($.store..isbn)\":[\"0-395-19395-8\",\"0-553-21311-3\",\"!.*\"]}";
        String actual = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONCompare.compare(expected, actual).assertMatches();
    }
}
