package ro.engineering.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTextMatcher extends JsonMatcher {

    public JsonTextMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    @Override
    public void matches() throws MatcherException {
        UseCase useCase = getUseCase(expected.asText());
        Pattern pattern = Pattern.compile(sanitize(expected.asText()));
        Matcher matcher = pattern.matcher(actual.asText());
        if (matcher.matches() != useCase.equals(UseCase.FIND)) {
            throw new MatcherException("Expected [" + expected + "]  but found [" + actual + "]");
        }
    }

    public static boolean isJsonText(JsonNode jsonNode) {
        JsonNodeType type = jsonNode.getNodeType();
        return type.equals(JsonNodeType.STRING) || type.equals(JsonNodeType.NUMBER) ||
                type.equals(JsonNodeType.BOOLEAN) || type.equals(JsonNodeType.NULL);
    }
}
