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
        matchNullType(useCase);
        Pattern pattern = Pattern.compile(sanitize(expected.asText()));
        Matcher matcher = pattern.matcher(actual.asText());
        if (matcher.matches() != useCase.equals(UseCase.FIND)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }

    private void matchNullType(UseCase useCase) throws MatcherException {
        if (useCase.equals(UseCase.FIND) && actual.getNodeType().equals(JsonNodeType.NULL)
                && !expected.getNodeType().equals(JsonNodeType.NULL)) {
            throw new MatcherException("Expected [" + expected + "] but found [" + actual + "]");
        }
    }
}
