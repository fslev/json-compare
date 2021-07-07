package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.*;

public class JsonObjectMatcher extends AbstractJsonMatcher {

    // The key names within a JSON OBJECT SHOULD be unique.
    private final Set<String> matchedFieldNames = new HashSet<>();

    public JsonObjectMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator,
                             Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
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
            switch (fieldUseCase) {
                case MATCH_ANY:
                case MATCH:
                    if (candidateEntries.isEmpty()) {
                        throw new MatcherException(String.format("Field %s was not found or cannot be matched", field));
                    }
                    matchWithCandidateEntries(sanitizedField, expectedValue, candidateEntries);
                    break;
                case DO_NOT_MATCH_ANY:
                case DO_NOT_MATCH:
                    if (!candidateEntries.isEmpty()) {
                        throw new MatcherException(String.format("Field %s was found", field));
                    }
                    break;
            }
        }
        if (compareModes.contains(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
                && expected.size() < actual.size()) {
            throw new MatcherException("Actual JSON OBJECT has extra fields");
        }
    }

    private void matchWithCandidateEntries(String expKey, JsonNode expValue, List<Map.Entry<String, JsonNode>> candidates) throws MatcherException {
        UseCase expValueUseCase = getUseCase(expValue);
        for (ListIterator<Map.Entry<String, JsonNode>> it1 = candidates.listIterator(); it1.hasNext(); ) {
            Map.Entry<String, JsonNode> candidateEntry = it1.next();
            String candidateField = candidateEntry.getKey();
            if (expValueUseCase == UseCase.MATCH_ANY) {
                matchedFieldNames.add(candidateField);
                break;
            }
            JsonNode candidateValue = candidateEntry.getValue();
            try {
                new JsonMatcher(expValue, candidateValue, comparator, compareModes).matches();
            } catch (MatcherException e) {
                if (it1.hasNext()) {
                    continue;
                } else {
                    throw new MatcherException(
                            String.format("%s <- \"%s\"", e.getMessage(), expKey));
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
