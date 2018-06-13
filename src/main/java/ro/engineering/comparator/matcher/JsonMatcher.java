package ro.engineering.comparator.matcher;

import ro.engineering.comparator.CompareMode;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual, Set<CompareMode> compareModes) {
        super(expected, actual, compareModes);
    }

    @Override
    public void matches() throws MatcherException {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            new JsonObjectMatcher(expected, actual, compareModes).matches();
        } else if (isJsonArray(expected) && isJsonArray(actual)) {
            new JsonArrayMatcher(expected, actual, compareModes).matches();
        } else if (isJsonText(expected) && isJsonText(actual)) {
            new JsonTextMatcher(expected, actual, compareModes).matches();
        } else {
            throw new MatcherException("Different JSON types");
        }
    }
}
