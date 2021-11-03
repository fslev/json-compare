package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JsonComparator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

abstract class AbstractJsonMatcher {

    protected final static String JSON_PATH_EXP_PREFIX = "#(";
    protected final static String JSON_PATH_EXP_SUFFIX = ")";

    protected JsonComparator comparator;
    protected Set<CompareMode> compareModes;
    protected JsonNode expected;
    protected JsonNode actual;

    AbstractJsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        this.expected = expected;
        this.actual = actual;
        this.comparator = comparator == null ? new DefaultJsonComparator() : comparator;
        this.compareModes = compareModes == null ? new HashSet<>() : compareModes;
    }

    protected abstract void match() throws MatcherException;

    protected static UseCase getUseCase(JsonNode node) {
        if (node.isValueNode()) {
            return getUseCase(node.asText());
        }
        return UseCase.MATCH;
    }

    protected static UseCase getUseCase(String value) {
        if (value == null || value.isEmpty()) {
            return UseCase.MATCH;
        } else if (value.equals(UseCase.MATCH_ANY.getValue())) {
            return UseCase.MATCH_ANY;
        } else if (value.equals(UseCase.DO_NOT_MATCH_ANY.getValue())) {
            return UseCase.DO_NOT_MATCH_ANY;
        } else if (value.startsWith(UseCase.DO_NOT_MATCH.getValue())) {
            return UseCase.DO_NOT_MATCH;
        } else return UseCase.MATCH;
    }

    protected static String sanitize(String value) {
        if (getUseCase(value) == UseCase.DO_NOT_MATCH || getUseCase(value) == UseCase.DO_NOT_MATCH_ANY) {
            return value.substring(1);
        }
        return removeEscapes(value);
    }

    protected static Optional<String> extractJsonPathExp(String field) {
        if (field.startsWith(JSON_PATH_EXP_PREFIX) && field.endsWith(JSON_PATH_EXP_SUFFIX)) {
            return Optional.of(field.substring(JSON_PATH_EXP_PREFIX.length(), field.length() - JSON_PATH_EXP_SUFFIX.length()));
        }
        return Optional.empty();
    }

    private static String removeEscapes(String value) {
        if (value == null) {
            return null;
        }
        if (value.startsWith("\\" + UseCase.DO_NOT_MATCH.getValue()) ||
                value.equals("\\" + UseCase.DO_NOT_MATCH_ANY.getValue()) ||
                value.equals("\\" + UseCase.MATCH_ANY.getValue()) ||
                value.startsWith("\\" + JSON_PATH_EXP_PREFIX)) {
            return value.replaceFirst("\\\\", "");
        }
        return value;
    }

    protected static boolean isJsonObject(JsonNode jsonNode) {
        return jsonNode != null && jsonNode.isObject();
    }

    protected static boolean isJsonArray(JsonNode jsonNode) {
        return jsonNode != null && jsonNode.isArray();
    }

    protected static boolean isValueNode(JsonNode jsonNode) {
        return jsonNode != null && jsonNode.isValueNode();
    }

    protected static boolean isJsonPathNode(JsonNode jsonNode) {
        if (jsonNode != null && jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                if (!extractJsonPathExp(fieldNames.next()).isPresent()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    protected static boolean isMissingNode(JsonNode jsonNode) {
        return jsonNode != null && jsonNode.isMissingNode();
    }

    protected static boolean areOfSameType(JsonNode expNode, JsonNode actNode) {
        return (isValueNode(expNode) & isValueNode(actNode)) || (isJsonObject(expNode) & isJsonObject(actNode))
                || (isJsonArray(expNode) & isJsonArray(actNode) || isJsonPathNode(expNode));
    }

    protected enum UseCase {
        MATCH, DO_NOT_MATCH("!"), MATCH_ANY(".*"), DO_NOT_MATCH_ANY("!.*");
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
