package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.DefaultJsonComparator;
import io.json.compare.JsonComparator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class for the concrete matchers. Holds the four pieces of state every
 * matcher needs (expected, actual, comparator, modes) and defines the shared
 * {@link #match()} contract. All inspection and DSL-parsing logic lives in
 * {@link NodeInspect} and {@link UseCase} so this class stays thin.
 */
abstract class AbstractJsonMatcher {

    protected static final String LS = System.lineSeparator();

    protected final JsonComparator comparator;
    protected final Set<CompareMode> compareModes;
    protected final JsonNode expected;
    protected final JsonNode actual;

    AbstractJsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        this.expected = expected;
        this.actual = actual;
        this.compareModes = compareModes == null ? new HashSet<>() : compareModes;
        this.comparator = comparator == null ? new DefaultJsonComparator(this.compareModes) : comparator;
    }

    protected abstract List<String> match();
}
