package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.List;
import java.util.Set;

class JsonValueMatcher extends AbstractJsonMatcher {

    JsonValueMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        String typeDiff = detectTypeMismatch(expected, actual);
        if (typeDiff != null) {
            return List.of(typeDiff);
        }

        UseCase useCase = UseCase.of(expected.asText());
        if (useCase == UseCase.MATCH_ANY) {
            return List.of();
        }

        String expectedText = UseCase.sanitize(expected.asText());
        String actualText = actual.asText();
        boolean matches = comparator.compareValues(expectedText, actualText);

        if (matches != (useCase == UseCase.MATCH)) {
            return List.of(diffMsg("value", expected, actual));
        }
        return List.of();
    }

    private static String detectTypeMismatch(JsonNode expected, JsonNode actual) {
        if (expected.isNull() && !actual.isNull()) {
            return LS + "Expected null:  But got: " + actual;
        }
        if (expected.isNumber() && !actual.isNumber()) {
            return diffMsg("number", expected, actual);
        }
        if (expected.isBoolean() && !actual.isBoolean()) {
            return diffMsg("boolean", expected, actual);
        }
        if (actual.isTextual() && !expected.isTextual()) {
            return diffMsg("text", expected, actual);
        }
        return null;
    }

    private static String diffMsg(String kind, Object expected, Object actual) {
        return LS + "Expected " + kind + ": " + expected + " But got: " + actual;
    }
}
