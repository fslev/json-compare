package ro.skyah.comparator.matcher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JsonComparator;

import java.util.Set;

class JsonPathMatcher extends AbstractJsonMatcher {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    private static final ParseContext PARSE_CONTEXT = JsonPath.using(new Configuration.ConfigurationBuilder()
            .jsonProvider(new JacksonJsonNodeJsonProvider()).build());

    private final String jsonPath;

    JsonPathMatcher(String jsonPath, JsonNode expectedValue, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expectedValue, actual, comparator, compareModes);
        this.jsonPath = jsonPath;
    }

    @Override
    public void match() throws MatcherException {
        try {
            JsonNode result = MAPPER.convertValue(PARSE_CONTEXT.parse(actual).read(jsonPath), JsonNode.class);
            new JsonMatcher(expected, result, comparator, compareModes).match();
        } catch (MatcherException | PathNotFoundException e) {
            throw new MatcherException(String.format("%s <- json path ('%s')", e.getMessage(), jsonPath));
        }
    }
}