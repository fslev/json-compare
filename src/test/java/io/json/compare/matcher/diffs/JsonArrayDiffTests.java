package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonArrayDiffTests {

    @Test
    void compareJsonArraysAndCheckForFor1ElementNotFoundDifference() {
        String expected = "[\"a\",\"c\",1,2,true,false,12.091,null]";
        String actual = "[\"a\",\"b\",1,2,true,false,12.091,null]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*\\Q$[1]\\E was not found.*\"c\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleElementNotFoundDifferences() {
        String expected = "[\"a\",\"c\",1,200,true,false,12.092,null,{\"lorem\":\"ipsum\"}]";
        String actual = "[12.091,10,\"b\",1,\"a\",2,true,{\"lorem\":\"ipsum-updated\"},\"some text\",false]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "\\Q$[1]\\E was not found.*\"c\".*" +
                "\\Q$[3]\\E was not found.*200.*" +
                "\\Q$[6]\\E was not found.*12.092.*" +
                "\\Q$[7]\\E was not found.*null.*" +
                "\\Q$[8]\\E was not found.*\\{.*\"lorem\".*\"ipsum\".*}.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForOneMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*" +
                "\\Q$[5]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();

        // empty actual json array
        String expected1 = "[\"a\",\".*\",1,\".*test\",\".*\",\".*\"]";
        String actual1 = "[]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected1, actual1).assertMatches());
        assertTrue(error1.getMessage().matches("(?s).*FOUND 6 DIFFERENCE.*" +
                "\\Q$[0]\\E was not found.*\"a\".*" +
                "\\Q$[1]\\E -> Expected condition \".*\" was not met. Actual JSON ARRAY has no extra elements.*" +
                "\\Q$[2]\\E was not found.*1.*" +
                "\\Q$[3]\\E was not found.*\"\\Q.*test\\E\".*" +
                "\\Q$[4]\\E -> Expected condition \".*\" was not met. Actual JSON ARRAY has no extra elements.*" +
                "\\Q$[5]\\E -> Expected condition \".*\" was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.compare(expected1, actual1).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForOneDoNotMatchDifferences() {
        String expected = "[1,\"!a\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[1]\\E was found.*\"!a\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleDoNotMatchDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!1\",\"!a\",\"!true\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was found.*\"!1\".*" +
                "\\Q$[2]\\E was found.*\"!a\".*" +
                "\\Q$[3]\\E was found.*\"!true\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForOneDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"1\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[2]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!.*\",\"1\",\"!.*\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$[3]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!.*\",\"1\",{\"lorem2\":\"ipsum2\",\"lorem3\":\"ipsum3\"}]";
        String actual = "[\"a\",true,1,{\"lorem2\":\"ipsum-updated\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$[0]\\E -> Different JSON types: expected ObjectNode but got TextNode.*" +
                "\\Q$[1]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$[3].lorem2\\E.*Expected value: \"ipsum2\" But got: \"ipsum-updated\".*" +
                "\\Q$[3].lorem3\\E was not found.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"a\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndOtherDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was not found.*\"c\".*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();

        String expected1 = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual1 = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error1.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was not found.*\"c\".*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndAndJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "\\Q$[0]\\E -> Different JSON types: expected ObjectNode but got TextNode.*" +
                "\\Q$[1]\\E.*Expected value: \"c\" But got: true.*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$[3]\\E.*Different JSON types: expected BooleanNode but got ObjectNode.*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER).assertNotMatches();
    }
}
