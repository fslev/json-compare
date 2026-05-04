package io.json.compare;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke-tests every deprecated static overload on {@link JSONCompare} so the
 * legacy API stays exercised while it is still on the classpath.
 */
@SuppressWarnings("deprecation")
public class JSONCompareDeprecatedApiTest {

    private static final String MATCHING_EXPECTED = """
            {
              "a": true
            }
            """;
    private static final String MATCHING_ACTUAL = """
            {
              "a": true,
              "b": false
            }
            """;
    private static final Set<CompareMode> NON_EXTENSIBLE = Set.of(CompareMode.JSON_OBJECT_NON_EXTENSIBLE);
    private static final JsonComparator EQUALS_COMPARATOR = new JsonComparator() {
        @Override
        public boolean compareValues(Object expected, Object actual) {
            return expected.equals(actual);
        }

        @Override
        public boolean compareFields(String expected, String actual) {
            return expected.equals(actual);
        }
    };

    @Test
    public void assertMatchesOverloads() {
        JSONCompare.assertMatches(MATCHING_EXPECTED, MATCHING_ACTUAL);
        JSONCompare.assertMatches(MATCHING_EXPECTED, MATCHING_ACTUAL, EQUALS_COMPARATOR);

        String strictExpected = """
                {
                  "a": true,
                  "b": false
                }
                """;
        JSONCompare.assertMatches(strictExpected, MATCHING_ACTUAL, NON_EXTENSIBLE);
        JSONCompare.assertMatches(strictExpected, MATCHING_ACTUAL, EQUALS_COMPARATOR, NON_EXTENSIBLE);
        JSONCompare.assertMatches(strictExpected, MATCHING_ACTUAL, NON_EXTENSIBLE, "msg");
        JSONCompare.assertMatches(strictExpected, MATCHING_ACTUAL, EQUALS_COMPARATOR, NON_EXTENSIBLE, "msg");
    }

    @Test
    public void assertNotMatchesOverloads() {
        String missing = """
                {
                  "missing": true
                }
                """;
        JSONCompare.assertNotMatches(missing, MATCHING_ACTUAL);
        JSONCompare.assertNotMatches(missing, MATCHING_ACTUAL, EQUALS_COMPARATOR);
        JSONCompare.assertNotMatches(MATCHING_EXPECTED, MATCHING_ACTUAL, NON_EXTENSIBLE);
        JSONCompare.assertNotMatches(MATCHING_EXPECTED, MATCHING_ACTUAL, EQUALS_COMPARATOR, NON_EXTENSIBLE);
        JSONCompare.assertNotMatches(MATCHING_EXPECTED, MATCHING_ACTUAL, NON_EXTENSIBLE, "msg");
        JSONCompare.assertNotMatches(MATCHING_EXPECTED, MATCHING_ACTUAL, EQUALS_COMPARATOR, NON_EXTENSIBLE, "msg");
    }

    @Test
    public void diffsOverloads() {
        List<String> diffs = JSONCompare.diffs(MATCHING_EXPECTED, MATCHING_ACTUAL);
        assertTrue(diffs.isEmpty());

        diffs = JSONCompare.diffs(MATCHING_EXPECTED, MATCHING_ACTUAL, EQUALS_COMPARATOR);
        assertTrue(diffs.isEmpty());

        diffs = JSONCompare.diffs(MATCHING_EXPECTED, MATCHING_ACTUAL, NON_EXTENSIBLE);
        assertTrue(diffs.contains("$ -> Actual JSON OBJECT has extra fields"));

        diffs = JSONCompare.diffs(MATCHING_EXPECTED, MATCHING_ACTUAL, EQUALS_COMPARATOR, NON_EXTENSIBLE);
        assertTrue(diffs.contains("$ -> Actual JSON OBJECT has extra fields"));
    }

    @Test
    public void deprecatedAssertionsThrowOnMismatch() {
        String expected = """
                {
                  "a": false
                }
                """;
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, MATCHING_ACTUAL));
        assertThrows(AssertionError.class, () -> JSONCompare.assertNotMatches(MATCHING_EXPECTED, MATCHING_ACTUAL));
    }
}
