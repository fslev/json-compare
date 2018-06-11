package ro.engineering.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonMatcher extends AbstractJsonMatcher {

    public JsonMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    @Override
    public void matches() throws MatcherException {
        if (isJsonObject(expected) && isJsonObject(actual)) {
            new JsonObjectMatcher(expected, actual).matches();
        } else if (isJsonText(expected) && isJsonText(actual)) {
            new JsonTextMatcher(expected, actual).matches();
        }
    }
}
