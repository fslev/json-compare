package ro.skyah.comparator.matcher;

import ro.skyah.comparator.CompareMode;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
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
        String expectedText = compareModes.contains(CompareMode.CASE_INSENSITIVE)
                ? sanitize(expected.asText()).toLowerCase()
                : sanitize(expected.asText());
        String actualText =
                compareModes.contains(CompareMode.CASE_INSENSITIVE) ? actual.asText().toLowerCase()
                        : actual.asText();
        if (!compareModes.contains(CompareMode.DO_NOT_USE_REGEX)) {
            Pattern pattern;
            try {
                pattern = Pattern.compile(expectedText);
            } catch (PatternSyntaxException e) {
                throw new MatcherException(e.getMessage());
            }
            Matcher matcher = pattern.matcher(actualText);
            if (matcher.matches() != useCase.equals(UseCase.MATCH)) {
                throw new MatcherException(
                        String.format("Expected value: %s  But found: %s ", expected, actual));
            }
        } else {
            if (expectedText.equals(actualText) != useCase.equals(UseCase.MATCH)) {
                throw new MatcherException(
                        String.format("Expected value: %s  But found: %s ", expected, actual));
            }
        }
    }

    private void matchNullType() throws MatcherException {
        if ((expected.getNodeType().equals(JsonNodeType.NULL)
                && !actual.getNodeType().equals(JsonNodeType.NULL))
                || (compareModes.contains(CompareMode.DO_NOT_USE_REGEX)
                        && actual.getNodeType().equals(JsonNodeType.NULL)
                        && (!expected.getNodeType().equals(JsonNodeType.NULL)))) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchNumberType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.NUMBER)
                && !actual.getNodeType().equals(JsonNodeType.NUMBER)
                || (compareModes.contains(CompareMode.DO_NOT_USE_REGEX)
                        && actual.getNodeType().equals(JsonNodeType.NUMBER)
                        && (!expected.getNodeType().equals(JsonNodeType.NUMBER)))) {
            throw new MatcherException(
                    String.format("Expected value: %s  But found: %s ", expected, actual));
        }
    }

    private void matchBooleanType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.BOOLEAN)
                && !actual.getNodeType().equals(JsonNodeType.BOOLEAN)
                || (compareModes.contains(CompareMode.DO_NOT_USE_REGEX)
                        && actual.getNodeType().equals(JsonNodeType.BOOLEAN)
                        && (!expected.getNodeType().equals(JsonNodeType.BOOLEAN)))) {
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
