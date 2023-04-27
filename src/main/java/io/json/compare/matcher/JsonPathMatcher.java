package io.json.compare.matcher;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class JsonPathMatcher extends AbstractJsonMatcher {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    static {
        MAPPER.getFactory().setStreamReadConstraints(StreamReadConstraints.builder()
                .maxNestingDepth(Integer.MAX_VALUE).maxNumberLength(Integer.MAX_VALUE).maxStringLength(Integer.MAX_VALUE).build());
    }

    private static final ParseContext PARSE_CONTEXT = JsonPath.using(new Configuration.ConfigurationBuilder()
            .jsonProvider(new JacksonJsonNodeJsonProvider()).build());

    private final String jsonPath;

    JsonPathMatcher(String jsonPath, JsonNode expectedValue, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expectedValue, actual, comparator, compareModes);
        this.jsonPath = jsonPath;
    }

    @Override
    public List<String> match() {
        List<String> diffs = new ArrayList<>();
        JsonNode result = MAPPER.convertValue(PARSE_CONTEXT.parse(actual).read(jsonPath), JsonNode.class);
        List<String> jsonPathDiffs = new JsonMatcher(expected, result, comparator, compareModes).match();
        jsonPathDiffs.forEach(diff -> diffs.add(String.format("Json path '%s' -> Expected json path result:" +
                        System.lineSeparator() + "%s" + System.lineSeparator() + "But got:" +
                        System.lineSeparator() + "%s" + System.lineSeparator() + "________diffs________" + System.lineSeparator() + "%s",
                jsonPath, expected, result, diff)));
        return diffs;
    }
}