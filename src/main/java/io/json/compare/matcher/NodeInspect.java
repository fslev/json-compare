package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 * Null-tolerant type predicates over Jackson {@link JsonNode}s. These are pure
 * functions extracted from the matcher hierarchy so the abstract base class
 * stays focused on state + dispatch rather than on inspection details.
 */
final class NodeInspect {

    private NodeInspect() {
    }

    static boolean isJsonObject(JsonNode node) {
        return node != null && node.isObject();
    }

    static boolean isJsonArray(JsonNode node) {
        return node != null && node.isArray();
    }

    static boolean isValueNode(JsonNode node) {
        return node != null && node.isValueNode();
    }

    static boolean isMissingNode(JsonNode node) {
        return node != null && node.isMissingNode();
    }

    /**
     * A "JSON path node" is an object whose every field name is wrapped in the
     * {@code #(...)} marker. Such objects are handled specially — the field
     * names are evaluated as JSON path expressions against the actual node.
     */
    static boolean isJsonPathNode(JsonNode node) {
        if (node == null || !node.isObject()) {
            return false;
        }
        Iterator<String> fieldNames = node.fieldNames();
        if (!fieldNames.hasNext()) {
            return false;
        }
        while (fieldNames.hasNext()) {
            if (UseCase.extractJsonPathExp(fieldNames.next()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Two nodes are "of same type" if they are both values, both objects, both
     * arrays, or if the expected side is a JSON-path wrapper (in which case the
     * actual side is resolved dynamically, so a type check is meaningless).
     */
    static boolean areOfSameType(JsonNode expected, JsonNode actual) {
        return (isValueNode(expected) && isValueNode(actual))
                || (isJsonObject(expected) && isJsonObject(actual))
                || (isJsonArray(expected) && isJsonArray(actual))
                || isJsonPathNode(expected);
    }
}
