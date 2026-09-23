package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import io.json.compare.util.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class JsonArrayMatcher extends AbstractJsonMatcher {

    private static final int UNMATCHED = -1;

    // For each actual element: the position of the expected element it was matched by, or UNMATCHED
    private final int[] matchedBy;
    private final int expectedDoNotMatchCount;

    JsonArrayMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
        this.matchedBy = new int[actual.size()];
        Arrays.fill(this.matchedBy, UNMATCHED);
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
        Candidate closestUnmatched = null;

        for (int j = 0; j < actualSize; j++) {
            if (matchedBy[j] != UNMATCHED) {
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
                        matchedBy[j] = expPosition;
                        return List.of();
                    }
                    if (strictOrder) {
                        for (String elementDiff : elementDiffs) {
                            diffs.add("[" + expPosition + "]" + elementDiff);
                        }
                        return diffs;
                    }
                    if (isHintCandidate(expElement, actElement)) {
                        closestUnmatched = Candidate.closer(closestUnmatched, new Candidate(j, elementDiffs));
                    }
                }
                case MATCH_ANY -> {
                    matchedBy[j] = expPosition;
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
                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement))
                    + hint(closestUnmatched)
                    + (strictOrder ? "" : hint(closestMatched(expElement, actualArray))));
        } else if (useCase == UseCase.MATCH_ANY) {
            diffs.add("[" + expPosition + "] -> Expected condition " + expElement
                    + " was not met. Actual JSON ARRAY has no extra elements");
        }
        return diffs;
    }

    // Only objects and arrays can partially match an element of the same type; scalars simply differ
    private static boolean isHintCandidate(JsonNode expElement, JsonNode actElement) {
        return expElement.isContainerNode() && NodeInspect.areOfSameType(expElement, actElement);
    }

    private Candidate closestMatched(JsonNode expElement, JsonNode actualArray) {
        Candidate closest = null;
        for (int j = 0; j < actualArray.size() && (closest == null || !closest.diffs().isEmpty()); j++) {
            JsonNode actElement = actualArray.get(j);
            if (matchedBy[j] != UNMATCHED && isHintCandidate(expElement, actElement)) {
                List<String> elementDiffs = new JsonMatcher(expElement, actElement, comparator, compareModes).match();
                closest = Candidate.closer(closest, new Candidate(j, elementDiffs));
            }
        }
        return closest;
    }

    private String hint(Candidate candidate) {
        if (candidate == null) {
            return "";
        }
        int matchedByPosition = matchedBy[candidate.index()];
        StringBuilder sb = new StringBuilder(LS).append("Closest ")
                .append(matchedByPosition == UNMATCHED ? "unmatched" : "matched")
                .append(" actual element [").append(candidate.index()).append(']');
        if (matchedByPosition != UNMATCHED) {
            sb.append(" (matched by expected [").append(matchedByPosition).append("])");
        }
        if (candidate.diffs().isEmpty()) {
            return sb.append(" would match").toString();
        }
        sb.append(" differs by:");
        for (String diff : candidate.diffs()) {
            sb.append(LS).append("  - ").append(diff.stripLeading().replace(LS, LS + "    "));
        }
        return MessageUtil.cropL(sb.toString());
    }

    private record Candidate(int index, List<String> diffs) {

        // Fewer diffs wins; on a tie the earlier candidate is kept
        static Candidate closer(Candidate current, Candidate other) {
            return current == null || other.diffs.size() < current.diffs.size() ? other : current;
        }
    }
}
