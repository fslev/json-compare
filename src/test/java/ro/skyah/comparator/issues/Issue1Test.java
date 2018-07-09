package ro.skyah.comparator.issues;

import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.JSONCompare;
import org.junit.Test;

/**
 * https://github.com/fslev/json-compare/issues/1
 */
public class Issue1Test {

    @Test
    public void testIssue() {
        String expected =
                "{\"field\":\"Moss Agate Cluster Bracelet strengthens in times of stress. It releases fears as it is made from genuine moss agate crystals. It is helpful in relieving sensitivities to weather and pollution. This can be used to speed up recovery, for a long term illnesses, cleanse the circulatory and elimination systems. It enhances concentration and persistence. It is an abundance stone. This affordable bracelet is great for daily wear to give you the benefit of the stone's energy all day long.\\n\\n1. Wear this for: supports relaxation, regeneration and recovery\\n2. promotes intuitive action leading to a cleansing of consciousness\\n3. sets you free from the walls of emotional limitation and helps you achieve independence\\n (Rest Details and Usage Method will be sent along with the product)\"}";
        String actual =
                "{\"field\":\"Moss Agate Cluster Bracelet strengthens in times of stress. It releases fears as it is made from genuine moss agate crystals. It is helpful in relieving sensitivities to weather and pollution. This can be used to speed up recovery, for a long term illnesses, cleanse the circulatory and elimination systems. It enhances concentration and persistence. It is an abundance stone. This affordable bracelet is great for daily wear to give you the benefit of the stone's energy all day long.\\n\\n1. Wear this for: supports relaxation, regeneration and recovery\\n2. promotes intuitive action leading to a cleansing of consciousness\\n3. sets you free from the walls of emotional limitation and helps you achieve independence\\n (Rest Details and Usage Method will be sent along with the product)\"}";
        JSONCompare.assertNotEquals(expected, actual);
        JSONCompare.assertEquals(expected, actual, CompareMode.DO_NOT_USE_REGEX);
    }

    @Test
    public void isolateIssue() {
        String expected = "{\"field\":\"new line.\\n\\n1. \\\\Q(wa)\\\\E\"}";
        String actual = "{\"field\":\"new line.\\n\\n1. (wa)\"}";
        JSONCompare.assertEquals(expected, actual);
    }

    @Test
    public void isolateIssue_negative() {
        String expected = "{\"field\":\"new line.\\n\\n1. (wa)\"}";
        String actual = "{\"field\":\"new line.\\n\\n1. (wa)\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

    @Test
    public void isolateIssueWithInvalidRegexPattern() {
        String expected = "{\"field\":\"new line.\\n\\n1. (wa\"}";
        String actual = "{\"field\":\"new line.\\n\\n1. (wa\"}";
        JSONCompare.assertEquals(expected, actual, CompareMode.DO_NOT_USE_REGEX);
    }

    @Test
    public void isolateIssueWithInvalidRegexpattern_negative() {
        String expected = "{\"field\":\"Snew line.\\n\\n1. (wa\"}";
        String actual = "{\"field\":\"new line.\\n\\n1. (wa\"}";
        JSONCompare.assertNotEquals(expected, actual);
    }

}
