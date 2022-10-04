package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            return new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        } else if (isJsonArray(expected) && isJsonArray(actual)) {
            return new JsonArrayMatcher(expected, actual, comparator, compareModes).match();
        } else if (isValueNode(expected) && isValueNode(actual)) {
            return new JsonValueMatcher(expected, actual, comparator, compareModes).match();
        } else if (isJsonPathNode(expected)) {
            return new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        } else if (isMissingNode(expected) && isMissingNode(actual)) {
            return Collections.emptyList();
        } else {
            List<String> diffs = new ArrayList<>();
            diffs.add("Different JSON types: expected " + expected.getClass().getSimpleName() + " but got " + actual.getClass().getSimpleName());
            return diffs;
        }
    }
}
