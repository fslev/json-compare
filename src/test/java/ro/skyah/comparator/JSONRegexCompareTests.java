package ro.skyah.comparator;

import org.junit.jupiter.api.Test;

public class JSONRegexCompareTests {

    @Test
    public void compareWithInvalidFieldRegex() {
        String expected = "{\"[a\":\"\\\\d+\"}";
        String actual = "{\"[a\":10}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithSimpleRegex() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"some text\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithSimpleRegex_negative() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"som text\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareWithNegativeLookAheadRegex_negative() {
        String expected = "{\"(?!a.*).*\":\".*\"}";
        String actual = "{\"ab\":\"som text\"}";
        JSONCompare.assertNotMatches(expected, actual);
        expected = "{\"(?!x.*).*\":\"blah\"}";
        JSONCompare.assertNotMatches(expected, actual);
        expected = "{\"(?!x.*).*\":\".*\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithEmptyValue() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithEmptyValue_negative() {
        String expected = "{\"a\":\"!.*\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareWithNumbers() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":10}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithNumbers_negative() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"notanumber\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareWithEscapedRegex() {
        String expected = "{\"a\":\"\\\\Q\\\\d+\\\\E\"}";
        String actual = "{\"a\":\"\\\\d+\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithEscapedRegex_negative() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"\\\\d+\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareFields() {
        String expected = "{\".*oba.*\":\"some value\"}";
        String actual = "{\"foobar\":\"some value\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareFields_negative() {
        String expected = "{\".*oba.*\":\"some value\"}";
        String actual = "{\"barfoo\":\"some value\",\"a\":\"some value\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareDoNotFindFields() {
        String expected = "{\"!.*oba.*\":\"field should not be found\",\"a\":\"some value\"}";
        String actual = "{\"barfoo\":\"field should not be found\",\"a\":\"some value\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareDoNotFindFields_negative() {
        String expected = "{\"!.*oba.*\":\"field should not be found\"}";
        String actual = "{\"a\":\"val1\", \"foobar\":\"field should not be found\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    /**
     * Pattern.quote() method wraps the string in \Q...\E, which turns the text is into a regex
     * literal.
     */
    public void compareWithRegexLiteral() {
        String expected = "{\"a\":\"\\\\Q1\\\\d+4\\\\E\"}";
        String actual = "{\"a\":\"1\\\\d+4\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithRegexLiteral_negative() {
        String expected = "{\"a\":\"1\\\\d+4\"}";
        String actual = "{\"a\":\"1\\\\d+4\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareArray() {
        String expected = "[1,true,\"\\\\d+\",\"text\"]";
        String actual = "[\"text\",1,true,240]";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareArray_negative() {
        String expected = "[\"1\",true,\"!\\\\d+\",\"text\"]";
        String actual = "[\"text\",1,true,240]";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareWithSpecialCharacters() {
        String expected = "{\"\\\\Q@!#$%^&*()\\\\E\":\"@!#$%^&*()[]{};'<./?.,\"}";
        String actual = "{\"@!#$%^&*()\":\"@!#$%^&*()[]{};'<./?.,\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareWithSpecialCharacters_negative() {
        String expected = "{\"\\\\Q@!#$%^&*()\\\\E\":\"!@#$%^&*()[]{};'<./?.,\"}";
        String actual = "{\"@!#$%^&*()a\":\"!@#$%^&*()[]{};'<./?.,\"}";
        JSONCompare.assertNotMatches(expected, actual);
    }
}
