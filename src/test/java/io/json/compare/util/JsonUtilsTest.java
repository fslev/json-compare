package io.json.compare.util;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilsTest {

    @Test
    public void convertStringToJson() throws IOException {
        assertEquals(2, JsonUtils.toJson("{\"a\":2}").get("a").asInt());
    }

    @Test
    public void convertObjectToJson() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 2);
        assertEquals(2, JsonUtils.toJson(map).get("a").asInt());
    }

    @Test
    public void testBigJsonPrettyPrint() throws IOException {
        String json = readFromPath("bigJsons/actualLargeJson.json");
        assertNotNull(JsonUtils.prettyPrint(json));
    }

    @Test
    public void testEmptyJsonPrettyPrint() throws IOException {
        assertEquals("{\n" +
                "  \"foo\" : \"bar\"\n" +
                "}", JsonUtils.prettyPrint("{\"foo\":\"bar\"}"));
        assertEquals("{ }", JsonUtils.prettyPrint("{}"));
        assertEquals("[ ]", JsonUtils.prettyPrint("[]"));
        assertEquals("null", JsonUtils.prettyPrint("null"));
        assertEquals("null", JsonUtils.prettyPrint(null));
        assertThrows(JsonParseException.class, () -> JsonUtils.prettyPrint("invalid"));
        assertThrows(JsonParseException.class, () -> JsonUtils.prettyPrint("{invalid}"));
        assertThrows(JsonParseException.class, () -> JsonUtils.prettyPrint("{\"a\":0},1"));
        assertEquals("", JsonUtils.prettyPrint(""));
    }

    private static String readFromPath(String filePath) throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        }
    }
}
