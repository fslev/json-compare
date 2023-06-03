package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonDiffsAsListTests {

    @Test
    void compareJsonArraysAndCheckForFor1ElementNotFoundDifference() {
        String expected = "[\"a\",\"c\",1,2,true,false,12.091,null]";
        String actual = "[\"a\",\"b\",1,2,true,false,12.091,null]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*Expected element from position 2 was NOT FOUND.*\"c\".*"));
    }

    @Test
    void compareJsonArraysAndCheckForMultipleElementNotFoundDifferences() {
        String expected = "[\"a\",\"c\",1,200,true,false,12.092,null,{\"lorem\":\"ipsum\"}]";
        String actual = "[12.091,10,\"b\",1,\"a\",2,true,{\"lorem\":\"ipsum-updated\"},\"some text\",false]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*Expected element from position 2 was NOT FOUND.*\"c\".*"));
        assertTrue(diffs.get(1).matches("(?s).*Expected element from position 4 was NOT FOUND.*200.*"));
        assertTrue(diffs.get(2).matches("(?s).*Expected element from position 7 was NOT FOUND.*12.092.*"));
        assertTrue(diffs.get(3).matches("(?s).*Expected element from position 8 was NOT FOUND.*null.*"));
        assertTrue(diffs.get(4).matches("(?s).*Expected element from position 9 was NOT FOUND.*\\{.*\"lorem\".*\"ipsum\".*}.*"));
    }

    @Test
    void compareJsonArraysAndCheckForOneMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*Expected condition \"\\Q.*\\E\" from position 5 was not met. Actual JSON ARRAY has no extra elements.*"));
    }

    @Test
    void compareJsonArraysAndCheckForOneDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"1\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*Expected condition \"\\Q!.*\\E\" from position 3 was not met. Actual JSON ARRAY has extra elements.*"));
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndAndJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        List<String> diffs = JSONCompare.diffs(expected, actual,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER)));
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*JSON ARRAY elements differ at position 1.*\"lorem\".*\"ipsum\".*diffs.*" +
                "Different JSON types: expected ObjectNode but got TextNode.*"));
        assertTrue(diffs.get(1).matches("(?s).*JSON ARRAY elements differ at position 2.*\"c\".*diffs.*Expected value: \"c\" But got: true.*"));
        assertTrue(diffs.get(2).matches("(?s).*Expected element from position 3 was FOUND.*\"!1\".*"));
        assertTrue(diffs.get(3).matches("(?s).*JSON ARRAY elements differ at position 4.*true.*diffs.*" +
                "Different JSON types: expected BooleanNode but got ObjectNode.*"));
        assertTrue(diffs.get(4).matches("(?s).*Actual JSON ARRAY has extra elements.*"));
    }

    @Test
    void compareJsonsWithCUstomComparator() {
        String expected = "{\"name\":\"test1\",\"records\":[3,4]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual, new JsonCustomComparatorDiffTests.CustomComparator());
        assertEquals(2, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*name ->.*Expected value: \"test1\" But got: \"test\".*"));
        assertTrue(diffs.get(1).matches("(?s).*records ->.*Expected element from position 2 was NOT FOUND.*4.*"));
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleInDepthFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"x\":51,\"b\":{\"b1\":\"val1\",\"b2\":{\"b21\":\"test\"}}}";
        String actual = "{\"b\":{\"b3\":\"val1\",\"b2\":{\"b22\":10.432}},\"a\":100,\"c\":true}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(3, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*Field 'x' was NOT FOUND.*"));
        assertTrue(diffs.get(1).matches("(?s).*b -> Field 'b1' was NOT FOUND.*"));
        assertTrue(diffs.get(2).matches("(?s).*b -> b2 -> Field 'b21' was NOT FOUND.*"));
    }

    @Test
    void checkMultipleJsonPathDifferences() {
        String expected = "{\"#($.a.length())\":3,\"b\":\"val1\",\"x\":{\"x1\":{\"y11\":{\"#($.www)\":\"lorem1\"}}},\"z\":[{\"#($.length())\":1}]," +
                "\"u\":{\"#($.u1)\":{\"u11\":20209}}}";
        String actual = "{\"z\":[2,3,4],\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]," +
                "\"u\":{\"u1\":{\"u11\":20000}}}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\QJson path '$.a.length()' -> Expected json path result\\E.*" +
                "3.*But got.*2.*"));
        assertTrue(diffs.get(1).matches("(?s).*Expected value: \"val1\" But got: \"val2\".*"));
        assertTrue(diffs.get(2).matches("(?s).*x -> x1 -> y11 -> \\QJson path '$.www'\\E -> No results for path.*"));
        assertTrue(diffs.get(3).matches("(?s).*z -> \\QJson path '$.length()' -> Expected json path result\\E.*" +
                "1.*But got.*3.*"));
        assertTrue(diffs.get(4).matches("(?s).*u -> \\QJson path '$.u1' -> Expected json path result\\E.*" +
                "u11 ->.*Expected value: 20209 But got: 20000.*"));
    }


    @Test
    void compareJsonsWithCustomComparatorAndCompareModes() {
        String expected = "{\"name\":\".*test\",\"records\":[3,1]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual, new JsonCustomComparatorDiffTests.CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
        assertEquals(3, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*name ->.*Expected value: \".*test\" But got: \"test\".*"));
        assertTrue(diffs.get(1).matches("(?s).*records ->.*Actual JSON ARRAY has extra elements.*"));
        assertTrue(diffs.get(2).matches("(?s).*Actual JSON OBJECT has extra fields.*"));
    }

    @Test
    void compareJsonsThatMatch() {
        String expected = "{\"name\":\"test\",\"records\":[3,1]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(0, diffs.size());
    }
}