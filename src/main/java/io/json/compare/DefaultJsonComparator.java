package io.json.compare;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Default {@link JsonComparator}: treats expected strings as Java regular expressions
 * (with {@link Pattern#DOTALL} and {@link Pattern#MULTILINE}), falling back to literal
 * equality either when {@link CompareMode#REGEX_DISABLED} is set or when the pattern
 * fails to compile. Compiled patterns are cached for the lifetime of this comparator,
 * which substantially reduces cost for JSONs that repeat the same expected values or
 * field names.
 */
public class DefaultJsonComparator implements JsonComparator {

    private static final int PATTERN_FLAGS = Pattern.DOTALL | Pattern.MULTILINE;

    private final Set<CompareMode> compareModes;
    private final ConcurrentMap<String, Optional<Pattern>> patternCache = new ConcurrentHashMap<>();

    public DefaultJsonComparator(Set<CompareMode> compareModes) {
        this.compareModes = compareModes;
    }

    @Override
    public boolean compareValues(Object expected, Object actual) {
        return match(expected.toString(), actual.toString());
    }

    @Override
    public boolean compareFields(String expected, String actual) {
        return match(expected, actual);
    }

    private boolean match(String expected, String actual) {
        if (compareModes != null && compareModes.contains(CompareMode.REGEX_DISABLED)) {
            return expected.equals(actual);
        }
        Optional<Pattern> pattern = patternCache.computeIfAbsent(expected, DefaultJsonComparator::tryCompile);
        return pattern.map(p -> p.matcher(actual).matches()).orElseGet(() -> expected.equals(actual));
    }

    private static Optional<Pattern> tryCompile(String regex) {
        try {
            return Optional.of(Pattern.compile(regex, PATTERN_FLAGS));
        } catch (PatternSyntaxException e) {
            return Optional.empty();
        }
    }
}
