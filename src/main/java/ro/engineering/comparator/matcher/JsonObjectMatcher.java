package ro.engineering.comparator.matcher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonObjectMatcher extends AbstractJsonMatcher {

    // The names within an object SHOULD be unique.
    private Set<String> matchedFieldNames = new HashSet<String>();

    public JsonObjectMatcher(JsonNode expected, JsonNode actual) {
        super(expected, actual);
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
            if (useCase.equals(UseCase.DO_NOT_FIND) && candidateEntry == null) {
                continue;
            }
            if (useCase.equals(UseCase.DO_NOT_FIND) && candidateEntry != null) {
                throw new MatcherException("Field " + field + " was found");
            }
            if (useCase.equals(UseCase.FIND) && candidateEntry == null) {
                throw new MatcherException("Field " + field + " was not found");
            }
            String candidateField = candidateEntry.getKey();
            JsonNode candidateValue = candidateEntry.getValue();
            try {
                new JsonMatcher(value, candidateValue).matches();
            } catch (MatcherException e) {
                throw new MatcherException(e.getMessage() + "\nwhile comparing values at field \""
                        + sanitizedField + "\"\n");
            }
            matchedFieldNames.add(candidateField);
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
            Pattern pattern = Pattern.compile(fieldName);
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
                return entry;
            }
        }
        return null;
    }
}
