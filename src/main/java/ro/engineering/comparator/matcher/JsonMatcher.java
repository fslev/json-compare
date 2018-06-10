package ro.engineering.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class JsonMatcher {
    protected JsonNode expected;
    protected JsonNode actual;

    public JsonMatcher(JsonNode expected, JsonNode actual) {
        this.expected = expected;
        this.actual = actual;
    }

    protected abstract void matches() throws MatcherException;

    public static UseCase getUseCase(String value) {
        if (value == null || value.length() == 0 || !value.substring(0, 1).equals("!")) {
            return UseCase.FIND;
        }
        return UseCase.DO_NOT_FIND;
    }

    protected String sanitize(String value) {
        if (getUseCase(value).equals(UseCase.DO_NOT_FIND)) {
            return value.substring(1, value.length());
        }
        return value;
    }

    public enum UseCase {
        FIND, DO_NOT_FIND
    }

}
