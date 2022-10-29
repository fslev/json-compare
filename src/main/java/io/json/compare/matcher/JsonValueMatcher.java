package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class JsonValueMatcher extends AbstractJsonMatcher {

    JsonValueMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();
        String diff = System.lineSeparator() + "Expected %s: %s But got: %s";

        if (expected.isNull() && !actual.isNull()) {
            diffs.add(String.format(diff, "null", "", actual));
            return diffs;
        } else if (expected.isNumber() && !actual.isNumber()) {
            diffs.add(String.format(diff, "number", expected, actual));
            return diffs;
        } else if (expected.isBoolean() && !actual.isBoolean()) {
            diffs.add(String.format(diff, "boolean", expected, actual));
            return diffs;
        } else if (actual.isTextual() && !expected.isTextual()) {
            diffs.add(String.format(diff, "text", expected, actual));
            return diffs;
        } else {
            UseCase useCase = getUseCase(expected.asText());
            String expectedText = sanitize(expected.asText());
            String actualText = actual.asText();

            if (!useCase.equals(UseCase.MATCH_ANY) && comparator.compareValues(expectedText, actualText) != useCase.equals(UseCase.MATCH)) {
                diffs.add(String.format(diff, "value", expected, actual));
            }
            return diffs;
        }
    }
}