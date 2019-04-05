package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;

import java.util.*;

public class JsonObjectMatcher extends AbstractJsonMatcher {

    // The key names within a JSON OBJECT SHOULD be unique.
    private Set<String> matchedFieldNames = new HashSet<>();

    public JsonObjectMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
    }

    @Override
    public void matches() throws MatcherException {
        Iterator<Map.Entry<String, JsonNode>> it = expected.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String field = entry.getKey();
            JsonNode expectedValue = entry.getValue();
            UseCase fieldUseCase = getUseCase(field);
            String sanitizedField = sanitize(field);
            List<Map.Entry<String, JsonNode>> candidateEntries =
                    searchCandidateEntriesByField(sanitizedField, actual);

            if (fieldUseCase.equals(UseCase.DO_NOT_MATCH)) {
                if (!candidateEntries.isEmpty()) {
                    throw new MatcherException(String.format("Field %s was found", field));
                }
                matchWithAllActualEntryValues("Found no match value for key: " + field, expectedValue, actual);
            } else {
                if (candidateEntries.isEmpty()) {
                    throw new MatcherException(String.format("Field %s was not found", field));
                }
                matchWithCandidateEntries(sanitizedField, expectedValue, candidateEntries);
            }
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
                && expected.size() < actual.size()) {
            throw new MatcherException("Actual JSON OBJECT has extra fields");
        }
    }

    private void matchWithCandidateEntries(String expKey, JsonNode expValue, List<Map.Entry<String, JsonNode>> candidates) throws MatcherException {
        for (ListIterator<Map.Entry<String, JsonNode>> it1 = candidates.listIterator(); it1.hasNext(); ) {
            Map.Entry<String, JsonNode> candidateEntry = it1.next();
            String candidateField = candidateEntry.getKey();
            JsonNode candidateValue = candidateEntry.getValue();
            try {
                new JsonMatcher(expValue, candidateValue).matches();
            } catch (MatcherException e) {
                if (it1.hasNext()) {
                    continue;
                } else {
                    throw new MatcherException(
                            String.format("%s <- field \"%s\"", e.getMessage(), expKey));
                }
            }
            matchedFieldNames.add(candidateField);
            break;
        }
    }

    private void matchWithAllActualEntryValues(String message, JsonNode expValue, JsonNode actual) throws MatcherException {
        Iterator<Map.Entry<String, JsonNode>> it = actual.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> actualEntry = it.next();
            if (matchedFieldNames.contains(actualEntry.getKey())) {
                continue;
            }
            try {
                new JsonMatcher(expValue, actualEntry.getValue()).matches();
            } catch (MatcherException e) {
                if (it.hasNext()) {
                    continue;
                } else {
                    throw new MatcherException(message);
                }
            }
            matchedFieldNames.add(actualEntry.getKey());
            break;
        }
    }

    private List<Map.Entry<String, JsonNode>> searchCandidateEntriesByField(String fieldName,
                                                                            JsonNode target) {
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
