package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.Set;

public class JsonTextMatcher extends AbstractJsonMatcher {

    public JsonTextMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator,
                           Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void matches() throws MatcherException {
        UseCase useCase = getUseCase(expected.asText());
        matchStringType();
        matchNumberType();
        matchBooleanType();
        matchNullType();
        String expectedText = sanitize(expected.asText());
        String actualText = actual.asText();

        if (comparator.compareValues(expectedText, actualText) != useCase.equals(UseCase.MATCH)) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNullType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.NULL)
                && !actual.getNodeType().equals(JsonNodeType.NULL)) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNumberType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.NUMBER)
                && !actual.getNodeType().equals(JsonNodeType.NUMBER)) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchBooleanType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.BOOLEAN)
                && !actual.getNodeType().equals(JsonNodeType.BOOLEAN)) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchStringType() throws MatcherException {
        if (actual.getNodeType().equals(JsonNodeType.STRING)
                && !expected.getNodeType().equals(JsonNodeType.STRING)) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }
}
