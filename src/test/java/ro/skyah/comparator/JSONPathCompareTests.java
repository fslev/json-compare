package ro.skyah.comparator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JSONPathCompareTests {

    @Test
    public void compareJsonObjectsWithEscapedJsonPath() {
        String expected = "{\"\\\\#\\\\Q(\\\\Elorem ipsum\\\\Q)\\\\E\":\"val1\"}";
        String actual = "{\"b\":\"val2\",\"#(lorem ipsum)\":\"val1\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsWithEscapedJsonPath_negative() {
        String expected = "{\"\\\\#\\\\Q(\\\\Elorem ipsum\\\\Q)\\\\E\":\"val1\"}";
        String actual = "{\"b\":\"val2\",\"#(lorem ipsum2)\":\"val1\"}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));
    }

    @Test
    public void compareJsonObjectsWithJsonPathPointingToValueNode() {
        String expected = "{\"#($.a)\":\"val1\"}";
        String actual = "{\"b\":\"val2\",\"a\":\"val1\"}";
        JSONCompare.assertEquals(expected, actual);
        expected = "{\"#($.a)\":null}";
        actual = "{\"b\":\"val2\",\"a\":null}";
        JSONCompare.assertEquals(expected, actual);
        expected = "{\"#($.a)\":190.23}";
        actual = "{\"b\":\"val2\",\"a\":190.23}";
        JSONCompare.assertEquals(expected, actual);
        expected = "{\"#($.a)\":false}";
        actual = "{\"b\":\"val2\",\"a\":false}";
        JSONCompare.assertEquals(expected, actual);
        expected = "{\"#($.a.length())\":2}";
        actual = "{\"b\":\"val2\",\"a\":[4,5]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsWithJsonPathPointingToValueNode_negative() {
        String expected = "{\"#($.a)\":\"val1\"}";
        String actual = "{\"b\":\"val2\",\"a\":\"val2\"}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));
        String expected1 = "{\"#($.a)\":null}";
        String actual1 = "{\"b\":\"val2\",\"a\":\"test\"}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected1, actual1));
        String expected2 = "{\"#($.a)\":190.23}";
        String actual2 = "{\"b\":\"val2\",\"a\":191.23}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected2, actual2));
        String expected3 = "{\"#($.a)\":true}";
        String actual3 = "{\"b\":\"val2\",\"a\":false}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected3, actual3));
        String expected4 = "{\"#($.a.length())\":1}";
        String actual4 = "{\"b\":\"val2\",\"a\":[4,5]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected4, actual4));
    }

    @Test
    public void compareJsonObjectWithJsonPathAgainstOtherJsonType() {
        String expected = "{\"#($.length())\":4}";
        String actual = "[5,true,null,\"lorem ipsum\"]";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($.length())\":1}";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "{\"#($[2])\":{\"a1\":[1,false,3,{\"a11\":\"lorem\"}]}}";
        actual = "[5,true,{\"a1\":[1,false,3,{\"a11\":\"lorem\"}]},\"lorem ipsum\"]";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($[2].a1[3])\":{\"a11\":\"lorem\"}}";
        actual = "[5,true,{\"a1\":[1,false,3,{\"a11\":\"lorem\"}]},\"lorem ipsum\"]";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($[2].a1[3])\":{\"a12\":\"lorem\"}}";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "{\"#($)\":\"test\"}";
        actual = "\"test\"";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($)\":290.87}";
        actual = "290.87";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectWithJsonPathAgainstOtherJsonType_negative() {
        String expected = "{\"#($.length())\":1}";
        String actual = "[5,true,null,\"lorem ipsum\"]";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));

        String expected1 = "{\"#($)\":\"test2\"}";
        String actual1 = "\"test\"";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected1, actual1));

        String expected2 = "{\"#($.length())\":4}";
        String actual2 = "\"test\"";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected2, actual2));
    }

    @Test
    public void compareJsonObjectsWithRelativeJsonPath() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"lorem\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareJsonObjectsWithRelativeJsonPath_negative() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":true}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));
    }


    @Test
    public void checkInvalidOrNotFoundJsonPathErrorMessage() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.idontexist)\":\"lorem\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        try {
            JSONCompare.assertEquals(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("No results for path: $['idontexist'] <- json path ('$.idontexist') <- \"a11\" <- \"a1\" <- \"a\""));
            return;
        }
        fail("No error thrown");
    }

    @Test
    public void checkJsonPathAssertionErrorMessage() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"lorem1\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        try {
            JSONCompare.assertEquals(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("But found: \"lorem\"  <- json path ('$.a') <- \"a11\" <- \"a1\" <- \"a\""));
            return;
        }
        fail("No error thrown");
    }

    @Test
    public void matchJsonObjectWithJsonPath_do_not_match_use_case() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"!lorem1\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"#($.a11)\":{\"!b\":\"lorem\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"#($.a11)\":{\"!a\":\"lorem\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"a11\":{\"!#($.x)\":\"lorem1\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem1\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"a11\":{\"!#($.a)\":\"does not matter\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem1\"}}}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void matchJsonObjectWithJsonPath_do_not_match_use_case_negative() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"!lorem1\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem1\"}}}}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));
    }

    @Test
    public void matchJsonObjectWithJsonPathAndRegex() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\".*\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"lo.*m\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"#($.a11)\":{\"!b\":\".*\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"#($.a11)\":{\"!.*\":\".*\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void matchJsonObjectWithJsonPathAndRegex_negative() {
        final String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"lol.*\"}}}}";
        final String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));
    }

    @Test
    public void matchJsonObjectWithJsonPathAndCompareModes() {
        String expected = "{\"b\":false,\"a\":{\"#($.a1)\":{\"b11\":null,\"a11\":{\"a\":\".*\"}},\"a2\":290.11}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE);

        expected = "{\"b\":false,\"a\":{\"#($.a1)\":{\"a11\":{\"a\":\".*\"}},\"a2\":290.11}}";
        JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE);
    }

    @Test
    public void matchJsonArrayWithJsonPath() {
        String expected = "[false,{\"#($.length())\":3},1]";
        String actual = "[1,[false,245.2,null,\"test\"],false]";
        JSONCompare.assertEquals(expected, actual);
        expected = "[false,{\"#($.length())\":3},2]";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "[false,{\"#($[1].x.length())\":4}]";
        actual = "[1,{\"x\":[false,245.2,null,\"test\"]},false]";
        JSONCompare.assertEquals(expected, actual);

        expected = "[false,{\"#($[1].x.length())\":3}]";
        JSONCompare.assertNotEquals(expected, actual);
        expected = "[false,{\"#($[0].x.length())\":4}]";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "[false,{\"x\":[{\"#($.length())\":4}]}]";
        actual = "[1,{\"x\":[false,245.2,null,\"test\"]},false]";
        JSONCompare.assertEquals(expected, actual);

        expected = "[true,{\"x\":[{\"#($.length())\":4}]}]";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void matchJsonObjectWithMultipleJsonPaths() {
        String expected = "{\"#($.b)\":false,\"a\":{\"#($.a1)\":{\"b11\":null,\"a11\":{\"a\":\".*\"}},\"#($.a2)\":290.11}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($.b)\":false,\"a\":{\"#($.a1)\":{\"b11\":null,\"a11\":{\"a\":\".*\"}},\"#($.a0)\":290.11}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertNotEquals(expected, actual);

        expected = "{\"#($.b)\":false,\"a\":{\"#($.a1)\":{\"b11\":null,\"a11\":{\"a\":\"not found\"}},\"#($.a2)\":290.11}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void matchJsonObjectWithDifferentJsonPaths() {
        String expected = "{\"#($.store..isbn)\":[\"0-395-19395-8\",\"0-553-21311-3\",\"!.*\"]}";
        String actual = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"#($.store..isbn)\":[\"0-395-19395-8\",\"0-553-21311-3\"]}";
        JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_ARRAY_STRICT_ORDER);


        expected = "{\"#($..book[?(@.price <= $['expensive'])])\":[" +
                "{\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "{\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"S.*e Century\",\n" +
                "                \"price\": 8.95\n" +
                "            }\n" +
                "]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void matchJsonObjectWithDifferentJsonPaths_negative() {
        final String expected = "{\"#($.store..z)\":[\"0-395-19395-8\",\"0-553-21311-3\",\"!.*\"]}";
        final String actual = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected, actual));

        final String expected1 = "{\"#($..book[?(@.price <= $['expensive'])])\":[" +
                "{\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "!.*," +
                "]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertEquals(expected1, actual));
    }
}
