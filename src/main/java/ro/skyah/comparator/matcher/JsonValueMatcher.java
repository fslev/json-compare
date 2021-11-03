package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.Set;

class JsonValueMatcher extends AbstractJsonMatcher {

    JsonValueMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void match() throws MatcherException {
        UseCase useCase = getUseCase(expected.asText());
        matchStringType();
        matchNumberType();
        matchBooleanType();
        matchNullType();
        String expectedText = sanitize(expected.asText());
        String actualText = actual.asText();

        if (comparator.compareValues(expectedText, actualText) != useCase.equals(UseCase.MATCH)) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNullType() throws MatcherException {
        if (expected.isNull() && !actual.isNull()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNumberType() throws MatcherException {
        if (expected.isNumber() && !actual.isNumber()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchBooleanType() throws MatcherException {
        if (expected.isBoolean() && !actual.isBoolean()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchStringType() throws MatcherException {
        if (actual.isTextual() && !expected.isTextual()) {
            throw new MatcherException(String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }
}