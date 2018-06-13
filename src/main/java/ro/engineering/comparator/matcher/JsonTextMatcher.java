package ro.engineering.comparator.matcher;

import ro.engineering.comparator.CompareMode;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JsonTextMatcher extends AbstractJsonMatcher {

    public JsonTextMatcher(JsonNode expected, JsonNode actual, Set<CompareMode> compareModes) {
        super(expected, actual, compareModes);
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
        Pattern pattern = Pattern.compile(expectedText);
        Matcher matcher = pattern.matcher(actualText);
        if ((!compareModes.contains(CompareMode.DO_NOT_USE_REGEX)
                && matcher.matches() != useCase.equals(UseCase.MATCH))
                || ((compareModes.contains(CompareMode.DO_NOT_USE_REGEX)
                        && !expectedText.equals(actualText)))) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNullType() throws MatcherException {
        if ((expected.getNodeType().equals(JsonNodeType.NULL)
                && !actual.getNodeType().equals(JsonNodeType.NULL))) {
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
