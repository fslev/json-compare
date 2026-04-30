package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import io.json.compare.util.MessageUtil;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

class JsonArrayMatcher extends AbstractJsonMatcher {

    private final BitSet matchedPositions;
    private final int expectedDoNotMatchCount;

    JsonArrayMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
        this.matchedPositions = new BitSet(actual.size());
        this.expectedDoNotMatchCount = UseCase.countDoNotMatchEntries(expected);
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();
        int expectedSize = expected.size();

        for (int i = 0; i < expectedSize; i++) {
            JsonNode expElement = expected.get(i);
            if (NodeInspect.isJsonPathNode(expElement)) {
                diffs.addAll(new JsonMatcher(expElement, actual, comparator, compareModes).match());
            } else {
                diffs.addAll(matchWithJsonArray(i, expElement, UseCase.of(expElement), actual));
            }
        }
        if (compareModes.contains(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)
                && expectedSize - expectedDoNotMatchCount < actual.size()) {
            diffs.add(" -> Actual JSON ARRAY has extra elements");
        }
        return diffs;
    }

    private List<String> matchWithJsonArray(int expPosition, JsonNode expElement, UseCase useCase, JsonNode actualArray) {
        List<String> diffs = new ArrayList<>();
        boolean strictOrder = compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER);
        int actualSize = actualArray.size();

        for (int j = 0; j < actualSize; j++) {
            if (matchedPositions.get(j)) {
                continue;
            }
            if (strictOrder) {
                if (j < expPosition) continue;
                if (j > expPosition) break;
            }
            switch (useCase) {
                case MATCH -> {
                    JsonNode actElement = actualArray.get(j);
                    List<String> elementDiffs = new JsonMatcher(expElement, actElement, comparator, compareModes).match();
                    if (elementDiffs.isEmpty()) {
                        matchedPositions.set(j);
                        return List.of();
                    }
                    if (strictOrder) {
                        for (String elementDiff : elementDiffs) {
                            diffs.add("[" + expPosition + "]" + elementDiff);
                        }
                        return diffs;
                    }
                }
                case MATCH_ANY -> {
                    matchedPositions.set(j);
                    return List.of();
                }
                case DO_NOT_MATCH -> {
                    JsonNode actElement = actualArray.get(j);
                    if (NodeInspect.areOfSameType(expElement, actElement)) {
                        List<String> elementDiffs = new JsonMatcher(expElement, actElement, comparator, compareModes).match();
                        if (!elementDiffs.isEmpty()) {
                            diffs.add("[" + expPosition + "] was found:" + LS
                                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
                            return diffs;
                        }
                    }
                }
                case DO_NOT_MATCH_ANY -> {
                    if (expected.size() - expectedDoNotMatchCount < actual.size()) {
                        diffs.add("[" + expPosition + "] -> Expected condition " + expElement
                                + " was not met. Actual JSON ARRAY has extra elements");
                    }
                    return diffs;
                }
            }
        }
        if (useCase == UseCase.MATCH) {
            diffs.add("[" + expPosition + "] was not found:" + LS
                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
        } else if (useCase == UseCase.MATCH_ANY) {
            diffs.add("[" + expPosition + "] -> Expected condition " + expElement
                    + " was not met. Actual JSON ARRAY has no extra elements");
        }
        return diffs;
    }
}
