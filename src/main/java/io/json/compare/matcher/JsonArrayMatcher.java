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
    private final BitSet notFoundPositions;
    private final int expectedDoNotMatchCount;

    JsonArrayMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes,
                     String actualPath) {
        super(expected, actual, comparator, compareModes, actualPath);
        this.matchedPositions = new BitSet(actual.size());
        this.notFoundPositions = new BitSet(expected.size());
        this.expectedDoNotMatchCount = UseCase.countDoNotMatchEntries(expected);
    }

    @Override
    public List<String> match() {
        int expectedSize = expected.size();
        List<List<String>> elementDiffs = new ArrayList<>(expectedSize);
        for (int i = 0; i < expectedSize; i++) {
            JsonNode expElement = expected.get(i);
            if (NodeInspect.isJsonPathNode(expElement)) {
                elementDiffs.add(matchChild(expElement, actual, ""));
            } else {
                elementDiffs.add(matchWithJsonArray(i, expElement, UseCase.of(expElement), actual));
            }
        }
        // Not-found elements are described only now, when it is final which actual elements are left unmatched
        List<String> diffs = new ArrayList<>();
        for (int i = 0; i < expectedSize; i++) {
            diffs.addAll(elementDiffs.get(i));
            if (notFoundPositions.get(i)) {
                diffs.add(notFoundDiff(i, expected.get(i)));
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
                    List<String> elementDiffs = strictOrder
                            ? matchChild(expElement, actElement, "[" + j + "]")
                            : probeChild(expElement, actElement);
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
                        List<String> elementDiffs = probeChild(expElement, actElement);
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
            notFoundPositions.set(expPosition);
        } else if (useCase == UseCase.MATCH_ANY) {
            diffs.add("[" + expPosition + "] -> Expected condition " + expElement
                    + " was not met. Actual JSON ARRAY has no extra elements");
        }
        return diffs;
    }

    private String notFoundDiff(int expPosition, JsonNode expElement) {
        if (isProbe()) {
            return "[" + expPosition + "] was not found";
        }
        String diff = "[" + expPosition + "] was not found:" + LS + MessageUtil.cropL(JSONCompare.prettyPrint(expElement));
        return compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER) ? diff : diff + hint(expElement);
    }

    private String hint(JsonNode expElement) {
        if (matchedPositions.cardinality() == actual.size()) {
            return LS + "No unmatched actual elements left in " + actualPath + ", so this expected element might be extra";
        }
        int closest = closestUnmatched(expElement);
        if (closest < 0) {
            return "";
        }
        String segment = "[" + closest + "]";
        StringBuilder sb = new StringBuilder(LS).append("Closest unmatched actual element ")
                .append(actualPath).append(segment).append(" differs by:");
        for (String diff : matchChild(expElement, actual.get(closest), segment)) {
            sb.append(LS).append("  - ").append(diff.stripLeading().replace(LS, LS + "    "));
        }
        return MessageUtil.cropL(sb.toString());
    }

    // The unmatched element of the same type with the fewest diffs (the lowest index on a tie), or -1.
    // Objects and arrays only: a scalar either matches or it doesn't, so a closest one tells nothing.
    private int closestUnmatched(JsonNode expElement) {
        int closest = -1;
        if (!expElement.isContainerNode()) {
            return closest;
        }
        int fewestDiffs = Integer.MAX_VALUE;
        for (int j = matchedPositions.nextClearBit(0); j < actual.size(); j = matchedPositions.nextClearBit(j + 1)) {
            JsonNode actElement = actual.get(j);
            if (NodeInspect.areOfSameType(expElement, actElement)) {
                int diffCount = probeChild(expElement, actElement).size();
                if (diffCount < fewestDiffs) {
                    closest = j;
                    fewestDiffs = diffCount;
                }
            }
        }
        return closest;
    }
}
