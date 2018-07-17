package ro.skyah.comparator.matcher;

import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JsonComparator;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class AbstractJsonMatcher {
    protected static Set<CompareMode> compareModes = new HashSet<CompareMode>();
    protected static JsonComparator comparator = new DefaultJsonComparator();
    protected JsonNode expected;
    protected JsonNode actual;


    public AbstractJsonMatcher(JsonNode expected, JsonNode actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public AbstractJsonMatcher(JsonNode expected, JsonNode actual, JsonComparator jsonComparator,
            Set<CompareMode> jsonCompareModes) {
        this(expected, actual);
        compareModes = jsonCompareModes;
        comparator = jsonComparator;
    }

    public abstract void matches() throws MatcherException;

    protected static UseCase getUseCase(String value) {
        if (value == null || value.length() == 0 || !value.substring(0, 1).equals("!")) {
            return UseCase.MATCH;
        }
        return UseCase.DO_NOT_MATCH;
    }

    protected static String sanitize(String value) {
        if (getUseCase(value).equals(UseCase.DO_NOT_MATCH)) {
            return value.substring(1, value.length());
        }
        return removeEscapedUseCase(value);
    }

    private static String removeEscapedUseCase(String value) {
        if (value == null) {
            return value;
        }
        if (value.matches("^(\\\\*)!.*$")) {
            return value.replaceFirst("\\\\\\\\\\\\", "");
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
        MATCH, DO_NOT_MATCH
    }
}
