package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.Set;

public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void match() throws MatcherException {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        } else if (isJsonArray(expected) && isJsonArray(actual)) {
            new JsonArrayMatcher(expected, actual, comparator, compareModes).match();
        } else if (isValueNode(expected) && isValueNode(actual)) {
            new JsonValueMatcher(expected, actual, comparator, compareModes).match();
        } else if (isJsonPathNode(expected)) {
            new JsonObjectMatcher(expected, actual, comparator, compareModes).match();
        } else if (isMissingNode(expected) && isMissingNode(actual)) {
            //do nothing
        } else {
            throw new MatcherException("Different JSON types: "
                    + expected.getClass().getSimpleName() + " vs " + actual.getClass().getSimpleName());
        }
    }
}
