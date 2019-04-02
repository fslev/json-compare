package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JsonComparator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                if (getUseCase(arrayNode.get(i)).equals(UseCase.MATCH)) {
                    return UseCase.MATCH;
                }
            }
        } else if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> it = objectNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (getUseCase(entry.getKey()).equals(UseCase.MATCH)
                        && getUseCase(entry.getValue()).equals(UseCase.MATCH)) {
                    return UseCase.MATCH;
                }
            }
        }
        return UseCase.DO_NOT_MATCH;
    }

    protected static UseCase getUseCase(String value) {
        if (value == null || value.length() == 0 || !value.substring(0, 1).equals("!")) {
            return UseCase.MATCH;
        }
        return UseCase.DO_NOT_MATCH;
    }

    protected static String sanitize(String value) {
        if (getUseCase(value).equals(UseCase.DO_NOT_MATCH)) {
            return value.substring(1);
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
