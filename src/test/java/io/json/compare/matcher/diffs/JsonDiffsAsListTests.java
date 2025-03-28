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
        assertTrue(diffs.get(0).matches("(?s).*\\Q$[1]\\E was not found.*\"c\".*"));
    }

    @Test
    void compareJsonArraysAndCheckForMultipleElementNotFoundDifferences() {
        String expected = "[\"a\",\"c\",1,200,true,false,12.092,null,{\"lorem\":\"ipsum\"}]";
        String actual = "[12.091,10,\"b\",1,\"a\",2,true,{\"lorem\":\"ipsum-updated\"},\"some text\",false]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$[1]\\E was not found.*\"c\".*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$[3]\\E was not found.*200.*"));
        assertTrue(diffs.get(2).matches("(?s).*\\Q$[6]\\E was not found.*12.092.*"));
        assertTrue(diffs.get(3).matches("(?s).*\\Q$[7]\\E was not found.*null.*"));
        assertTrue(diffs.get(4).matches("(?s).*\\Q$[8]\\E was not found.*\\{.*\"lorem\".*\"ipsum\".*}.*"));
    }

    @Test
    void compareJsonArraysAndCheckForOneMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$[4]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*"));
    }

    @Test
    void compareJsonArraysAndCheckForOneDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"1\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$[2]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndAndJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        List<String> diffs = JSONCompare.diffs(expected, actual,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER)));
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$[0]\\E -> Different JSON types: expected ObjectNode but got TextNode.*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$[1]\\E.*Expected value: \"c\" But got: true.*"));
        assertTrue(diffs.get(2).matches("(?s).*\\Q$[2]\\E was found.*\"!1\".*"));
        assertTrue(diffs.get(3).matches("(?s).*\\Q$[3]\\E -> Different JSON types: expected BooleanNode but got ObjectNode.*"));
        assertTrue(diffs.get(4).matches("(?s).*\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
    }

    @Test
    void compareJsonsWithCUstomComparator() {
        String expected = "{\"name\":\"test1\",\"records\":[3,4]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual, new JsonCustomComparatorDiffTests.CustomComparator());
        assertEquals(2, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$.name\\E.*Expected value: \"test1\" But got: \"test\".*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$.records[1]\\E.* was not found.*4.*"));
    }

    @Test
    void compareJsonObjectsAndCheckForMultipleInDepthFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"x\":51,\"b\":{\"b1\":\"val1\",\"b2\":{\"b21\":\"test\"}}}";
        String actual = "{\"b\":{\"b3\":\"val1\",\"b2\":{\"b22\":10.432}},\"a\":100,\"c\":true}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(3, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$.x\\E was not found.*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$.b.b1\\E was not found.*"));
        assertTrue(diffs.get(2).matches("(?s).*\\Q$.b.b2.b21\\E was not found.*"));
    }

    @Test
    void checkMultipleJsonPathDifferences() {
        String expected = "{\"#($.a.length())\":3,\"b\":\"val1\",\"x\":{\"x1\":{\"y11\":{\"#($.www)\":\"lorem1\"}}},\"z\":[{\"#($.length())\":1}]," +
                "\"u\":{\"#($.u1)\":{\"u11\":20209}}}";
        String actual = "{\"z\":[2,3,4],\"x\":{\"x2\":290.11,\"x1\":{\"x11\":null,\"y11\":{\"a\":\"lorem2\"}}},\"b\":\"val2\",\"a\":[4,5]," +
                "\"u\":{\"u1\":{\"u11\":20000}}}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(5, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$.#($.a.length())\\E.*Expected json path result.*" +
                "3.*But got.*2.*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$.b\\E.*Expected value: \"val1\" But got: \"val2\".*"));
        assertTrue(diffs.get(2).matches("(?s).*\\Q$.x.x1.y11.#($.www)\\E -> Json path -> No results for path.*"));
        assertTrue(diffs.get(3).matches("(?s).*\\Q$.z.#($.length())\\E.*Expected json path result.*1.*But got.*3.*"));
        assertTrue(diffs.get(4).matches("(?s).*\\Q$.u.#($.u1).u11\\E.*Expected value: 20209 But got: 20000" +
                ".*Expected json path result.*u11.*20209.*20000.*"));
    }


    @Test
    void compareJsonsWithCustomComparatorAndCompareModes() {
        String expected = "{\"name\":\".*test\",\"records\":[3,1]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual, new JsonCustomComparatorDiffTests.CustomComparator(),
                new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
        assertEquals(3, diffs.size());
        assertTrue(diffs.get(0).matches("(?s).*\\Q$.name\\E.*Expected value: \".*test\" But got: \"test\".*"));
        assertTrue(diffs.get(1).matches("(?s).*\\Q$.records\\E ->.*Actual JSON ARRAY has extra elements.*"));
        assertTrue(diffs.get(2).matches("(?s).*\\Q$\\E -> Actual JSON OBJECT has extra fields.*"));
    }

    @Test
    void compareJsonsThatMatch() {
        String expected = "{\"name\":\"test\",\"records\":[3,1]}";
        String actual = "{\"name\":\"test\",\"records\":[1,2,3], \"otherRecords\":[4]}";
        List<String> diffs = JSONCompare.diffs(expected, actual);
        assertEquals(0, diffs.size());
    }
}