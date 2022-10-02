package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import io.json.compare.util.MessageUtil;

import java.util.*;

class JsonArrayMatcher extends AbstractJsonMatcher {

    private final Set<Integer> matchedPositions = new HashSet<>();

    JsonArrayMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();

        for (int i = 0; i < expected.size(); i++) {
            JsonNode expElement = expected.get(i);
            if (isJsonPathNode(expElement)) {
                diffs.addAll(new JsonMatcher(expElement, actual, comparator, compareModes).match());
            } else {
                diffs.addAll(matchWithJsonArray(i, expElement, actual));
            }
        }
        if (compareModes.contains(CompareMode.JSON_ARRAY_NON_EXTENSIBLE) && expected.size() < actual.size()) {
            diffs.add("Actual JSON ARRAY has extra elements");
        }
        return diffs;
    }

    private List<String> matchWithJsonArray(int expPosition, JsonNode expElement, JsonNode actualArray) {
        List<String> diffs = new ArrayList<>();
        UseCase useCase = getUseCase(expElement);
        boolean found = false;

        for (int j = 0; j < actualArray.size(); j++) {
            if (matchedPositions.contains(j)) {
                continue;
            }
            if (compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER) && j != expPosition) {
                continue;
            }
            List<String> elementDiffs;
            switch (useCase) {
                case MATCH:
                    JsonNode actElement = actualArray.get(j);
                    elementDiffs = new JsonMatcher(expElement, actElement, comparator, compareModes).match();
                    if (elementDiffs.isEmpty()) {
                        matchedPositions.add(j);
                        return Collections.emptyList();
                    } else {
                        if (compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER)) {
                            diffs.add(String.format("JSON ARRAY elements differ at position %s:\n%s\nDifferences:\n%s", expPosition + 1,
                                    MessageUtil.cropL(JSONCompare.prettyPrint(expElement)), String.join("\n", elementDiffs)));
                            return diffs;
                        }
                    }
                    break;
                case MATCH_ANY:
                    matchedPositions.add(j);
                    return Collections.emptyList();
                case DO_NOT_MATCH:
                    actElement = actualArray.get(j);
                    if (areOfSameType(expElement, actElement)) {
                        elementDiffs = new JsonMatcher(expElement, actElement, comparator, compareModes).match();
                        if (!elementDiffs.isEmpty()) {
                            found = true;
                        }
                    }
                    break;
                case DO_NOT_MATCH_ANY:
                    diffs.add(String.format("Actual JSON array has extra elements.\nCondition %s from position %s means there" +
                                    " should be no more actual elements to match",
                            expElement, expPosition + 1));
                    return diffs;
            }
            if (found) {
                break;
            }
        }
        if (!found && useCase == UseCase.MATCH) {
            diffs.add("Expected element from position " + (expPosition + 1) + " was NOT FOUND:\n"
                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
        } else if (found) {
            diffs.add("Expected element from position " + (expPosition + 1)
                    + " was FOUND:\n" + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
        } else if (useCase == UseCase.MATCH_ANY) {
            diffs.add(String.format("Actual Json Array has no extra elements. Condition %s from position %s means there" +
                    " should be more actual elements", expElement, expPosition + 1));
        }
        return diffs;
    }
}
