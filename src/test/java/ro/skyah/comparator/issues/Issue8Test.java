package ro.skyah.comparator.issues;

import org.junit.Test;
import ro.skyah.comparator.JSONCompare;

public class Issue8Test {

    @Test
    public void testJsonObjectMatchUseCase() {
        String expected = "{\"records\":[ {\"a\":0} ]}";
        String actual = "{\"records\":[ true, {\"a\":0} ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testJsonObjectMatchUseCase_negative() {
        String expected = "{\"records\":[ {\"a\":0} ]}";
        String actual = "{\"records\":[ {\"a\":1} ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testJsonArrayMatchUseCase() {
        String expected = "{\"records\":[ 1,true, \"b\", {\"a\": 0}, [2] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, [2], 1 ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testJsonArrayMatchUseCase_negative() {
        String expected = "{\"records\":[ 1,true, \"b\", {\"a\": 0}, [2] ]}";
        String actual = "{\"records\":[  \"b\", {\"b\": 0}, true, [2], 1 ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testJsonObjectDoNotMatchUseCase() {
        String expected = "{\"records\":[ {\"!a\":0} ]}";
        String actual = "{\"records\":[ true, {\"b\":0} ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testJsonObjectDoNotMatchUseCase_negative() {
        String expected = "{\"records\":[ {\"!a\":0} ]}";
        String actual = "{\"records\":[ {\"a\":1} ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testJsonArrayDoNotMatchUseCase() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, [2], 1 ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testJsonArrayDoNotMatchUseCase_negative() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, true, 1 ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testJsonArrayDoNotMatchUseCase_negative1() {
        String expected = "{\"records\":[  [\"!c\"] ]}";
        String actual = "{\"records\":[  \"b\", {\"a\": 0}, [\"c\"], true, 1 ]}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testDoNotMatchUseCaseCompareEscaped() {
        String expected = "{\"\\\\Q\\\\!records\\\\E\":0}";
        String actual = "{\"!records\":0}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void testDoNotMatchUseCaseCompareEscaped1() {
        String expected = "{\"\\\\Q\\\\!records\\\\E\":0}";
        String actual = "{\"\\\\!records\":0}";
        JSONCompare.assertEquals(expected, actual);
    }
}
