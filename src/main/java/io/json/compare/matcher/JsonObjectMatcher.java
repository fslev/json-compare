package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.PathNotFoundException;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class JsonObjectMatcher extends AbstractJsonMatcher {

    private final Set<String> matchedFieldNames = new HashSet<>();

    JsonObjectMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();

        Iterator<Map.Entry<String, JsonNode>> it = expected.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String expectedField = entry.getKey();
            JsonNode expectedValue = entry.getValue();
            UseCase fieldUseCase = getUseCase(expectedField);
            String expectedSanitizedField = sanitize(expectedField);
            Optional<String> jsonPathExpression = extractJsonPathExp(expectedSanitizedField);
            List<Map.Entry<String, JsonNode>> candidateEntries = null;
            if (!jsonPathExpression.isPresent()) {
                candidateEntries = searchCandidatesByField(fieldUseCase, expectedSanitizedField, actual);
            }
            switch (fieldUseCase) {
                case MATCH_ANY:
                case MATCH:
                    if (!jsonPathExpression.isPresent()) {
                        if (candidateEntries.isEmpty()) {
                            diffs.add(String.format("Field '%s' was NOT FOUND", expectedField));
                        } else {
                            diffs.addAll(matchWithCandidates(expectedSanitizedField, expectedValue, candidateEntries));
                        }
                    } else {
                        try {
                            diffs.addAll(new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match());
                        } catch (PathNotFoundException e) {
                            diffs.add(String.format("Json path '%s' -> %s", jsonPathExpression.get(), e.getMessage()));
                        }
                    }
                    break;
                case DO_NOT_MATCH_ANY:
                    if (expected.size() - getDoNotMatchUseCases(expected) < actual.size()) {
                        diffs.add(String.format("Expected condition '%s' was not met. Actual JSON OBJECT has extra fields", expectedField));
                    }
                    break;
                case DO_NOT_MATCH:
                    if (!jsonPathExpression.isPresent()) {
                        if (!candidateEntries.isEmpty()) {
                            diffs.add(String.format("Field '%s' was FOUND", expectedField));
                        }
                    } else {
                        try {
                            new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match();
                        } catch (PathNotFoundException e) {
                            break;
                        }
                        diffs.add(String.format("Json path '%s' was FOUND", expectedField));
                    }
                    break;
            }
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE) && expected.size() - getDoNotMatchUseCases(expected) < actual.size()) {
            diffs.add("Actual JSON OBJECT has extra fields");
        }
        return diffs;
    }

    private List<String> matchWithCandidates(String expectedField, JsonNode expectedValue, List<Map.Entry<String, JsonNode>> candidates) {
        List<String> diffs = new ArrayList<>();

        UseCase expectedValueUseCase = getUseCase(expectedValue);

        for (Map.Entry<String, JsonNode> candidateEntry : candidates) {
            String candidateField = candidateEntry.getKey();

            if (expectedValueUseCase == UseCase.MATCH_ANY) {
                matchedFieldNames.add(candidateField);
                return Collections.emptyList();
            }

            JsonNode candidateValue = candidateEntry.getValue();
            List<String> candidateDiffs = new JsonMatcher(expectedValue, candidateValue, comparator, compareModes).match();
            if (candidateDiffs.isEmpty()) {
                matchedFieldNames.add(candidateField);
                return Collections.emptyList();
            } else {
                candidateDiffs.forEach(diff -> diffs.add(String.format("%s -> %s", expectedField, diff)));
            }
        }
        return diffs;
    }

    private List<Map.Entry<String, JsonNode>> searchCandidatesByField(UseCase fieldUseCase, String fieldName, JsonNode target) {
        List<Map.Entry<String, JsonNode>> candidates = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> it = target.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            if (matchedFieldNames.contains(key)) {
                continue;
            }
            if (fieldUseCase.equals(UseCase.MATCH_ANY) || comparator.compareFields(fieldName, key)) {
                candidates.add(entry);
            }
        }
        return candidates;
    }
}
