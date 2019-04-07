package ro.skyah.comparator;

import org.junit.Test;

public class JSONObjectCompareTests {

    @Test
    public void compareSimple() {
        String expected = "{\"b\":\"\\\\Q(⌐■_■)\\\\E\",\"a\":\"val2\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"(⌐■_■)\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareByFieldNotFound() {
        String expected = "{\"!b\":\"val1\",\"a\":\"val2\"}";
        String actual = "{\"a\":\"val2\",\"c\":\"val1\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareByFieldNotFound_Negative() {
        String expected = "{\"!b\":\"val1\",\"a\":\"val2\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithNumbers() {
        String expected = "{\"b\":1,\"a\":2}";
        String actual = "{\"a\":2,\"b\":1}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithExpectedStringRepresentedNumbers() {
        String expected = "{\"b\":1,\"a\":\"2\"}";
        String actual = "{\"a\":2,\"b\":1}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithFloatNumbers() {
        String expected = "{\"b\":-10.54325,\"a\":10.429318549148632}";
        String actual = "{\"a\":10.429318549148632,\"b\":-10.54325}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithFloatNumbers_negative() {
        String expected = "{\"b\":-10.54325,\"a\":10.429318549148632}";
        String actual = "{\"a\":10.429318549148632,\"b\":-10.5432}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithSpecialCharacters() {
        String expected = "{\"b\":\"\\\\Qso!@!$#@%$#^&^%*)(*&\\\\Eme \\n\\t te\\\\\\\\'xt\"}";
        String actual = "{\"b\":\"so!@!$#@%$#^&^%*)(*&me \\n\\t te\\\\'xt\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareWithSpecialCharacters_negative() {
        String expected = "{\"b\":\"some \\n\\t text\"}";
        String actual = "{\"b\":\"some \\\\n\\\\t text\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareWithActualStringRepresentedNumbers_negative() {
        String expected = "{\"b\":1,\"a\":2}";
        String actual = "{\"a\":\"2\",\"b\":1}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void deepCompareJson() {
        String expected =
                "{\"b\":{\"x\":\"val1\",\"y\":\"val2\"},\"a\":{\"t\":\"val3\",\"z\":\"val4\"}}";
        String actual =
                "{\"a\":{\"t\":\"val3\",\"z\":\"val4\"},\"b\":{\"x\":\"val1\",\"y\":\"val2\"}}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void deepCompareJson_negative() {
        String expected =
                "{\"b\":{\"x\":\"val1\",\"y\":\"val2\"},\"a\":{\"t\":\"val3\",\"z\":\"val3\"}}";
        String actual =
                "{\"a\":{\"t\":\"val3\",\"z\":\"notval3\"},\"b\":{\"x\":\"val1\",\"y\":\"val2\"}}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareForExtraFields() {
        String expected = "{\"b\":\"val1\",\"a\":\"val2\",\".*\":\".*\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\",\"c\":\"val3\",\"d\":\"val4\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareForExtraFields1() {
        String expected = "{\"b\":\"val1\",\"a\":\"val2\",\".*\":\".*\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\",\"c\":{\"a\":0}}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareForExtraFields_negative() {
        String expected = "{\"b\":\"val1\",\"a\":\"val2\",\".*\":\".*\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void compareForNoExtraFields() {
        String expected = "{\"b\":\"val1\",\"a\":\"val2\",\"!.*\":\".*\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void compareForNoExtraFields_negative() {
        String expected = "{\"b\":\"val1\",\"a\":\"val2\",\"!.*\":\".*\"}";
        String actual = "{\"a\":\"val2\",\"b\":\"val1\",\"c\":\"val3\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }
}
