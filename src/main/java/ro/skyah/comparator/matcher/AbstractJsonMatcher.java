package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JsonComparator;

import java.util.HashSet;
import java.util.Set;

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

    protected static UseCase getUseCase(JsonNode node) {
        if (node.isValueNode()) {
            return getUseCase(node.asText());
        }
        return UseCase.MATCH;
    }

    protected static UseCase getUseCase(String value) {
        if (value == null || value.isEmpty()) {
            return UseCase.MATCH;
        } else if (value.startsWith(UseCase.DO_NOT_MATCH.getValue())) {
            return UseCase.DO_NOT_MATCH;
        } else return UseCase.MATCH;
    }

    protected static String sanitize(String value) {
        if (getUseCase(value) == UseCase.DO_NOT_MATCH) {
            return value.substring(1);
        }
        return removeEscapedUseCase(value);
    }

    private static String removeEscapedUseCase(String value) {
        if (value == null) {
            return value;
        }
        if (value.matches("^(\\\\*)" + UseCase.DO_NOT_MATCH + ".*$")) {
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

    protected static boolean areOfSameType(JsonNode expNode, JsonNode actNode) {
        return (isJsonText(expNode) & isJsonText(actNode))
                || (isJsonObject(expNode) & isJsonObject(actNode))
                || (isJsonArray(expNode) & isJsonArray(actNode));
    }

    public enum UseCase {
        MATCH, DO_NOT_MATCH("!");
        private String value;

        UseCase() {
        }

        UseCase(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
