package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.PathNotFoundException;
import io.json.compare.CompareMode;
import io.json.compare.DefaultJsonComparator;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class JsonObjectMatcher extends AbstractJsonMatcher {

    private final Set<String> matchedFieldNames = new HashSet<>();
    private final int expectedDoNotMatchCount;
    private final boolean canUseLiteralLookup;

    JsonObjectMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
        this.expectedDoNotMatchCount = UseCase.countDoNotMatchEntries(expected);
        this.canUseLiteralLookup = this.comparator.getClass() == DefaultJsonComparator.class;
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();

        for (Map.Entry<String, JsonNode> entry : expected.properties()) {
            String expectedField = entry.getKey();
            JsonNode expectedValue = entry.getValue();
            UseCase fieldUseCase = UseCase.of(expectedField);
            String expectedSanitizedField = UseCase.sanitize(expectedField);
            Optional<String> jsonPathExpression = UseCase.extractJsonPathExp(expectedSanitizedField);

            switch (fieldUseCase) {
                case MATCH_ANY, MATCH -> {
                    if (jsonPathExpression.isPresent()) {
                        try {
                            diffs.addAll(new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match());
                        } catch (PathNotFoundException e) {
                            diffs.add("." + UseCase.JSON_PATH_EXP_PREFIX + jsonPathExpression.get() + UseCase.JSON_PATH_EXP_SUFFIX
                                    + " -> Json path -> " + e.getMessage());
                        }
                    } else {
                        List<Map.Entry<String, JsonNode>> candidateEntries = searchCandidatesByField(fieldUseCase, expectedSanitizedField, actual);
                        if (candidateEntries.isEmpty()) {
                            diffs.add("." + expectedField + " was not found");
                        } else {
                            diffs.addAll(matchWithCandidates(expectedSanitizedField, expectedValue, candidateEntries));
                        }
                    }
                }
                case DO_NOT_MATCH_ANY -> {
                    if (expected.size() - expectedDoNotMatchCount < actual.size()) {
                        diffs.add(".\"" + expectedField + "\" condition was not met. Actual JSON OBJECT has extra fields");
                    }
                }
                case DO_NOT_MATCH -> {
                    if (jsonPathExpression.isPresent()) {
                        boolean pathFound;
                        try {
                            new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match();
                            pathFound = true;
                        } catch (PathNotFoundException e) {
                            pathFound = false;
                        }
                        if (pathFound) {
                            diffs.add("." + expectedField + " -> Json path was found");
                        }
                    } else {
                        List<Map.Entry<String, JsonNode>> candidateEntries = searchCandidatesByField(fieldUseCase, expectedSanitizedField, actual);
                        if (!candidateEntries.isEmpty()) {
                            diffs.add(".\"" + expectedField + "\" was found");
                        }
                    }
                }
            }
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
                && expected.size() - expectedDoNotMatchCount < actual.size()) {
            diffs.add(" -> Actual JSON OBJECT has extra fields");
        }
        return diffs;
    }

    private List<String> matchWithCandidates(String expectedField, JsonNode expectedValue, List<Map.Entry<String, JsonNode>> candidates) {
        UseCase expectedValueUseCase = UseCase.of(expectedValue);
        List<String> diffs = new ArrayList<>();

        for (Map.Entry<String, JsonNode> candidateEntry : candidates) {
            String candidateField = candidateEntry.getKey();

            if (expectedValueUseCase == UseCase.MATCH_ANY) {
                matchedFieldNames.add(candidateField);
                return List.of();
            }

            JsonNode candidateValue = candidateEntry.getValue();
            List<String> candidateDiffs = new JsonMatcher(expectedValue, candidateValue, comparator, compareModes).match();
            if (candidateDiffs.isEmpty()) {
                matchedFieldNames.add(candidateField);
                return List.of();
            }
            for (String diff : candidateDiffs) {
                diffs.add("." + expectedField + diff);
            }
        }
        return diffs;
    }

    private List<Map.Entry<String, JsonNode>> searchCandidatesByField(UseCase fieldUseCase, String fieldName, JsonNode target) {
        // Fast path: if the comparator is the default one and the expected field name is a
        // plain literal (no regex metachars, or REGEX_DISABLED is active), look up the
        // field directly in O(1) instead of scanning every field in `target`.
        if (fieldUseCase != UseCase.MATCH_ANY && canUseLiteralLookup
                && (compareModes.contains(CompareMode.REGEX_DISABLED) || isPlainLiteral(fieldName))) {
            JsonNode direct = target.get(fieldName);
            if (direct == null || matchedFieldNames.contains(fieldName)) {
                return List.of();
            }
            return List.of(Map.entry(fieldName, direct));
        }

        List<Map.Entry<String, JsonNode>> candidates = new ArrayList<>();
        for (Map.Entry<String, JsonNode> entry : target.properties()) {
            String key = entry.getKey();
            if (matchedFieldNames.contains(key)) {
                continue;
            }
            if (fieldUseCase == UseCase.MATCH_ANY || comparator.compareFields(fieldName, key)) {
                candidates.add(entry);
            }
        }
        return candidates;
    }

    private static boolean isPlainLiteral(String s) {
        for (int i = 0, n = s.length(); i < n; i++) {
            switch (s.charAt(i)) {
                case '.':
                case '*':
                case '+':
                case '?':
                case '^':
                case '$':
                case '{':
                case '}':
                case '(':
                case ')':
                case '|':
                case '[':
                case ']':
                case '\\':
                    return false;
                default:
            }
        }
        return true;
    }
}
