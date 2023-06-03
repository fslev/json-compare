package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonCustomComparatorDiffTests {

    @Test
    public void compareJsons() {
        String expected = "{\"name\":\"test\",\"records\":[3]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator());

        String expected1 = "{\"name\":\"test1\",\"records\":[3,4]}";
        String actual1 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1, new CustomComparator()));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "name ->.*Expected value: \"test1\" But got: \"test\".*" +
                "records ->.*Expected element from position 2 was NOT FOUND.*4.*"));
    }

    @Test
    public void compareJsonsWithCompareModes() {
        String expected = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                        CompareMode.JSON_ARRAY_STRICT_ORDER)));

        String expected1 = "{\"name\":\"test\",\"records\":[2,1]}";
        String actual1 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1, new CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                        CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "records -> JSON ARRAY elements differ at position 1.*2.*Expected value: 2 But got: 1.*" +
                "records -> JSON ARRAY elements differ at position 2.*1.*Expected value: 1 But got: 2.*" +
                "records -> Actual JSON ARRAY has extra elements.*" +
                "Actual JSON OBJECT has extra fields.*"));

        String expected2 = "{\"name\":\"test\",\"records\":[1,2]}";
        String actual2 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected2, actual2, new CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                        CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "records -> Actual JSON ARRAY has extra elements.*" +
                "Actual JSON OBJECT has extra fields.*"));

        String expected3 = "{\"name\":\"test\",\"records\":[1,2,3]}";
        String actual3 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected3, actual3, new CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                        CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON OBJECT has extra fields.*"));
    }

    @Test
    public void compareJsonsWithUseCases() {
        String expected = "{\"!name\":\"test\",\"records\":[1,2,3, \"!.*\"], \"otherRecords\":[4, \"!.*\"]}";
        String actual = "{\"names\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator());

        expected = "{\"!name\":\"test\", \"records\":[1,2,3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        actual = "{\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator());

        expected = "{\".*\":\"test\",\"records\":[1, \".*\", 3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        actual = "{\"names\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator());

        String expected1 = "{\".*\":\"test\", \"records\":[1, \".*\", 3, \"!.*\"], \"otherRecords\":[4, \"!.*\"], \"!.*\":\".*\"}";
        String actual1 = "{\"names\":\"test1\", \"records\":[2,1,4,3], \"otherRecords\":[1,2], \"another\":\"record\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1, new CustomComparator()));
        assertTrue(error.getMessage().matches("(?s).*FOUND 8 DIFFERENCE.*" +
                "\\Q.*\\E ->.*Expected value: \"test\" But got: \"test1\".*" +
                "\\Q.*\\E ->.*Different JSON types: expected TextNode but got ArrayNode.*" +
                "\\Q.*\\E ->.*Different JSON types: expected TextNode but got ArrayNode.*" +
                "\\Q.*\\E ->.*Expected value: \"test\" But got: \"record\".*" +
                "records ->.*Expected condition \"\\Q!.*\\E\" from position 4 was not met. Actual JSON ARRAY has extra elements.*" +
                "otherRecords ->.*Expected element from position 1 was NOT FOUND.*4.*" +
                "otherRecords ->.*Expected condition \"\\Q!.*\\E\" from position 2 was not met. Actual JSON ARRAY has extra elements.*" +
                "Expected condition '\\Q!.*\\E' was not met. Actual JSON OBJECT has extra fields.*"));

        String expected2 = "{\"name\":\"test\", \"records\":[1, \".*\", 3, 4, \".*\"], \"otherRecords\":[4, \"!.*\"], \".*\":\".*\"}";
        String actual2 = "{\"names\":\"test1\", \"records\":[2,1,4,3], \"otherRecords\":[1,2, 4], \"another\":\"record\"}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected2, actual2, new CustomComparator()));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Field 'name' was NOT FOUND.*" +
                "records ->.*Expected condition \"\\Q.*\\E\" from position 5 was not met. Actual JSON ARRAY has no extra elements.*" +
                "otherRecords ->.*Expected condition \"\\Q!.*\\E\" from position 2 was not met. Actual JSON ARRAY has extra elements.*"));

        String expected3 = "{\"name\":\"test\", \"records\":[1, \".*\", 3, 4, \".*\"], \"otherRecords\":[4, 2, \"!.*\"], \".*\":\".*\"}";
        String actual3 = "{\"name\":\"test\", \"records\":[2,1,5,4,3], \"otherRecords\":[2, 4]}";
        error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected3, actual3, new CustomComparator()));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Field '\\Q.*\\E' was NOT FOUND.*"));

    }

    @Test
    public void compareJsonsWithRegexes() {
        String expected = "{\"na.*\":\"test\",\"records\":[1,\"\\\\d+\",3], \"otherRecords\":[4]}";
        String actual = "{\"na.*\":\"test\",\"records\":[\"\\\\d+\",3,1], \"otherRecords\":[4]}";
        JSONCompare.assertMatches(expected, actual, new CustomComparator());

        String expected1 = "{\"na.*\":\"test\",\"records\":[2,\"\\\\d+\"]}";
        String actual1 = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1, new CustomComparator()));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "Field '\\Qna.*\\E' was NOT FOUND.*" +
                "records ->.*Expected element from position 2 was NOT FOUND.*\"\\Q\\\\d+\\E\".*"));
    }

    public static class CustomComparator implements JsonComparator {
        @Override
        public boolean compareValues(Object expected, Object actual) {
            return expected.equals(actual);
        }

        @Override
        public boolean compareFields(String expected, String actual) {
            return expected.equals(actual);
        }
    }
}
