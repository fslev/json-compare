package io.json.compare.util;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.JsonNodeFeature;

import java.io.IOException;

/**
 * Shared JSON I/O: wraps a single immutable Jackson {@link ObjectMapper} tuned for
 * strict, precision-preserving parsing. Stream-read constraints are lifted so the
 * library can handle deeply nested or very large payloads without manual tuning.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            .configure(JsonNodeFeature.STRIP_TRAILING_BIGDECIMAL_ZEROES, false)
            .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);

    static {
        MAPPER.getFactory().setStreamReadConstraints(StreamReadConstraints.builder()
                .maxNestingDepth(Integer.MAX_VALUE)
                .maxNumberLength(Integer.MAX_VALUE)
                .maxStringLength(Integer.MAX_VALUE)
                .build());
    }

    private JsonUtils() {
    }

    /**
     * Converts any JSON-convertible object into a Jackson {@link JsonNode}.
     * Accepted inputs: {@link JsonNode} (returned as-is), JSON {@link String}
     * (parsed), or any POJO / {@link java.util.Map} / {@link java.util.List}
     * (serialized then re-read).
     */
    public static JsonNode toJson(Object obj) throws IOException {
        if (obj instanceof JsonNode) {
            return (JsonNode) obj;
        }
        if (obj instanceof String) {
            return MAPPER.readTree((String) obj);
        }
        return MAPPER.convertValue(obj, JsonNode.class);
    }

    /**
     * Pretty-prints the given content. Empty strings are returned as-is.
     */
    public static String prettyPrint(Object content) throws IOException {
        if (content instanceof String && ((String) content).isEmpty()) {
            return "";
        }
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toJson(content));
    }
}
