package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.Collections;
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
            out.add(diffMsg("value", expected, actual));
            return out;
        }
        return Collections.emptyList();
    }

    private static String detectTypeMismatch(JsonNode expected, JsonNode actual) {
        if (expected.isNull() && !actual.isNull()) {
            // Preserves the long-standing message shape (double space after the colon
            // because the legacy template was "Expected %s: %s But got: %s" with the
            // middle %s left empty for null).
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
