package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class Issue12Test {

    @Test
    public void testNegation() {
        String expected = "{\n" +
                "    \"domainConnect\": {\n" +
                "      \"ionos.com\": {\n" +
                "        \"temporaryRedirectWithwww\": {\n" +
                "          \"@\": {\n" +
                "            \"startDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\",\n" +
                "            \"endDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\"\n" +
                "          },\n" +
                "          \"host\": {\n" +
                "            \"startDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\",\n" +
                "            \"!endDate\": \".*\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"frameRedirectWithwww\": {\n" +
                "          \"@\": {\n" +
                "            \"startDate\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z\",\n" +
                "            \"!endDate\": \".*\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        String actual = "{\n" +
                "  \"domainConnect\" : {\n" +
                "    \"ionos.com\" : {\n" +
                "      \"temporaryRedirectWithwww\" : {\n" +
                "        \"@\" : {\n" +
                "          \"startDate\" : \"2020-04-21T09:56:18.960Z\",\n" +
                "          \"endDate\" : \"2020-04-21T09:56:23.609Z\"\n" +
                "        },\n" +
                "        \"host\" : {\n" +
                "          \"startDate\" : \"2020-04-21T09:56:18.960Z\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"frameRedirectWithwww\" : {\n" +
                "        \"@\" : {\n" +
                "          \"startDate\" : \"2020-04-21T09:56:23.576Z\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        JSONCompare.assertMatches(expected, actual);
    }
}
