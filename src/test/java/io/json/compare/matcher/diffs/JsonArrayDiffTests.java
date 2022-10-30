package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonArrayDiffTests {

    @Test
    public void compareJsonArraysAndCheckForFor1ElementNotFoundDifference() {
        String expected = "[\"a\",\"c\",1,2,true,false,12.091,null]";
        String actual = "[\"a\",\"b\",1,2,true,false,12.091,null]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*Expected element from position 2 was NOT FOUND.*\"c\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForMultipleElementNotFoundDifferences() {
        String expected = "[\"a\",\"c\",1,200,true,false,12.092,null,{\"lorem\":\"ipsum\"}]";
        String actual = "[12.091,10,\"b\",1,\"a\",2,true,{\"lorem\":\"ipsum-updated\"},\"some text\",false]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "Expected element from position 2 was NOT FOUND.*\"c\".*" +
                "Expected element from position 4 was NOT FOUND.*200.*" +
                "Expected element from position 7 was NOT FOUND.*12.092.*" +
                "Expected element from position 8 was NOT FOUND.*null.*" +
                "Expected element from position 9 was NOT FOUND.*\\{.*\"lorem\".*\"ipsum\".*}.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForOneMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Expected condition \"\\Q.*\\E\" from position 5 was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForMultipleMatchAnyDifferences() {
        String expected = "[\"a\",\".*\",1,\".*\",\".*\",\".*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "Expected condition \"\\Q.*\\E\" from position 5 was not met. Actual JSON ARRAY has no extra elements.*" +
                "Expected condition \"\\Q.*\\E\" from position 6 was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual);

        // empty actual json array
        String expected1 = "[\"a\",\".*\",1,\".*test\",\".*\",\".*\"]";
        String actual1 = "[]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1));
        assertTrue(error1.getMessage().matches("(?s).*FOUND 6 DIFFERENCE.*" +
                "Expected element from position 1 was NOT FOUND.*\"a\".*" +
                "Expected condition \".*\" from position 2 was not met. Actual JSON ARRAY has no extra elements.*" +
                "Expected element from position 3 was NOT FOUND.*1.*" +
                "Expected element from position 4 was NOT FOUND.*\"\\Q.*test\\E\".*" +
                "Expected condition \".*\" from position 5 was not met. Actual JSON ARRAY has no extra elements.*" +
                "Expected condition \".*\" from position 6 was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.assertNotMatches(expected1, actual1);
    }

    @Test
    public void compareJsonArraysAndCheckForOneDoNotMatchDifferences() {
        String expected = "[1,\"!a\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Expected element from position 2 was FOUND.*\"!a\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForMultipleDoNotMatchDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!1\",\"!a\",\"!true\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Expected element from position 2 was FOUND.*\"!1\".*" +
                "Expected element from position 3 was FOUND.*\"!a\".*" +
                "Expected element from position 4 was FOUND.*\"!true\".*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForOneDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"1\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Expected condition \"\\Q!.*\\E\" from position 3 was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForMultipleDoNotMatchAnyDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!.*\",\"1\",\"!.*\",\"!.*\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Expected condition \"\\Q!.*\\E\" from position 2 was not met. Actual JSON ARRAY has extra elements.*" +
                "Expected condition \"\\Q!.*\\E\" from position 4 was not met. Actual JSON ARRAY has extra elements.*" +
                "Expected condition \"\\Q!.*\\E\" from position 5 was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"!.*\",\"1\",{\"lorem2\":\"ipsum2\",\"lorem3\":\"ipsum3\"}]";
        String actual = "[\"a\",true,1,{\"lorem2\":\"ipsum-updated\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "JSON ARRAY elements differ at position 1:.*lorem.*ipsum.*diffs.*Different JSON types: expected ObjectNode but got TextNode.*" +
                "Expected condition \"\\Q!.*\\E\" from position 2 was not met. Actual JSON ARRAY has extra elements.*" +
                "JSON ARRAY elements differ at position 4:.*lorem2.*ipsum2.*lorem3.*ipsum3.*diffs.*" +
                "lorem2 ->.*Expected value: \"ipsum2\" But got: \"ipsum-updated\".*" +
                "Field 'lorem3' was NOT FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysAndCheckForJsonNonExtensibleDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"a\"]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJsonArraysAndCheckForJsonNonExtensibleAndOtherDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"}]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Expected element from position 2 was NOT FOUND.*\"c\".*" +
                "Expected element from position 3 was FOUND.*\"!1\".*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));

        String expected1 = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual1 = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE))));
        assertTrue(error1.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Expected element from position 2 was NOT FOUND.*\"c\".*" +
                "Expected element from position 3 was FOUND.*\"!1\".*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJsonArraysAndCheckForJsonNonExtensibleAndAndJsonStrictOrderDifferences() {
        String expected = "[{\"lorem\":\"ipsum\"},\"c\",\"!1\",true]";
        String actual = "[\"a\",true,1,{\"lorem\":\"ipsum\"},-10.02]";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "JSON ARRAY elements differ at position 1.*\"lorem\".*\"ipsum\".*diffs.*" +
                "Different JSON types: expected ObjectNode but got TextNode.*" +
                "JSON ARRAY elements differ at position 2.*" +
                "\"c\".*diffs.*Expected value: \"c\" But got: true.*" +
                "Expected element from position 3 was FOUND.*\"!1\".*" +
                "JSON ARRAY elements differ at position 4.*true.*diffs.*" +
                "Different JSON types: expected BooleanNode but got ObjectNode.*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.assertNotMatches(expected, actual,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }
}
