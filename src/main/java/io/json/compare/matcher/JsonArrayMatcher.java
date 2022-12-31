package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import io.json.compare.util.MessageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            UseCase useCase = getUseCase(expElement);
            if (isJsonPathNode(expElement)) {
                diffs.addAll(new JsonMatcher(expElement, actual, comparator, compareModes).match());
            } else {
                diffs.addAll(matchWithJsonArray(i, expElement, useCase, actual));
            }
        }
        if (compareModes.contains(CompareMode.JSON_ARRAY_NON_EXTENSIBLE) && expected.size() - getDoNotMatchUseCases(expected) < actual.size()) {
            diffs.add("Actual JSON ARRAY has extra elements");
        }
        return diffs;
    }

    private List<String> matchWithJsonArray(int expPosition, JsonNode expElement, UseCase useCase, JsonNode actualArray) {
        List<String> diffs = new ArrayList<>();

        for (int j = 0; j < actualArray.size(); j++) {
            if (matchedPositions.contains(j)) {
                continue;
            }
            if (compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER)) {
                if (j < expPosition) {
                    continue;
                } else if (j > expPosition) {
                    break;
                }
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
                            diffs.add(String.format("JSON ARRAY elements differ at position %s:" +
                                            System.lineSeparator() + "%s" + System.lineSeparator() +
                                            "________diffs________" + System.lineSeparator() + "%s", expPosition + 1,
                                    MessageUtil.cropL(JSONCompare.prettyPrint(expElement)), String.join(
                                            System.lineSeparator() + "_____________________" + System.lineSeparator(), elementDiffs)));
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
                            diffs.add("Expected element from position " + (expPosition + 1)
                                    + " was FOUND:" + System.lineSeparator() + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
                            return diffs;
                        }
                    }
                    break;
                case DO_NOT_MATCH_ANY:
                    if (expected.size() - getDoNotMatchUseCases(expected) < actual.size()) {
                        diffs.add(String.format("Expected condition %s from position %s was not met." +
                                        " Actual JSON ARRAY has extra elements",
                                expElement, expPosition + 1));
                    }
                    return diffs;
            }
        }
        if (useCase == UseCase.MATCH) {
            diffs.add(System.lineSeparator() + "Expected element from position " + (expPosition + 1) + " was NOT FOUND:" + System.lineSeparator()
                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
        } else if (useCase == UseCase.MATCH_ANY) {
            diffs.add(String.format("Expected condition %s from position %s was not met." +
                    " Actual JSON ARRAY has no extra elements", expElement, expPosition + 1));
        }
        return diffs;
    }
}
