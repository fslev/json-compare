package ro.engineering.comparator.matcher;

import ro.engineering.comparator.CompareMode;
import ro.engineering.comparator.JSONCompare;
import ro.engineering.util.StringUtil;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonArrayMatcher extends AbstractJsonMatcher {

    private Set<Integer> matchedPositions = new HashSet<Integer>();

    public JsonArrayMatcher(JsonNode expected, JsonNode actual, Set<CompareMode> compareModes) {
        super(expected, actual, compareModes);
    }

    @Override
    public void matches() throws MatcherException {
        for (int i = 0; i < expected.size(); i++) {
            JsonNode element = expected.get(i);
            UseCase useCase = getUseCase(element.asText());
            boolean found = false;
            for (int j = 0; j < actual.size(); j++) {
                if (useCase.equals(UseCase.MATCH)) {
                    if (matchedPositions.contains(j)) {
                        continue;
                    }
                    JsonNode actElement = actual.get(j);
                    try {
                        new JsonMatcher(element, actElement, compareModes).matches();
                    } catch (MatcherException e) {
                        if (i == j && compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER)) {
                            throw new MatcherException(String
                                    .format("JSON ARRAY elements differ at position %s", i + 1));
                        }
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
                        new JsonMatcher(element, actElement, compareModes).matches();
                    } catch (MatcherException e) {
                        if (i == j && compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER)) {
                            throw new MatcherException(String
                                    .format("JSON ARRAY elements differ at position %s", i + 1));
                        }
                        found = true;
                        break;
                    }
                }
            }
            if (!found && useCase.equals(UseCase.MATCH)) {
                throw new MatcherException("Expected element at position " + (i + 1)
                        + " NOT FOUND:\n" + StringUtil.cropSmall(JSONCompare.prettyPrint(element)));
            }
            if (found && useCase.equals(UseCase.DO_NOT_MATCH)) {
                throw new MatcherException("Expected element found at position " + (i + 1)
                        + " was FOUND:\n" + StringUtil.cropSmall(JSONCompare.prettyPrint(element)));
            }

            if (compareModes.contains(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)
                    && expected.size() < actual.size()) {
                throw new MatcherException("Actual JSON ARRAY has extra elements");
            }
        }
    }
}
