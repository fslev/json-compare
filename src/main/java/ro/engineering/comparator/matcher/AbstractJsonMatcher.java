package ro.engineering.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class AbstractJsonMatcher {
    protected JsonNode expected;
    protected JsonNode actual;

    public AbstractJsonMatcher(JsonNode expected, JsonNode actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public abstract void matches() throws MatcherException;

    protected static UseCase getUseCase(String value) {
        if (value == null || value.length() == 0 || !value.substring(0, 1).equals("!")) {
            return UseCase.FIND;
        }
        return UseCase.DO_NOT_FIND;
    }

    protected static String sanitize(String value) {
        if (getUseCase(value).equals(UseCase.DO_NOT_FIND)) {
            return value.substring(1, value.length());
        }
        return value;
    }

    protected static boolean isJsonObject(JsonNode jsonNode) {
        if (jsonNode == null) {
            return false;
        }
        return jsonNode.getNodeType().equals(JsonNodeType.OBJECT);
    }

    protected static boolean isJsonArray(JsonNode jsonNode) {
        if (jsonNode == null) {
            return false;
        }
        return jsonNode.getNodeType().equals(JsonNodeType.ARRAY);
    }

    protected static boolean isJsonText(JsonNode jsonNode) {
        JsonNodeType type = jsonNode.getNodeType();
        return type.equals(JsonNodeType.STRING) || type.equals(JsonNodeType.NUMBER)
                || type.equals(JsonNodeType.BOOLEAN) || type.equals(JsonNodeType.NULL);
    }

    public enum UseCase {
        FIND, DO_NOT_FIND
    }
}
