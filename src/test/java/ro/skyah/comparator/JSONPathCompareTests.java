package ro.skyah.comparator;

import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test(expected = AssertionError.class)
    public void compareJsonObjectsWithRelativeJsonPath_negative() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":true}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);
    }


    @Test
    public void checkInvalidOrNotFoundJsonPathErrorMessage() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.idontexist)\":\"lorem\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        try {
            JSONCompare.assertEquals(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("<- json path ('$.idontexist') <- \"a11\" <- \"a1\" <- \"a\""));
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
    public void matchJsonObjectstWithJsonPath_do_not_match_use_case() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"!lorem1\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);

        expected = "{\"a\":{\"a1\":{\"#($.a11)\":{\"!b\":\"lorem\"}}}}";
        actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem\"}}}}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void matchJsonObjectstWithJsonPath_do_not_match_use_case_negative() {
        String expected = "{\"a\":{\"a1\":{\"a11\":{\"#($.a)\":\"!lorem1\"}}}}";
        String actual = "{\"b\":false,\"a\":{\"a2\":290.11,\"a1\":{\"b11\":null,\"a11\":{\"a\":\"lorem1\"}}}}";
        JSONCompare.assertEquals(expected, actual);
    }
}
