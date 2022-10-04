package io.json.compare.matcher.issues;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Issue11Test {

    @Test
    public void testSimpleJsonArrayStrictOrderWithRegexFieldsThrowsCorrectMessage() {
        String expected = "{\".*\":{\"eventLogs\":[{\"id\":2},{\"id\":4},{\"id\":1},{\"id\":3}]}}";
        String actual = "{\"_embedded\":{\"eventLogs\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("elements differ at position"));
        }
    }

    @Test
    public void testJsonArrayStrictOrderThrowsCorrectMessage() {
        String expected = "{\"_embedded\":{\"eventLogs\":[{\"id\":2},{\"id\":4},{\"id\":1},{\"id\":3}]}}";
        String actual = "{\n" +
                "  \"_embedded\": {\n" +
                "    \"eventLogs\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"session_id\": \"session id 1\",\n" +
                "        \"date\": \"2019-10-01T10:40:16.000+0000\",\n" +
                "        \"entity\": \"USER\",\n" +
                "        \"action\": \"CREATE\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 1\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 1\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/1\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/1\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"session_id\": \"session id 2\",\n" +
                "        \"date\": \"2019-10-01T10:40:16.000+0000\",\n" +
                "        \"entity\": \"GROUP\",\n" +
                "        \"action\": \"UPDATE\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 2\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 2\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/2\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/2\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"session_id\": \"session id 2\",\n" +
                "        \"date\": \"2019-10-02T10:40:16.000+0000\",\n" +
                "        \"entity\": \"USER\",\n" +
                "        \"action\": \"REMOVE\",\n" +
                "        \"data\": {\n" +
                "          \"some other data for session id\": 2\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some other for session 2\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/3\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/3\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 4,\n" +
                "        \"session_id\": \"session id 3\",\n" +
                "        \"date\": \"2019-10-03T10:40:16.000+0000\",\n" +
                "        \"entity\": \"GROUP\",\n" +
                "        \"action\": \"ADD_TO_GROUP\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 3\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 3\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/4\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/4\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"_links\": {\n" +
                "    \"self\": {\n" +
                "      \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/search/by?start=2019-10-01T00%3A00%3A00.000-00%3A00&end=2019-10-05T23%3A59%3A59.000-00%3A00&page=0&size=20&sort=date,asc\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"page\": {\n" +
                "    \"size\": 20,\n" +
                "    \"totalElements\": 4,\n" +
                "    \"totalPages\": 1,\n" +
                "    \"number\": 0\n" +
                "  }\n" +
                "}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("elements differ at position"));
        }
    }

    @Test
    public void testJsonArrayStrictOrderWithRegexFieldsThrowsCorrectMessage() {
        String expected = "{\".*\":{\"eventLogs\":[{\"id\":2},{\"id\":4},{\"id\":1},{\"id\":3}]}}";
        String actual = "{\n" +
                "  \"_embedded\": {\n" +
                "    \"eventLogs\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"session_id\": \"session id 1\",\n" +
                "        \"date\": \"2019-10-01T10:40:16.000+0000\",\n" +
                "        \"entity\": \"USER\",\n" +
                "        \"action\": \"CREATE\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 1\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 1\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/1\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/1\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"session_id\": \"session id 2\",\n" +
                "        \"date\": \"2019-10-01T10:40:16.000+0000\",\n" +
                "        \"entity\": \"GROUP\",\n" +
                "        \"action\": \"UPDATE\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 2\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 2\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/2\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/2\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"session_id\": \"session id 2\",\n" +
                "        \"date\": \"2019-10-02T10:40:16.000+0000\",\n" +
                "        \"entity\": \"USER\",\n" +
                "        \"action\": \"REMOVE\",\n" +
                "        \"data\": {\n" +
                "          \"some other data for session id\": 2\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some other for session 2\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/3\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/3\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 4,\n" +
                "        \"session_id\": \"session id 3\",\n" +
                "        \"date\": \"2019-10-03T10:40:16.000+0000\",\n" +
                "        \"entity\": \"GROUP\",\n" +
                "        \"action\": \"ADD_TO_GROUP\",\n" +
                "        \"data\": {\n" +
                "          \"some data for session id\": 3\n" +
                "        },\n" +
                "        \"outcome\": \"SUCCESS\",\n" +
                "        \"message\": \"some message for session 3\",\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/4\"\n" +
                "          },\n" +
                "          \"eventLog\": {\n" +
                "            \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/4\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"_links\": {\n" +
                "    \"self\": {\n" +
                "      \"href\": \"http://clouddashboardqa01.ops.server.lan:38351/event/search/by?start=2019-10-01T00%3A00%3A00.000-00%3A00&end=2019-10-05T23%3A59%3A59.000-00%3A00&page=0&size=20&sort=date,asc\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"page\": {\n" +
                "    \"size\": 20,\n" +
                "    \"totalElements\": 4,\n" +
                "    \"totalPages\": 1,\n" +
                "    \"number\": 0\n" +
                "  }\n" +
                "}";
        try {
            JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("was NOT FOUND"));
        }
    }

}
