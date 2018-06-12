package ro.engineering.comparator.matcher;

import ro.engineering.comparator.JSONCompare;
import ro.engineering.util.StringUtil;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonArrayMatcher extends AbstractJsonMatcher {

    private Set<Integer> matchedPositions = new HashSet<Integer>();

    public JsonArrayMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    @Override
    public void matches() throws MatcherException {
        for (int i = 0; i < expected.size(); i++) {
            JsonNode element = expected.get(i);
            UseCase useCase = getUseCase(element.asText());
            boolean found = false;
            for (int j = 0; j < actual.size(); j++) {
                if (useCase.equals(UseCase.FIND)) {
                    if (matchedPositions.contains(j)) {
                        continue;
                    }
                    JsonNode actElement = actual.get(j);
                    try {
                        new JsonMatcher(element, actElement).matches();
                    } catch (MatcherException e) {
                        continue;
                    }
                    found = true;
                    matchedPositions.add(j);
                    break;
                } else {
                    if (matchedPositions.contains(j)) {
                        continue;
                    }
                    JsonNode actElement = actual.get(j);
                    try {
                        new JsonMatcher(element, actElement).matches();
                    } catch (MatcherException e) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found && useCase.equals(UseCase.FIND)) {
                throw new MatcherException("Expected element at position " + i + " NOT FOUND:\n"
                        + StringUtil.cropSmall(JSONCompare.prettyPrint(element)));
            }
            if (found && useCase.equals(UseCase.DO_NOT_FIND)) {
                throw new MatcherException("Expected element found at position " + i
                        + " was FOUND:\n" + StringUtil.cropSmall(JSONCompare.prettyPrint(element)));
            }
        }
    }
}
