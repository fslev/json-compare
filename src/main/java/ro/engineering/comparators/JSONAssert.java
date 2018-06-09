package ro.engineering.comparators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONAssert {

    public static void compare(String expectedJson, String actualJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode expectedJsonNode = mapper.readTree(expectedJson);
            JsonNode actualJsonNode = mapper.readTree(actualJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJsonNode = mapper.readTree("{\"key1\":\"val1\",\".*\":\".*\"}");
        JsonNode actualJsonNode = mapper.readTree("{\"key1\":\"val1\",\"key2\":\"val2\"}");
        System.out.println(new JsonObjectMatcher(expectedJsonNode, actualJsonNode).matches());
    }
}
