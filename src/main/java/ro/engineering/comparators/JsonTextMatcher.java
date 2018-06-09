package ro.engineering.comparators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTextMatcher {

    private JsonNode expected;
    private JsonNode actual;

    public JsonTextMatcher(JsonNode expectedJsonObject, JsonNode actualJsonObject) throws IOException {
        this.expected = expectedJsonObject;
        this.actual = actualJsonObject;
    }

    public boolean matches() throws IOException {
        Pattern pattern = Pattern.compile(expected.toString());
        Matcher matcher = pattern.matcher(actual.toString());
        return matcher.matches();
    }

    public static JsonNode parseTextJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        if (!isTextJson(jsonNode)) {
            throw new IOException(StringUtil.crop(json) + " is not a JSON TEXT");
        }
        return jsonNode;
    }

    public static boolean isTextJson(JsonNode jsonNode) {
        JsonNodeType type = jsonNode.getNodeType();
        return type.equals(JsonNodeType.STRING) || type.equals(JsonNodeType.NUMBER) ||
                type.equals(JsonNodeType.BOOLEAN) || type.equals(JsonNodeType.NULL);
    }
}
