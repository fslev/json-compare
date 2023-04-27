package io.json.compare.util;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.JsonNodeFeature;

import java.io.IOException;

public class JsonUtils {

    private JsonUtils() {

    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            .configure(JsonNodeFeature.STRIP_TRAILING_BIGDECIMAL_ZEROES, false)
            .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);

    static {
        MAPPER.getFactory().setStreamReadConstraints(StreamReadConstraints.builder()
                .maxNestingDepth(Integer.MAX_VALUE).maxNumberLength(Integer.MAX_VALUE).maxStringLength(Integer.MAX_VALUE).build());
    }

    public static JsonNode toJson(Object obj) throws IOException {
        return obj instanceof JsonNode ? (JsonNode) obj :
                (obj instanceof String) ? MAPPER.readTree(obj.toString()) : MAPPER.convertValue(obj, JsonNode.class);
    }

    public static String prettyPrint(Object content) throws IOException {
        if (content instanceof String && content.toString().isEmpty()) {
            return "";
        }
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toJson(content));
    }
}
