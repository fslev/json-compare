package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class JSONRegexCompareTests {

    @Test
    public void compareWithInvalidFieldRegex() {
        String expected = "{\"[a\":\"\\\\d+\"}";
        String actual = "{\"[a\":10}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithInvalidRegexAndDifferentCases() {
        String expected = "{\"[a\":\"(test\"}";
        String actual = "{\"[A\":\"(Test\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
        assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        String actual1 = "{\"[a\":\"(test\"}";
        JSONCompare.compare(expected, actual1).assertMatches();
    }

    @Test
    public void compareWithInvalidFieldRegexWithCompareModeRegexDisabled() {
        String expected = "{\"[a\":\"10\"}";
        String actual = "{\"[a\":10}";
        JSONCompare.compare(expected, actual).modes(CompareMode.REGEX_DISABLED).assertMatches();

        String expected1 = "{\"[a\":\"\\\\d+\"}";
        assertThrows(AssertionError.class, () ->
                JSONCompare.compare(expected1, actual).modes(CompareMode.REGEX_DISABLED).assertMatches());
        JSONCompare.compare(expected1, actual).modes(CompareMode.REGEX_DISABLED).assertNotMatches();
    }

    @Test
    public void compareWithSimpleRegex() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"some text\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithSimpleRegex_negative() {
        String expected = "{\"a\":\".*me.*\"}";
        String actual = "{\"a\":\"som text\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithNegativeLookAheadRegex_negative() {
        String expected = "{\"(?!a.*).*\":\".*\"}";
        String actual = "{\"ab\":\"som text\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
        expected = "{\"(?!x.*).*\":\"blah\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
        expected = "{\"(?!x.*).*\":\".*\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithEmptyValue() {
        String expected = "{\"a\":\".*\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithEmptyValue_negative() {
        String expected = "{\"a\":\"!.*\"}";
        String actual = "{\"a\":\"\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithNumbers() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":10}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithNumbers_negative() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"notanumber\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithEscapedRegex() {
        String expected = "{\"a\":\"\\\\Q\\\\d+\\\\E\"}";
        String actual = "{\"a\":\"\\\\d+\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithEscapedRegex_negative() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"\\\\d+\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareFields() {
        String expected = "{\".*oba.*\":\"some value\"}";
        String actual = "{\"foobar\":\"some value\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareFields_negative() {
        String expected = "{\".*oba.*\":\"some value\"}";
        String actual = "{\"barfoo\":\"some value\",\"a\":\"some value\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareDoNotFindFields() {
        String expected = "{\"!.*oba.*\":\"field should not be found\",\"a\":\"some value\"}";
        String actual = "{\"barfoo\":\"field should not be found\",\"a\":\"some value\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareDoNotFindFields_negative() {
        String expected = "{\"!.*oba.*\":\"field should not be found\"}";
        String actual = "{\"a\":\"val1\", \"foobar\":\"field should not be found\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    /**
     * Pattern.quote() method wraps the string in \Q...\E, which turns the text is into a regex
     * literal.
     */
    public void compareWithRegexLiteral() {
        String expected = "{\"a\":\"\\\\Q1\\\\d+4\\\\E\"}";
        String actual = "{\"a\":\"1\\\\d+4\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithRegexLiteral_negative() {
        String expected = "{\"a\":\"1\\\\d+4\"}";
        String actual = "{\"a\":\"1\\\\d+4\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareArray() {
        String expected = "[1,true,\"\\\\d+\",\"text\"]";
        String actual = "[\"text\",1,true,240]";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareArray_negative() {
        String expected = "[\"1\",true,\"!\\\\d+\",\"text\"]";
        String actual = "[\"text\",1,true,240]";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareWithSpecialCharacters() {
        String expected = "{\"\\\\Q@!#$%^&*()\\\\E\":\"@!#$%^&*()[]{};'<./?.,\"}";
        String actual = "{\"@!#$%^&*()\":\"@!#$%^&*()[]{};'<./?.,\"}";
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareWithSpecialCharacters_negative() {
        String expected = "{\"\\\\Q@!#$%^&*()\\\\E\":\"!@#$%^&*()[]{};'<./?.,\"}";
        String actual = "{\"@!#$%^&*()a\":\"!@#$%^&*()[]{};'<./?.,\"}";
        JSONCompare.compare(expected, actual).assertNotMatches();
    }
}
