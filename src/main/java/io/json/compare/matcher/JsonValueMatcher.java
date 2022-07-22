package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.Set;

class JsonValueMatcher extends AbstractJsonMatcher {

    JsonValueMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void match() throws MatcherException {
        UseCase useCase = getUseCase(expected.asText());
        matchTextTypes();
        matchNumberTypes();
        matchBooleanTypes();
        matchNullTypes();
        String expectedText = sanitize(expected.asText());
        String actualText = actual.asText();

        if (!useCase.equals(UseCase.MATCH_ANY) && comparator.compareValues(expectedText, actualText) != useCase.equals(UseCase.MATCH)) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNullTypes() throws MatcherException {
        if (expected.isNull() && !actual.isNull()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNumberTypes() throws MatcherException {
        if (expected.isNumber() && !actual.isNumber()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchBooleanTypes() throws MatcherException {
        if (expected.isBoolean() && !actual.isBoolean()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchTextTypes() throws MatcherException {
        if (actual.isTextual() && !expected.isTextual()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }
}