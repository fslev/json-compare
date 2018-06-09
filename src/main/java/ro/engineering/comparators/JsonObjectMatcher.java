package ro.engineering.comparators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ro.engineering.comparators.JsonTextMatcher.isTextJson;

public class JsonObjectMatcher {

    private JsonNode expected;
    private JsonNode actual;
    private Set<String> matchedFieldNames = new HashSet<String>();

    public JsonObjectMatcher(JsonNode expectedJsonObject, JsonNode actualJsonObject) throws IOException {
        this.expected = expectedJsonObject;
        this.actual = actualJsonObject;
    }

    public boolean matches() throws IOException {
        Iterator<Map.Entry<String, JsonNode>> it = expected.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String field = entry.getKey();
            JsonNode value = entry.getValue();
            JsonNode candidate = searchCandidateByField(field, actual);
            if (candidate == null) {
                return false;
            }
            if (isJsonObject(value) && isJsonObject(candidate)) {
                if (!new JsonObjectMatcher(value, candidate).matches()) {
                    return false;
                }
                matchedFieldNames.add(field);
                continue;
            }
            if (isTextJson(value) && isTextJson(candidate)) {
                if (!new JsonTextMatcher(value, candidate).matches()) {
                    return false;
                }
                matchedFieldNames.add(field);
                continue;
            }

        }
        return true;
    }

    private JsonNode searchCandidateByField(String fieldName, JsonNode target) {
        if (matchedFieldNames.contains(fieldName)) {
            return null;
        }

        Iterator<Map.Entry<String, JsonNode>> it = target.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            Pattern pattern = Pattern.compile(fieldName);
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static JsonNode parseJsonObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        if (!isJsonObject(jsonNode)) {
            throw new IOException(StringUtil.crop(json) + " is not a JSON OBJECT");
        }
        return jsonNode;
    }

    public static boolean isJsonObject(JsonNode jsonNode) {
        return jsonNode.getNodeType().equals(JsonNodeType.OBJECT) ? true : false;
    }
}
