package ro.skyah.comparator.matcher;

import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    public JsonMatcher(JsonNode expected, JsonNode actual, JsonComparator jsonComparator,
            Set<CompareMode> jsonCompareModes) {
        super(expected, actual, jsonComparator, jsonCompareModes);
    }

    @Override
    public void matches() throws MatcherException {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            new JsonObjectMatcher(expected, actual).matches();
        } else if (isJsonArray(expected) && isJsonArray(actual)) {
            new JsonArrayMatcher(expected, actual).matches();
        } else if (isJsonText(expected) && isJsonText(actual)) {
            new JsonTextMatcher(expected, actual).matches();
        } else {
            throw new MatcherException("Different JSON types");
        }
    }
}
