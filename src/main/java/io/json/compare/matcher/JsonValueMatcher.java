package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

class JsonValueMatcher extends AbstractJsonMatcher {

    private static final String DIFF_FORMAT = LS + "Expected %s: %s But got: %s";

    JsonValueMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        String typeDiff = detectTypeMismatch(expected, actual);
        if (typeDiff != null) {
            List<String> out = new ArrayList<>(1);
            out.add(typeDiff);
            return out;
        }

        UseCase useCase = UseCase.of(expected.asText());
        if (useCase == UseCase.MATCH_ANY) {
            return Collections.emptyList();
        }

        String expectedText = UseCase.sanitize(expected.asText());
        String actualText = actual.asText();
        boolean matches = comparator.compareValues(expectedText, actualText);

        if (matches != (useCase == UseCase.MATCH)) {
            List<String> out = new ArrayList<>(1);
            out.add(String.format(DIFF_FORMAT, "value", expected, actual));
            return out;
        }
        return Collections.emptyList();
    }

    private static String detectTypeMismatch(JsonNode expected, JsonNode actual) {
        if (expected.isNull() && !actual.isNull()) {
            return String.format(DIFF_FORMAT, "null", "", actual);
        }
        if (expected.isNumber() && !actual.isNumber()) {
            return String.format(DIFF_FORMAT, "number", expected, actual);
        }
        if (expected.isBoolean() && !actual.isBoolean()) {
            return String.format(DIFF_FORMAT, "boolean", expected, actual);
        }
        if (actual.isTextual() && !expected.isTextual()) {
            return String.format(DIFF_FORMAT, "text", expected, actual);
        }
        return null;
    }
}
