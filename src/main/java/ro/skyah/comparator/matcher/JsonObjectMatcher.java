package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonObjectMatcher extends AbstractJsonMatcher {

    // The names within an object SHOULD be unique.
    private Set<String> matchedFieldNames = new HashSet<String>();

    public JsonObjectMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public void matches() throws MatcherException {
        Iterator<Map.Entry<String, JsonNode>> it = expected.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String field = entry.getKey();
            JsonNode value = entry.getValue();
            UseCase useCase = getUseCase(field);
            String sanitizedField = sanitize(field);
            Map.Entry<String, JsonNode> candidateEntry =
                    searchCandidateEntryByField(sanitizedField, actual);
            if (useCase.equals(UseCase.DO_NOT_MATCH) && candidateEntry == null) {
                continue;
            }
            if (useCase.equals(UseCase.DO_NOT_MATCH) && candidateEntry != null) {
                throw new MatcherException(String.format("Field %s was found", field));
            }
            if (useCase.equals(UseCase.MATCH) && candidateEntry == null) {
                throw new MatcherException(String.format("Field %s was not found", field));
            }
            String candidateField = candidateEntry.getKey();
            JsonNode candidateValue = candidateEntry.getValue();
            try {
                new JsonMatcher(value, candidateValue, comparator, compareModes).matches();
            } catch (MatcherException e) {
                throw new MatcherException(
                        String.format("%s <- field \"%s\"", e.getMessage(), sanitizedField));
            }
            matchedFieldNames.add(candidateField);
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
                && expected.size() < actual.size()) {
            throw new MatcherException("Actual JSON OBJECT has extra fields");
        }
    }

    private Map.Entry<String, JsonNode> searchCandidateEntryByField(String fieldName,
                                                                    JsonNode target) {
        Iterator<Map.Entry<String, JsonNode>> it = target.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            if (matchedFieldNames.contains(key)) {
                continue;
            }
            if (comparator.compareFields(fieldName, key)) {
                return entry;
            }
        }
        return null;
    }
}
