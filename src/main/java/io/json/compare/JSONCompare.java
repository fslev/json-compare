package io.json.compare;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.util.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Entry point for the json-compare library.
 *
 * <p>Modern usage is via the fluent builder returned by
 * {@link #compare(Object, Object)}:
 * <pre>{@code
 *   JSONCompare.compare(expected, actual)
 *       .modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
 *       .assertMatches();
 *
 *   ComparisonResult result = JSONCompare.compare(expected, actual).result();
 *   result.diffsOfKind(DiffKind.MISSING_FIELD).forEach(System.out::println);
 * }</pre>
 *
 * <p>The previous telescoping static methods are preserved for backward
 * compatibility but are now deprecated in favor of the builder.
 *
 * @author fslev
 */
public final class JSONCompare {

    private JSONCompare() {
    }

    /**
     * Starts a fluent comparison of {@code expected} against {@code actual}.
     * Both arguments may be any JSON-convertible object: {@link JsonNode}, JSON
     * {@link String}, {@link java.util.Map Map}, {@link java.util.List List},
     * or any POJO.
     */
    public static ComparisonBuilder compare(Object expected, Object actual) {
        return new ComparisonBuilder(expected, actual);
    }

    /** Pretty-prints a Jackson node with the library's standard formatting. */
    public static String prettyPrint(JsonNode jsonNode) {
        try {
            return JsonUtils.prettyPrint(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // --------------------------------------------------------------------
    // Deprecated static overloads — kept for API compatibility.
    // Prefer JSONCompare.compare(expected, actual).<builder methods>.
    // --------------------------------------------------------------------

    /** @deprecated Use {@code JSONCompare.compare(expected, actual).assertMatches()}. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual) {
        compare(expected, actual).assertMatches();
    }

    /** @deprecated Use {@code JSONCompare.compare(expected, actual).modes(...).assertMatches()}. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual, Set<CompareMode> compareModes) {
        compare(expected, actual).modes(compareModes).assertMatches();
    }

    /** @deprecated Use {@code JSONCompare.compare(expected, actual).comparator(...).assertMatches()}. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual, JsonComparator comparator) {
        compare(expected, actual).comparator(comparator).assertMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        compare(expected, actual).comparator(comparator).modes(compareModes).assertMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual, Set<CompareMode> compareModes, String message) {
        compare(expected, actual).modes(compareModes).message(message).assertMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes, String message) {
        compare(expected, actual).comparator(comparator).modes(compareModes).message(message).assertMatches();
    }

    /** @deprecated Use {@code JSONCompare.compare(expected, actual).assertNotMatches()}. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual) {
        compare(expected, actual).assertNotMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual, Set<CompareMode> compareModes) {
        compare(expected, actual).modes(compareModes).assertNotMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator) {
        compare(expected, actual).comparator(comparator).assertNotMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        compare(expected, actual).comparator(comparator).modes(compareModes).assertNotMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual, Set<CompareMode> compareModes, String message) {
        compare(expected, actual).modes(compareModes).message(message).assertNotMatches();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static void assertNotMatches(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes, String message) {
        compare(expected, actual).comparator(comparator).modes(compareModes).message(message).assertNotMatches();
    }

    /** @deprecated Use {@code JSONCompare.compare(expected, actual).diffs()} or {@code .result()}. */
    @Deprecated(since = "8.0")
    public static List<String> diffs(Object expected, Object actual) {
        return compare(expected, actual).diffs();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static List<String> diffs(Object expected, Object actual, Set<CompareMode> compareModes) {
        return compare(expected, actual).modes(compareModes).diffs();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static List<String> diffs(Object expected, Object actual, JsonComparator comparator) {
        return compare(expected, actual).comparator(comparator).diffs();
    }

    /** @deprecated Use the {@link #compare(Object, Object)} builder. */
    @Deprecated(since = "8.0")
    public static List<String> diffs(Object expected, Object actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        return compare(expected, actual).comparator(comparator).modes(compareModes).diffs();
    }
}
