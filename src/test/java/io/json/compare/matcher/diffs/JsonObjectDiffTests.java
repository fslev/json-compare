package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonObjectDiffTests {

    @Test
    void compareJsonObjectsAndCheckForSingleFieldNotFoundDifference() {
        String expected = "{\"a\":100,\"e\":\"lorem100\"}";
        String actual = "{\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.e\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"e\":\"lorem100\",\"x\":true}";
        String actual = "{\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.e\\E was not found.*" +
                "\\Q$.x\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleInDepthFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"x\":51,\"b\":{\"b1\":\"val1\",\"b2\":{\"b21\":\"test\"}}}";
        String actual = "{\"b\":{\"b3\":\"val1\",\"b2\":{\"b22\":10.432}},\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$.x\\E was not found.*" +
                "\\Q$.b.b1\\E was not found.*" +
                "\\Q$.b.b2.b21\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForSingleDoNotMatchFieldDifference() {
        String expected = "{\"c\":true,\"!a\":100}";
        String actual = "{\"a\":200,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.\"!a\"\\E was found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleDoNotMatchFieldDifferences() {
        String expected = "{\"c\":true,\"!a\":100,\"!c\":200,\"!x\":200}";
        String actual = "{\"a\":200,\"c\":true,\"x\":\"lorem\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.\"!a\"\\E was found.*" +
                "\\Q$.\"!x\"\\E was found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForInDepthDoNotMatchFieldDifferences() {
        String expected = "{\"!c\":true,\"a\":200,\"x\":{\"!x1\":\"lorem\",\"x2\":{\"!x21\":\"test\"}}}";
        String actual = "{\"c\":true,\"a\":200,\"x\":{\"x1\":\"lorem\",\"x2\":{\"x23\":\"pat\",\"x21\":\"lol\"}}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$.\"!c\"\\E was found.*" +
                "\\Q$.x.\"!x1\"\\E was found.*" +
                "\\Q$.x.x2.\"!x21\"\\E was found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleCandidatesDifferences() {
        String expected = "{\".*\":{\"a\":1,\"x\":2}}";
        String actual = "{\"test1\":{\"a\":10,\"b\":22},\"test2\":{\"a\":20,\"x\":2}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$..*.a\\E.*Expected value: 1 But got: 10.*" +
                "\\Q$..*.x\\E was not found.*" +
                "\\Q$..*.a\\E.*Expected value: 1 But got: 20.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\".*\":{\"a\":1,\"x\":2}}";
        String actual1 = "{\"test1\":{\"a\":10,\"b\":22},\"test2\":{\"a\":1,\"x\":2}}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    void compareJsonObjectsAndCheckForDoNotMatchDifferences() {
        String expected = "{\"b\":2,\"a\":1,\"c\":{\"c2\":\"lorem2\",\"!.*\":\".*\"},\"!.*\":\".*\"}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c1\":\"lorem1\",\"c2\":\"lorem2\"},\"d\":1}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.c.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\"b\":2,\"a\":1,\"c\":{\"c2\":\"lorem2\",\"!.*\":\".*\"},\"!.*\":\".*\"}";
        String actual1 = "{\"a\":1,\"b\":2,\"c\":{\"c2\":\"lorem2\"}}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    void compareJsonObjectsAndCheckForMatchAnyDifferences() {
        String expected = "{\"b\":2,\"a\":1,\"c\":{\"c1\":\"lorem2\",\".*\":\".*\"},\"!.*\":\".*\"}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c1\":\"lorem2\"}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.c..*\\E was not found.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMatchAnyDifferences1() {
        String expected = "{\"a\":1,\"c\":{\"c1\":\"lorem2\",\".*\":\".*\"},\"!.*\":\".*\"}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c1\":\"lorem2\"}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.c..*\\E was not found.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForNonExtensibleObjectDifferences() {
        String expected = "{\"b\":2,\"a\":0,\"c\":{\"c1\":\"lorem1\"}}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c2\":\"lorem2\",\"c1\":\"lorem1\"},\"x\":0}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$.a\\E.*Expected value: 0 But got: 1.*" +
                "\\Q$.c\\E -> Actual JSON OBJECT has extra fields.*" +
                "Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForDoNotMatchAnyDifferencesFromArrayObject() {
        String expected = "{\"b\":2,\"a\":0,\"c\":{\"c1\":\"lorem1\"},\"u\":[3,1,\"!.*\"]}";
        String actual = "{\"a\":1, \"u\":[3,1,false], \"b\":2,\"c\":{\"c2\":\"lorem2\",\"c1\":\"lorem1\"},\"x\":0}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$.a\\E.*Expected value: 0 But got: 1.*" +
                "\\Q$.c\\E -> Actual JSON OBJECT has extra fields.*" +
                "\\Q$.u[2]\\E -> Expected condition \"!.*\" was not met. Actual JSON ARRAY has extra elements.*" +
                "Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\"b\":2,\"a\":0,\"c\":{\"c1\":\"lorem1\", \"c2\":\"incorrect\"},\"u\":[3,1,\"!.*\"]}";
        String actual1 = "{\"a\":1, \"u\":[3,1,false], \"b\":2,\"c\":{\"c2\":\"lorem2\",\"c1\":\"lorem1\"},\"x\":0}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$.a\\E.*Expected value: 0 But got: 1.*" +
                "\\Q$.c.c2\\E.*Expected value: \"incorrect\" But got: \"lorem2\".*" +
                "\\Q$.u[2]\\E.*Expected condition \\Q\"!.*\"\\E was not met. Actual JSON ARRAY has extra elements.*" +
                "Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsAndCheckForMismatchDifferences() {
        String expected = "{\n" +
                "  \"branch\": -212276217.0580678,\n" +
                "  \"wagon\": {\n" +
                "    \"being\": true,\n" +
                "    \"position\": {\n" +
                "      \"interior1\": \"balance\",\n" +
                "      \"writer\": -806595724.6570452,\n" +
                "      \"area\": true,\n" +
                "      \"difficulty\": \"not equal\"\n" +
                "    },\n" +
                "    \"ill\": -2976723441,\n" +
                "    \"particular\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        894216220\n" +
                "      ],\n" +
                "      [\n" +
                "        -1644147917.075238,\n" +
                "        \"recent\",\n" +
                "        [\n" +
                "          false,\n" +
                "          111947014.54409099,\n" +
                "          {\n" +
                "            \"monkey\": -172346568.4752009,\n" +
                "            \"construction\": \"affect\"\n" +
                "          },\n" +
                "          true\n" +
                "        ]\n" +
                "      ],\n" +
                "      222085833,\n" +
                "      false\n" +
                "    ]" +
                "  },\n" +
                "  \"duty\": false,\n" +
                "  \"master\": -1005804822.0610056\n" +
                "}";
        String actual = "{\n" +
                "  \"branch\": -212276217.0580678,\n" +
                "  \"wagon\": {\n" +
                "    \"being\": false,\n" +
                "    \"arrange\": -2082234921.4886482,\n" +
                "    \"position\": {\n" +
                "      \"interior\": \"balance\",\n" +
                "      \"writer\": -806595724.6570451,\n" +
                "      \"area\": true,\n" +
                "      \"difficulty\": \"equal\"\n" +
                "    },\n" +
                "    \"ill\": -297672344,\n" +
                "    \"particular\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        894216228\n" +
                "      ],\n" +
                "      [\n" +
                "        -1644147917.075238,\n" +
                "        \"recent\",\n" +
                "        [\n" +
                "          false,\n" +
                "          111947014.54409099,\n" +
                "          {\n" +
                "            \"monkey\": -172346568.4752009,\n" +
                "            \"construction\": \"affect\"\n" +
                "          },\n" +
                "          true\n" +
                "        ]\n" +
                "      ],\n" +
                "      222085833,\n" +
                "      false\n" +
                "    ]" +
                "  },\n" +
                "  \"duty\": false,\n" +
                "  \"master\": 1005804822.0610056\n" +
                "}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual, null, "My custom message"));
        assertTrue(error.getMessage().matches("(?s).*FOUND 7 DIFFERENCE.*" +
                "\\Q$.wagon.being\\E.*Expected value: true But got: false.*" +
                "\\Q$.wagon.position.interior1\\E was not found.*" +
                "\\Q$.wagon.position.writer\\E.*Expected value: -806595724.6570452 But got: -806595724.6570451.*" +
                "\\Q$.wagon.position.difficulty\\E.*Expected value: \"not equal\" But got: \"equal\".*" +
                "\\Q$.wagon.ill\\E.*Expected value: -2976723441 But got: -297672344.*" +
                "\\Q$.wagon.particular[0]\\E was not found.*" +
                "true.*894216220.*" +
                "\\Q$.master\\E.*Expected value: -1005804822.0610056 But got: 1005804822.0610056.*" +
                "My custom message.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    void compareJsonObjectsWithJsonArraysAndDoNotMatchAny() {
        String expected = "{\"name\":\"test\",\".*\":[4],\"!.*\":\".*\"}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));

        String expected0 = "{\"records\":[3], \"!name\":\"test\",\".*\":[4],\"!.*\":\".*\"}";
        String actual0 = "{\"names\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected0, actual0));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));

        String expected1 = "{\"name\":\"test\",\".*\":[4],\"records\":[3],\"!.*\":\".*\"}";
        String actual1 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    void compareJsonsWithUseCases() {
        String expected = "{\"!name\":\"test\",\"records\":[1,2,3, \"!.*\"], \"otherRecords\":[4, \"!.*\"]}";
        String actual = "{\"names\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual);

        expected = "{\"!name\":\"test\", \"records\":[1,2,3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        actual = "{\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual);

        expected = "{\".*\":\"test\",\"records\":[1, \".*\", 3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        actual = "{\"names\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual);

        String expected1 = "{\".*\":\"test\", \"records\":[1, \".*\", 3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        String actual1 = "{\"names\":\"test1\", \"records\":[2,1,4,3], \"otherRecords\":[1,2], \"another\":\"record\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1));
        assertTrue(error.getMessage().matches("(?s).*FOUND 8 DIFFERENCE.*" +
                "\\Q$..*\\E.*Expected value: \"test\" But got: \"test1\".*" +
                "\\Q$..*\\E.*Different JSON types: expected TextNode but got ArrayNode.*" +
                "\\Q$..*\\E.*Different JSON types: expected TextNode but got ArrayNode.*" +
                "\\Q$..*\\E.*Expected value: \"test\" But got: \"record\".*" +
                "\\Q$.records[3]\\E ->.*Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$.otherRecords[0]\\E was not found.*4.*" +
                "\\Q$.otherRecords[1]\\E ->.*Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));

        String expected2 = "{\"name\":\"test\", \"records\":[1, \".*\", 3, 4, \".*\"], \"otherRecords\":[4, \"!.*\"], \".*\":\".*\"}";
        String actual2 = "{\"names\":\"test1\", \"records\":[2,1,4,3], \"otherRecords\":[1,2, 4], \"another\":\"record\"}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected2, actual2));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$.name\\E was not found.*" +
                "\\Q$.records[4]\\E ->.*Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*" +
                "\\Q$.otherRecords[1]\\E ->.*Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));

        String expected3 = "{\"name\":\"test\", \"records\":[1, \".*\", 3, 4, \".*\"], \"otherRecords\":[4, 2, \"!.*\"], \".*\":\".*\"}";
        String actual3 = "{\"name\":\"test\", \"records\":[2,1,5,4,3], \"otherRecords\":[2, 4]}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected3, actual3));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$..*\\E was not found.*"));
    }

    @Test
    void compareJsonsWithNestedUseCases() {
        String expected = "{\"name\":\"test\", \"x\":{\"b\":2, \"!.*\":\".*\"}, \"!.*\":\".*\"}";
        String actual = "{\"names\":\"test\", \"x\":{\"a\":1, \"b\":2}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$.name\\E was not found.*" +
                "\\Q$.x.\"!.*\"\\E condition was not met. Actual JSON OBJECT has extra fields.*"));
    }
}
