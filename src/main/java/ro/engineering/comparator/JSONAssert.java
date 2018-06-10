package ro.engineering.comparator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.engineering.comparator.matcher.JsonObjectMatcher;
import ro.engineering.comparator.matcher.MatcherException;

import java.io.IOException;
import java.util.Iterator;

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


    public static void main(String[] args) throws IOException, MatcherException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJsonNode = mapper.readTree("{\"key2\":\"!true\",\"!da\":\"fdsfds\"}");
        JsonNode actualJsonNode = mapper.readTree("{\"key2\":false,\"das\":\"43\"}");
//        System.out.println(actualJsonNode.get("key1")).get;
        new JsonObjectMatcher(expectedJsonNode, actualJsonNode).matches();

        expectedJsonNode = mapper.readTree("{\"key2\":\"!true\",\"!da\":\"fdsfds\"}");
        actualJsonNode = mapper.readTree("[1,2,\"3\",\"test\"]");
        Iterator it = actualJsonNode.elements();
        it.next();
        it.next();
        System.out.println(it.next());
    }
}
