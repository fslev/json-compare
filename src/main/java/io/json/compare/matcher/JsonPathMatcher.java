package io.json.compare.matcher;

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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ParseContext PARSE_CONTEXT = JsonPath.using(new Configuration.ConfigurationBuilder()
            .jsonProvider(new JacksonJsonNodeJsonProvider()).build());

    private final String jsonPath;

    JsonPathMatcher(String jsonPath, JsonNode expectedValue, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expectedValue, actual, comparator, compareModes);
        this.jsonPath = jsonPath;
    }

    @Override
    public List<String> match() {
        JsonNode result = MAPPER.convertValue(PARSE_CONTEXT.parse(actual).read(jsonPath), JsonNode.class);
        List<String> jsonPathDiffs = new JsonMatcher(expected, result, comparator, compareModes).match();
        List<String> diffs = new ArrayList<>(jsonPathDiffs.size());
        for (String diff : jsonPathDiffs) {
            diffs.add("." + UseCase.JSON_PATH_EXP_PREFIX + jsonPath + UseCase.JSON_PATH_EXP_SUFFIX + diff
                    + LS + "Expected json path result:" + LS + expected
                    + LS + "But got:" + LS + result + LS);
        }
        return diffs;
    }
}
