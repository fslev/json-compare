package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.List;
import java.util.Set;

/**
 * Top-level dispatcher: decides which concrete matcher should handle a pair of
 * nodes based on their Jackson node kinds. Kept thin on purpose — all
 * comparison logic lives inside {@link JsonObjectMatcher},
 * {@link JsonArrayMatcher}, {@link JsonValueMatcher}, or
 * {@link JsonPathMatcher}.
 */
public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        if (NodeInspect.isJsonObject(expected) && NodeInspect.isJsonObject(actual)) {
            return new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        }
        if (NodeInspect.isJsonArray(expected) && NodeInspect.isJsonArray(actual)) {
            return new JsonArrayMatcher(expected, actual, comparator, compareModes).match();
        }
        if (NodeInspect.isValueNode(expected) && NodeInspect.isValueNode(actual)) {
            return new JsonValueMatcher(expected, actual, comparator, compareModes).match();
        }
        if (NodeInspect.isJsonPathNode(expected)) {
            return new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        }
        if (NodeInspect.isMissingNode(expected) && NodeInspect.isMissingNode(actual)) {
            return List.of();
        }
        return List.of(" -> Different JSON types: expected "
                + expected.getClass().getSimpleName() + " but got " + actual.getClass().getSimpleName());
    }
}
