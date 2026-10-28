package io.json.compare;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Strategy for deciding whether an expected and actual value (or field name)
 * should be considered a match.
 *
 * <p>The {@linkplain DefaultJsonComparator default implementation} treats the
 * expected side as a Java regular expression (with {@code DOTALL | MULTILINE})
 * and falls back to literal equality when that pattern is invalid or when
 * {@link CompareMode#REGEX_DISABLED} is active.
 *
 * <p>Implementations of this interface should be thread-safe if they will be
 * reused across concurrent comparisons; the library does not share comparator
 * instances across top-level calls by default.
 */
public interface JsonComparator {

    /**
     * @return {@code true} if {@code actual} is considered a match for
     * {@code expected}.
     */
    boolean compareValues(Object expected, Object actual);

    /**
     * @return {@code true} if the actual JSON object field name {@code actual}
     * is considered a match for the expected field name
     * {@code expected}.
     */
    boolean compareFields(String expected, String actual);

    /**
     * Called when the expected value is a string but the actual value is a JSON
     * object or array.
     *
     * @return {@code true} to accept {@code actual} as a match for
     * {@code expected}. Default: no match.
     */
    default boolean compareValueToNode(String expected, JsonNode actual) {
        return false;
    }
}
