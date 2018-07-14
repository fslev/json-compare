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
    public void matches() throws MatcherException {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            new JsonObjectMatcher(expected, actual, comparator, compareModes).matches();
        } else if (isJsonArray(expected) && isJsonArray(actual)) {
            new JsonArrayMatcher(expected, actual, comparator, compareModes).matches();
        } else if (isJsonText(expected) && isJsonText(actual)) {
            new JsonTextMatcher(expected, actual, comparator, compareModes).matches();
        } else {
            throw new MatcherException("Different JSON types");
        }
    }
}
