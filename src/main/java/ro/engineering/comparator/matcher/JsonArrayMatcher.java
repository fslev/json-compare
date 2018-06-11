//package ro.engineering.comparator.matcher;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.JsonNodeType;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import static ro.engineering.comparator.matcher.JsonTextMatcher.isJsonText;
//
//public class JsonArrayMatcher extends JsonMatcher {
//
//    private Set<Integer> matchedPositions = new HashSet<Integer>();
//
//    public JsonArrayMatcher(JsonNode expected, JsonNode actual) {
//        super(expected, actual);
//    }
//
//    public void matches() throws MatcherException {
//        for (int i = 0; i < expected.size(); i++) {
//            JsonNode element = expected.get(i);
//            for (int j = 0; j < actual.size(); j++) {
//                JsonNode actElement = actual.get(j);
//
//            }
//
//
//            String sanitizedField = sanitize(field);
//            Map.Entry<String, JsonNode> candidateEntry = searchCandidateEntryByField(sanitizedField, actual);
//            if (useCase.equals(UseCase.DO_NOT_FIND) && candidateEntry == null) {
//                continue;
//            }
//            if (useCase.equals(UseCase.DO_NOT_FIND) && candidateEntry != null) {
//                throw new MatcherException("Field " + field + " was found");
//            }
//            if (useCase.equals(UseCase.FIND) && candidateEntry == null) {
//                throw new MatcherException("Field " + field + " was not found");
//            }
//            String candidateField = candidateEntry.getKey();
//            JsonNode candidateValue = candidateEntry.getValue();
//            if (isJsonObject(value) && isJsonObject(candidateValue)) {
//                new JsonArrayMatcher(value, candidateValue).matches();
//                matchedFieldNames.add(candidateField);
//            } else if (isJsonText(value) && isJsonText(candidateValue)) {
//                new JsonTextMatcher(value, candidateValue).matches();
//                matchedFieldNames.add(candidateField);
//            }
//        }
//    }
//
//    public static boolean isJsonArray(JsonNode jsonNode) {
//        if (jsonNode == null) {
//            return false;
//        }
//        return jsonNode.getNodeType().equals(JsonNodeType.ARRAY);
//    }
//}
