package ro.engineering.comparator;

import org.junit.Test;

public class JSONMixedCompareTests {

    @Test
    public void compareSimple() {
        String expected = "" +
                "{\n" +
                "  \"a\": \"val1\",\n" +
                "  \"b\": \"val2\",\n" +
                "  \"c\": [\n" +
                "    1,\n" +
                "    null,\n" +
                "    true,\n" +
                "    \"text\",\n" +
                "    {\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"g\": \"\"\n" +
                "}" +
                "";
        String actual = "" +
                "{\n" +
                "  \"b\": \"val2\",\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"c\": [\n" +
                "    \"text\",\n" +
                "    null,\n" +
                "    true,\n" +
                "    1,\n" +
                "    {\n" +
                "      \"c2\": \"some other text\",\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"a\": \"val1\",\n" +
                "  \"g\": \"\",\n" +
                "  \"h\": \"text again\"\n" +
                "}" +
                "";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareSimple_negative() {
        String expected = "" +
                "{\n" +
                "  \"a\": \"val1\",\n" +
                "  \"b\": \"val2\",\n" +
                "  \"c\": [\n" +
                "    1,\n" +
                "    null,\n" +
                "    true,\n" +
                "    \"text\",\n" +
                "    {\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"g\": \"\"\n" +
                "}" +
                "";
        String actual = "" +
                "{\n" +
                "  \"b\": \"val2\",\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"c\": [\n" +
                "    \"text\",\n" +
                "    null,\n" +
                "    true,\n" +
                "    1,\n" +
                "    {\n" +
                "      \"c2\": \"some other text\",\n" +
                "      \"c1\": \"DIFFERS HERE\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"a\": \"val1\",\n" +
                "  \"g\": \"\",\n" +
                "  \"h\": \"text again\"\n" +
                "}" +
                "";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareSimpleViaDoNotFindUseCase() {
        String expected = "" +
                "{\n" +
                "  \"a\": \"val1\",\n" +
                "  \"b\": \"val2\",\n" +
                "  \"c\": [\n" +
                "    1,\n" +
                "    null,\n" +
                "    true,\n" +
                "    \"text\",\n" +
                "    {\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"!field\": \"DON'T CARE ABOUT THE VALUE HERE\"\n" +
                "}" +
                "";
        String actual = "" +
                "{\n" +
                "  \"b\": \"val2\",\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"c\": [\n" +
                "    \"text\",\n" +
                "    null,\n" +
                "    true,\n" +
                "    1,\n" +
                "    {\n" +
                "      \"c2\": \"some other text\",\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"a\": \"val1\",\n" +
                "  \"g\": \"\",\n" +
                "  \"h\": \"text again\"\n" +
                "}" +
                "";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareSimpleViaDoNotFindUseCase_negative() {
        String expected = "" +
                "{\n" +
                "  \"a\": \"val1\",\n" +
                "  \"b\": \"val2\",\n" +
                "  \"c\": [\n" +
                "    1,\n" +
                "    null,\n" +
                "    true,\n" +
                "    \"text\",\n" +
                "    {\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"!h\": \"DON'T CARE ABOUT THE VALUE HERE\"\n" +
                "}" +
                "";
        String actual = "" +
                "{\n" +
                "  \"b\": \"val2\",\n" +
                "  \"d\": true,\n" +
                "  \"e\": 1024,\n" +
                "  \"f\": null,\n" +
                "  \"c\": [\n" +
                "    \"text\",\n" +
                "    null,\n" +
                "    true,\n" +
                "    1,\n" +
                "    {\n" +
                "      \"c2\": \"some other text\",\n" +
                "      \"c1\": \"some text\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"a\": \"val1\",\n" +
                "  \"g\": \"\",\n" +
                "  \"h\": \"text again\"\n" +
                "}" +
                "";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
