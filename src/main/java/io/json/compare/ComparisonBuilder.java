package io.json.compare;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.matcher.JsonMatcher;
import io.json.compare.result.ComparisonResult;
import io.json.compare.util.JsonUtils;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Fluent entry point to the json-compare engine. Obtained via
 * {@link JSONCompare#compare(Object, Object)}.
 *
 * <p>Configuration methods return {@code this} for chaining; terminal methods
 * ({@link #assertMatches()}, {@link #assertNotMatches()}, {@link #diffs()},
 * {@link #result()}) trigger the actual comparison.
 *
 * <p>Example:
 * <pre>{@code
 *   JSONCompare.compare(expected, actual)
 *       .modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
 *       .message("users endpoint")
 *       .assertMatches();
 * }</pre>
 */
public final class ComparisonBuilder {

    private static final String LS = System.lineSeparator();
    private static final String DIFF_DIVIDER = "_________________________DIFF__________________________";
    private static final String ASSERTION_ERROR_HINT_MESSAGE =
            "Json matching is by default case-sensitive and uses regular expressions." + LS
                    + "In case expected json contains any unintentional regexes, then quote them between \\Q and \\E delimiters.\n"
                    + "For disabling case-sensitivity, use (?i) and (?-i) modifiers. Or, use a custom comparator.";

    private final Object expected;
    private final Object actual;
    private JsonComparator comparator;
    private Set<CompareMode> modes;
    private String message;

    ComparisonBuilder(Object expected, Object actual) {
        this.expected = expected;
        this.actual = actual;
    }

    /** Sets a custom comparator (defaults to {@link DefaultJsonComparator}). */
    public ComparisonBuilder comparator(JsonComparator comparator) {
        this.comparator = comparator;
        return this;
    }

    /** Sets compare modes (replaces any previously set modes). */
    public ComparisonBuilder modes(Set<CompareMode> modes) {
        this.modes = modes == null ? null : Collections.unmodifiableSet(new LinkedHashSet<>(modes));
        return this;
    }

    /** Sets compare modes (replaces any previously set modes). Null-safe. */
    public ComparisonBuilder modes(CompareMode... modes) {
        if (modes == null || modes.length == 0) {
            this.modes = null;
            return this;
        }
        this.modes = Collections.unmodifiableSet(EnumSet.copyOf(Arrays.asList(modes)));
        return this;
    }

    /** Appends a user-supplied message to the assertion failure output. */
    public ComparisonBuilder message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Asserts that expected and actual match, throwing
     * {@link AssertionFailedError} with every difference on failure.
     */
    public void assertMatches() {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        List<String> diffs = runMatch(expectedJson, actualJson);
        if (diffs.isEmpty()) {
            return;
        }
        String defaultMessage = buildFailureMessage(diffs);
        String finalMessage = message == null ? defaultMessage : defaultMessage + LS + message;
        throw new AssertionFailedError(finalMessage, prettyPrint(expectedJson), prettyPrint(actualJson));
    }

    /**
     * Asserts that expected and actual DO NOT match, throwing
     * {@link AssertionFailedError} when they unexpectedly do.
     */
    public void assertNotMatches() {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        List<String> diffs = runMatch(expectedJson, actualJson);
        if (!diffs.isEmpty()) {
            return;
        }
        String defaultMessage = LS + "JSONs are equal";
        String finalMessage = message == null ? defaultMessage : defaultMessage + LS + message;
        throw new AssertionFailedError(finalMessage);
    }

    /**
     * Returns the differences as a list of JSONPath-prefixed strings (legacy
     * format). Prefer {@link #result()} for new code — it gives you the same
     * information in a filterable, typed form.
     */
    public List<String> diffs() {
        JsonNode expectedJson = toJson(expected);
        JsonNode actualJson = toJson(actual);
        List<String> diffs = runMatch(expectedJson, actualJson);
        return diffs.stream().map(d -> "$" + d).toList();
    }

    /** Returns a structured {@link ComparisonResult} — never throws. */
    public ComparisonResult result() {
        return ComparisonResult.of(diffs());
    }

    private List<String> runMatch(JsonNode expectedJson, JsonNode actualJson) {
        JsonComparator effectiveComparator = comparator != null ? comparator : new DefaultJsonComparator(modes);
        return new JsonMatcher(expectedJson, actualJson, effectiveComparator, modes).match();
    }

    private String buildFailureMessage(List<String> diffs) {
        StringBuilder sb = new StringBuilder(128 + diffs.size() * 64);
        sb.append("FOUND ").append(diffs.size()).append(" DIFFERENCE(S):").append(LS);
        for (String diff : diffs) {
            sb.append(LS).append(LS).append(DIFF_DIVIDER).append(LS).append('$').append(diff);
        }
        sb.append(LS);
        if (comparator == null || comparator.getClass() == DefaultJsonComparator.class) {
            sb.append(LS).append(LS).append(ASSERTION_ERROR_HINT_MESSAGE).append(LS);
        }
        return sb.toString();
    }

    private static JsonNode toJson(Object obj) {
        try {
            return JsonUtils.toJson(obj);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON" + LS + e + LS);
        }
    }

    private static String prettyPrint(JsonNode jsonNode) {
        try {
            return JsonUtils.prettyPrint(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
