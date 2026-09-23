package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonArrayDiffTests {

    @Test
    void compareJsonArraysAndCheckForFor1ElementNotFoundDifference() {
        String expected = """
                [
                  "a",
                  "c",
                  1,
                  2,
                  true,
                  false,
                  12.091,
                  null
                ]
                """;
        String actual = """
                [
                  "a",
                  "b",
                  1,
                  2,
                  true,
                  false,
                  12.091,
                  null
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*\\Q$[1]\\E was not found.*\"c\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleElementNotFoundDifferences() {
        String expected = """
                [
                  "a",
                  "c",
                  1,
                  200,
                  true,
                  false,
                  12.092,
                  null,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        String actual = """
                [
                  12.091,
                  10,
                  "b",
                  1,
                  "a",
                  2,
                  true,
                  {
                    "lorem": "ipsum-updated"
                  },
                  "some text",
                  false
                ]
                """;
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
        String expected = """
                [
                  "a",
                  ".*",
                  1,
                  ".*",
                  ".*"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleMatchAnyDifferences() {
        String expected = """
                [
                  "a",
                  ".*",
                  1,
                  ".*",
                  ".*",
                  ".*"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*" +
                "\\Q$[5]\\E -> Expected condition \"\\Q.*\\E\" was not met. Actual JSON ARRAY has no extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();

        // empty actual json array
        String expected1 = """
                [
                  "a",
                  ".*",
                  1,
                  ".*test",
                  ".*",
                  ".*"
                ]
                """;
        String actual1 = """
                []
                """;
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
        String expected = """
                [
                  1,
                  "!a",
                  true
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[1]\\E was found.*\"!a\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleDoNotMatchDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "!1",
                  "!a",
                  "!true"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was found.*\"!1\".*" +
                "\\Q$[2]\\E was found.*\"!a\".*" +
                "\\Q$[3]\\E was found.*\"!true\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForOneDoNotMatchAnyDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "1",
                  "!.*"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "\\Q$[2]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForMultipleDoNotMatchAnyDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "!.*",
                  "1",
                  "!.*",
                  "!.*"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$[3]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*" +
                "\\Q$[4]\\E -> Expected condition \"\\Q!.*\\E\" was not met. Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonStrictOrderDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "!.*",
                  "1",
                  {
                    "lorem2": "ipsum2",
                    "lorem3": "ipsum3"
                  }
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem2": "ipsum-updated"
                  }
                ]
                """;
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
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "a"
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndOtherDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "c",
                  "!1",
                  true
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  }
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was not found.*\"c\".*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();

        String expected1 = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "c",
                  "!1",
                  true
                ]
                """;
        String actual1 = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  },
                  -10.02
                ]
                """;
        AssertionError error1 = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertMatches());
        assertTrue(error1.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q$[1]\\E was not found.*\"c\".*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected1, actual1).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForJsonNonExtensibleAndAndJsonStrictOrderDifferences() {
        String expected = """
                [
                  {
                    "lorem": "ipsum"
                  },
                  "c",
                  "!1",
                  true
                ]
                """;
        String actual = """
                [
                  "a",
                  true,
                  1,
                  {
                    "lorem": "ipsum"
                  },
                  -10.02
                ]
                """;
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 5 DIFFERENCE.*" +
                "\\Q$[0]\\E -> Different JSON types: expected ObjectNode but got TextNode.*" +
                "\\Q$[1]\\E.*Expected value: \"c\" But got: true.*" +
                "\\Q$[2]\\E was found.*\"!1\".*" +
                "\\Q$[3]\\E.*Different JSON types: expected BooleanNode but got ObjectNode.*" +
                "\\Q$\\E -> Actual JSON ARRAY has extra elements.*"));
        JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_STRICT_ORDER).assertNotMatches();
    }

    @Test
    void notFoundObjectShowsClosestUnmatchedElementAndItsDifferences() {
        String expected = """
                [
                  { "id": 3, "name": "Carol", "role": "admin", "active": true }
                ]
                """;
        String actual = """
                [
                  { "id": 1, "name": "Alice", "role": "dev", "active": true },
                  { "id": 2, "name": "Bob", "role": "dev", "active": false },
                  { "id": 3, "name": "Carol", "role": "dev", "active": true },
                  { "id": 4, "name": "Dave", "role": "admin", "active": false }
                ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s)\\Q$[0]\\E was not found:.*}" +
                "\\RClosest unmatched actual element \\[2] differs by:" +
                "\\R  - \\Q.role\\E" +
                "\\R    Expected value: \"admin\" But got: \"dev\""), diffs.get(0));
    }

    @Test
    void notFoundNestedObjectShowsClosestElementAtEachLevel() {
        String expected = """
                [
                  { "order": 7, "items": [ { "sku": "A-1", "qty": 2 } ] }
                ]
                """;
        String actual = """
                [
                  { "order": 5, "items": [ { "sku": "C-3", "qty": 9 } ] },
                  { "order": 7, "items": [ { "sku": "A-1", "qty": 3 }, { "sku": "B-2", "qty": 1 } ] }
                ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s)\\Q$[0]\\E was not found:.*}" +
                "\\RClosest unmatched actual element \\[1] differs by:" +
                "\\R  - \\Q.items[0]\\E was not found:" +
                "\\R    \\{.*}" +
                "\\R    Closest unmatched actual element \\[0] differs by:" +
                "\\R      - \\Q.qty\\E" +
                "\\R        Expected value: 2 But got: 3"), diffs.get(0));
    }

    @Test
    void notFoundScalarHasNoClosestElementHint() {
        String expected = """
                [ "a", "c" ]
                """;
        String actual = """
                [ "a", "b", "d" ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("\\Q$[1]\\E was not found:\\R\"c\""), diffs.get(0));
    }

    @Test
    void notFoundObjectIgnoresCandidatesOfOtherTypes() {
        String expected = """
                [ "x", { "id": 1 } ]
                """;
        String actual = """
                [ "x", "id", 1, [ 1 ] ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("\\Q$[1]\\E was not found:\\R\\{\\R  \"id\" : 1\\R}"), diffs.get(0));
    }

    @Test
    void notFoundObjectPrefersLowestIndexWhenCandidatesAreEquallyClose() {
        String expected = """
                [ { "a": 1, "b": 2 } ]
                """;
        String actual = """
                [ { "a": 1, "b": 3 }, { "a": 0, "b": 2 } ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s)\\Q$[0]\\E was not found:.*}" +
                "\\RClosest unmatched actual element \\[0] differs by:" +
                "\\R  - \\Q.b\\E" +
                "\\R    Expected value: 2 But got: 3"), diffs.get(0));
    }

    @Test
    void notFoundObjectAlsoShowsClosestElementMatchedByAnotherExpectedElement() {
        String expected = """
                [
                  { "status": "INACTIVE" },
                  { "id": ".*" },
                  { "id": 7, "status": "ACTIVE" }
                ]
                """;
        String actual = """
                [
                  { "id": 9, "status": "CLOSED" },
                  { "id": 7, "status": "INACTIVE" },
                  { "id": 8, "status": "PENDING" }
                ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s)\\Q$[2]\\E was not found:.*}" +
                "\\RClosest unmatched actual element \\[2] differs by:" +
                "\\R  - \\Q.id\\E" +
                "\\R    Expected value: 7 But got: 8" +
                "\\R  - \\Q.status\\E" +
                "\\R    Expected value: \"ACTIVE\" But got: \"PENDING\"" +
                "\\RClosest matched actual element \\[1] \\(matched by expected \\[0]\\) differs by:" +
                "\\R  - \\Q.status\\E" +
                "\\R    Expected value: \"ACTIVE\" But got: \"INACTIVE\""), diffs.get(0));
    }

    @Test
    void notFoundObjectShowsElementAlreadyMatchedByLooserExpectedElementWouldMatch() {
        String expected = """
                [ { "id": ".*" }, { "id": 7 } ]
                """;
        String actual = """
                [ { "id": 7 }, { "id": 8 } ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("(?s)\\Q$[1]\\E was not found:.*}" +
                "\\RClosest unmatched actual element \\[1] differs by:" +
                "\\R  - \\Q.id\\E" +
                "\\R    Expected value: 7 But got: 8" +
                "\\RClosest matched actual element \\[0] \\(matched by expected \\[0]\\) would match"), diffs.get(0));
    }

    @Test
    void notFoundObjectInStrictOrderHasNoClosestMatchedElementHint() {
        String expected = """
                [ { "a": 1 }, { "a": 1 } ]
                """;
        String actual = """
                [ { "a": 1 } ]
                """;
        List<String> diffs = JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).diffs();
        assertEquals(1, diffs.size());
        assertTrue(diffs.get(0).matches("\\Q$[1]\\E was not found:\\R\\{\\R  \"a\" : 1\\R}"), diffs.get(0));
    }
}
