package io.json.compare.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper().setNodeFactory(JsonNodeFactory.withExactBigDecimals(true))
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    public static JsonNode toJson(Object obj) throws IOException {
        return obj instanceof JsonNode ? (JsonNode) obj :
                (obj instanceof String) ? MAPPER.readTree(obj.toString()) : MAPPER.convertValue(obj, JsonNode.class);
    }

    public static String prettyPrint(Object content) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toJson(content));
    }
}
