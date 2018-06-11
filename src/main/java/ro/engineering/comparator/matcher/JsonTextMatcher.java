package ro.engineering.comparator.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JsonTextMatcher extends AbstractJsonMatcher {

    public JsonTextMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    @Override
    public void matches() throws MatcherException {
        UseCase useCase = getUseCase(expected.asText());
        matchStringType();
        matchNumberType();
        matchBooleanType();
        matchNullType(useCase);
        Pattern pattern = Pattern.compile(sanitize(expected.asText()));
        Matcher matcher = pattern.matcher(actual.asText());
        if (matcher.matches() != useCase.equals(UseCase.FIND)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }

    private void matchNullType(UseCase useCase) throws MatcherException {
        if (actual.getNodeType().equals(JsonNodeType.NULL) && useCase.equals(UseCase.FIND)
                && !expected.getNodeType().equals(JsonNodeType.NULL)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }

    private void matchNumberType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.NUMBER)
                && !expected.getNodeType().equals(JsonNodeType.NUMBER)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }

    private void matchBooleanType() throws MatcherException {
        if (expected.getNodeType().equals(JsonNodeType.BOOLEAN)
                && !expected.getNodeType().equals(JsonNodeType.BOOLEAN)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }

    private void matchStringType() throws MatcherException {
        if (actual.getNodeType().equals(JsonNodeType.STRING)
                && !expected.getNodeType().equals(JsonNodeType.STRING)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }
}
