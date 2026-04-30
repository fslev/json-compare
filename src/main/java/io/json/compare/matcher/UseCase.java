package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Optional;

/**
 * Matching use-case encoded in either a field name or a string value of the
 * expected JSON. The symbolic prefixes ({@code !}, {@code .*}, {@code !.*}) are
 * part of the library's public DSL and are documented in the README.
 *
 * <p>This enum also centralizes the small parsing helpers that translate a raw
 * token into a use-case, strip its marker prefix, and recognize the embedded
 * JSON path expression syntax ({@code #(...)}).
 */
enum UseCase {
    MATCH(""),
    DO_NOT_MATCH("!"),
    MATCH_ANY(".*"),
    DO_NOT_MATCH_ANY("!.*");

    static final String JSON_PATH_EXP_PREFIX = "#(";
    static final String JSON_PATH_EXP_SUFFIX = ")";

    private final String value;

    UseCase(String value) {
        this.value = value;
    }

    /**
     * Resolves the use-case for a JSON node. Non-textual nodes are always
     * {@link #MATCH} since the DSL markers only apply to strings.
     */
    static UseCase of(JsonNode node) {
        return node.isTextual() ? of(node.asText()) : MATCH;
    }

    /**
     * Resolves the use-case for a raw string token.
     * Order matters: {@code "!.*"} must be checked before {@code "!"}.
     */
    static UseCase of(String value) {
        if (MATCH_ANY.value.equals(value)) return MATCH_ANY;
        if (DO_NOT_MATCH_ANY.value.equals(value)) return DO_NOT_MATCH_ANY;
        if (value.startsWith(DO_NOT_MATCH.value)) return DO_NOT_MATCH;
        return MATCH;
    }

    /**
     * Strips the DSL marker prefix from a value or field (e.g. {@code "!foo"} →
     * {@code "foo"}). Also removes a single leading backslash used to escape an
     * otherwise-significant DSL token (e.g. {@code "\!foo"} → {@code "!foo"}).
     */
    static String sanitize(String value) {
        UseCase useCase = of(value);
        if (useCase == DO_NOT_MATCH || useCase == DO_NOT_MATCH_ANY) {
            return value.substring(1);
        }
        return removeEscapes(value);
    }

    /**
     * Extracts the JSON path expression wrapped in {@code #(...)} markers,
     * returning the inner expression. Empty if the field is not a JSON-path
     * expression.
     */
    static Optional<String> extractJsonPathExp(String field) {
        if (field.startsWith(JSON_PATH_EXP_PREFIX) && field.endsWith(JSON_PATH_EXP_SUFFIX)) {
            return Optional.of(field.substring(JSON_PATH_EXP_PREFIX.length(),
                    field.length() - JSON_PATH_EXP_SUFFIX.length()));
        }
        return Optional.empty();
    }

    /**
     * Counts how many entries in the given expected node carry a
     * "do-not-match" style use-case (either as a field name or as an element),
     * including JSON-path markers. The result is used to compute effective
     * sizes when {@code *_NON_EXTENSIBLE} modes are active.
     */
    static int countDoNotMatchEntries(JsonNode expected) {
        int count = 0;
        if (expected.isArray()) {
            int size = expected.size();
            for (int i = 0; i < size; i++) {
                JsonNode element = expected.get(i);
                UseCase useCase = of(element);
                if (useCase == DO_NOT_MATCH_ANY || useCase == DO_NOT_MATCH || NodeInspect.isJsonPathNode(element)) {
                    count++;
                }
            }
        } else if (expected.isObject()) {
            Iterator<String> it = expected.fieldNames();
            while (it.hasNext()) {
                String field = it.next();
                UseCase useCase = of(field);
                if (useCase == DO_NOT_MATCH_ANY || useCase == DO_NOT_MATCH
                        || extractJsonPathExp(field).isPresent()) {
                    count++;
                }
            }
        }
        return count;
    }

    private static String removeEscapes(String value) {
        if (value.startsWith("\\" + DO_NOT_MATCH.value)
                || value.equals("\\" + MATCH_ANY.value)
                || value.startsWith("\\" + JSON_PATH_EXP_PREFIX)) {
            return value.replaceFirst("\\\\", "");
        }
        return value;
    }
}
