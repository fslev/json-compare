package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.DefaultJsonComparator;
import io.json.compare.JsonComparator;

import java.util.List;
import java.util.Set;

/**
 * Base class for the concrete matchers. Holds the state every matcher needs
 * (expected, actual, comparator, modes, expected and actual paths) and defines the shared
 * {@link #match()} contract. All inspection and DSL-parsing logic lives in
 * {@link NodeInspect} and {@link UseCase} so this class stays thin.
 */
abstract class AbstractJsonMatcher {

    protected static final String LS = System.lineSeparator();

    protected final JsonComparator comparator;
    protected final Set<CompareMode> compareModes;
    protected final JsonNode expected;
    protected final JsonNode actual;
    /**
     * Where {@link #actual} sits in the actual JSON (e.g. {@code $.records[1]}), so hints can point at
     * actual elements. {@code null} while probing: the diffs are only counted, never reported.
     */
    protected final String actualPath;
    /**
     * Where {@link #expected} sits in the expected JSON (e.g. {@code $.records[0]}), so hints can show full
     * expected paths. {@code null} while probing, like {@link #actualPath}.
     */
    protected final String expectedPath;

    AbstractJsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes,
                        String expectedPath, String actualPath) {
        this.expected = expected;
        this.actual = actual;
        this.compareModes = compareModes == null ? Set.of() : compareModes;
        this.comparator = comparator == null ? new DefaultJsonComparator(this.compareModes) : comparator;
        this.expectedPath = expectedPath;
        this.actualPath = actualPath;
    }

    protected abstract List<String> match();

    /**
     * Matches a child pair whose diffs may be reported. {@code expSegment} and {@code actSegment}
     * (e.g. {@code .name} or {@code [2]}) locate the children within {@link #expected} and {@link #actual}.
     */
    protected List<String> matchChild(JsonNode expChild, JsonNode actChild, String expSegment, String actSegment) {
        if (isProbe()) {
            return probeChild(expChild, actChild);
        }
        return new JsonMatcher(expChild, actChild, comparator, compareModes,
                expectedPath + expSegment, actualPath + actSegment).match();
    }

    /**
     * Matches a child pair only to test or rank it, skipping the work that just builds messages.
     */
    protected List<String> probeChild(JsonNode expChild, JsonNode actChild) {
        return new JsonMatcher(expChild, actChild, comparator, compareModes, null, null).match();
    }

    protected boolean isProbe() {
        return actualPath == null;
    }
}
