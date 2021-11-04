package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.PathNotFoundException;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.*;

class JsonObjectMatcher extends AbstractJsonMatcher {

    private final Set<String> matchedFieldNames = new HashSet<>();

    JsonObjectMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void match() throws MatcherException {
        Iterator<Map.Entry<String, JsonNode>> it = expected.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String expectedField = entry.getKey();
            JsonNode expectedValue = entry.getValue();
            UseCase useCase = getUseCase(expectedField);
            String expectedSanitizedField = sanitize(expectedField);
            Optional<String> jsonPathExpression = extractJsonPathExp(expectedSanitizedField);
            List<Map.Entry<String, JsonNode>> candidateEntries = null;
            if (!jsonPathExpression.isPresent()) {
                candidateEntries = searchCandidateEntriesByField(expectedSanitizedField, actual);
            }
            switch (useCase) {
                case MATCH_ANY:
                case MATCH:
                    if (!jsonPathExpression.isPresent()) {
                        if (candidateEntries.isEmpty()) {
                            throw new MatcherException(String.format("Field %s was not found or cannot be matched", expectedField));
                        }
                        matchWithCandidateEntries(expectedSanitizedField, expectedValue, candidateEntries);
                    } else {
                        try {
                            new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match();
                        } catch (PathNotFoundException e) {
                            throw new MatcherException(String.format("%s <- json path ('%s')", e.getMessage(), jsonPathExpression.get()));
                        }
                    }
                    break;
                case DO_NOT_MATCH_ANY:
                case DO_NOT_MATCH:
                    if (!jsonPathExpression.isPresent()) {
                        if (!candidateEntries.isEmpty()) {
                            throw new MatcherException(String.format("Field %s was found", expectedField));
                        }
                    } else {
                        try {
                            new JsonPathMatcher(jsonPathExpression.get(), expectedValue, actual, comparator, compareModes).match();
                        } catch (PathNotFoundException e) {
                            break;
                        }
                        throw new MatcherException(String.format("Json path '%s' was found", expectedField));
                    }
                    break;
            }
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE) && expected.size() < actual.size()) {
            throw new MatcherException("Actual JSON OBJECT has extra fields");
        }
    }

    private void matchWithCandidateEntries(String expectedKey, JsonNode expectedValue, List<Map.Entry<String, JsonNode>> candidates) throws MatcherException {
        UseCase expectedValueUseCase = getUseCase(expectedValue);
        for (ListIterator<Map.Entry<String, JsonNode>> it = candidates.listIterator(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> candidateEntry = it.next();
            String candidateField = candidateEntry.getKey();
            if (expectedValueUseCase == UseCase.MATCH_ANY) {
                matchedFieldNames.add(candidateField);
                break;
            }
            JsonNode candidateValue = candidateEntry.getValue();
            try {
                new JsonMatcher(expectedValue, candidateValue, comparator, compareModes).match();
            } catch (MatcherException e) {
                if (it.hasNext()) {
                    continue;
                } else {
                    throw new MatcherException(String.format("%s <- \"%s\"", e.getMessage(), expectedKey));
                }
            }
            matchedFieldNames.add(candidateField);
            break;
        }
    }

    private List<Map.Entry<String, JsonNode>> searchCandidateEntriesByField(String fieldName, JsonNode target) {
        List<Map.Entry<String, JsonNode>> candidatesList = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> it = target.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            if (matchedFieldNames.contains(key)) {
                continue;
            }
            if (comparator.compareFields(fieldName, key)) {
                candidatesList.add(entry);
            }
        }
        return candidatesList;
    }
}