package io.json.compare.matcher;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import io.json.compare.JsonComparator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class JSONCompareModeTests {

    @Test
    public void compareJSONObjectNonExtensible() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true}";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "Jsons do not match");
        expected = "{}";
        actual = "{}";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONObjectNonExtensible_negative() {
        String expected = "{\"a\":true}";
        String actual = "{\"a\":true,\"b\":false}";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)), "Jsons match");
        expected = "{}";
        actual = "{\"a\":\"val2\"}";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONArrayNonExtensible() {
        String expected = "[1,2,3]";
        String actual = "[1,2,3]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONArrayNonExtensible_negative() {
        String expected = "[1,2,3]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONArrayViaJSONObjectNonExtensibleMode() {
        String expected = "[2,{\"a\":true}]";
        String actual = "[1,2,3,4,{\"a\":true},{\"b\":false}]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONArrayViaJSONObjectNonExtensibleMode_negative() {
        String expected = "[2,{\"a\":true}]";
        String actual = "[1,2,3,4,{\"a\":true,\"b\":false}]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
        JSONCompare.assertNotMatches(expected, actual, null, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareEmptyJSONArrayViaStrictOrder() {
        String expected = "[]";
        String actual = "[1,2]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareEmptyJSONArrayViaStrictOrder_negative() {
        String expected = "[2]";
        String actual = "[]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareEmptyJSONArrayViaJSONArrayNonExtensible() {
        String expected = "[]";
        String actual = "[]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareEmptyJSONArrayViaJSONArrayNonExtensible_negative() {
        String expected = "[]";
        String actual = "[1,2]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONObjectViaJSONArrayNonExtensibleMode() {
        String expected = "{\"a\":true,\"b\":[2,4,5,6]}";
        String actual = "{\"a\":true,\"c\":\"text\",\"b\":[6,5,2,4]}";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareJSONObjectViaJSONArrayNonExtensibleMode_negative() {
        String expected = "{\"a\":true,\"b\":[2,4,5,6]}";
        String actual = "{\"a\":true,\"c\":\"text\",\"b\":[6,5,2,4,2018]}";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareViaJSONArrayAndJSONObjectNonExtensibleModes() {
        String expected = "{\"a\":true,\"b\":[2,4,5,6]}";
        String actual = "{\"b\":[6,5,2,4],\"a\":true}";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareViaJSONArrayAndJSONObjectNonExtensibleModes_negative() {
        String expected = "{\"a\":true,\"b\":[2,4,5,6]}";
        String actual = "{\"b\":[6,5,2,4,8],\"a\":true,\"c\":2}";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE,
                CompareMode.JSON_OBJECT_NON_EXTENSIBLE)));
    }

    @Test
    public void compareViaDoNotUseRegexMode() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"\\\\d+\"}";
        JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });

        // OR
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Collections.singletonList(CompareMode.REGEX_DISABLED)));
    }

    @Test
    public void compareViaDoNotUseRegexMode_negative() {
        String expected = "{\"a\":\"\\\\d+\"}";
        String actual = "{\"a\":\"2\"}";
        JSONCompare.assertNotMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareBooleanStringViaDoNotUseRegexMode() {
        String expected = "{\"a\":\"true\"}";
        String actual = "{\"a\":\"true\"}";
        JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareBooleanStringViaDoNotUseRegexMode_negative() {
        String expected = "{\"a\":\"true\"}";
        String actual = "{\"a\":true}";
        JSONCompare.assertNotMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return false;
            }
        });
    }

    @Test
    public void compareViaDoNotUseRegexModeOnJsonFields() {
        String expected = "{\"\\\\d+\":\".*\"}";
        String actual = "{\"\\\\d+\":\"text\"}";
        JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                try {
                    Pattern pattern = Pattern.compile(expected.toString());
                    return pattern.matcher(actual.toString()).matches();
                } catch (PatternSyntaxException e) {
                    return expected.equals(actual);
                }
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareViaDoNotUseRegexModeOnJsonFields_negative() {
        String expected = "{\"\\\\d+\":\".*\"}";
        String actual = "{\"2\":\"text\"}";
        JSONCompare.assertNotMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                try {
                    Pattern pattern = Pattern.compile(expected.toString());
                    return pattern.matcher(actual.toString()).matches();
                } catch (PatternSyntaxException e) {
                    return expected.equals(actual);
                }
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }


    @Test
    public void compareViaDoNotUseRegexModeAndDoNotFindUseCase() {
        String expected = "{\"!\\\\d+\":\"text\"}";
        String actual = "{\"10\":\"text\"}";
        JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareViaDoNotUseRegexModeAndDoNotFindUseCase_negative() {
        String expected = "{\"!\\\\d+\":\"text\"}";
        String actual = "{\"\\\\d+\":\"text\"}";
        JSONCompare.assertNotMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareWithJsonArrayStrictOrderMode() {
        String expected = "[1,2,3]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareWithJsonArrayStrictOrderMode_negative() {
        String expected = "[1,3,2]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareWithJsonArrayStrictOrderAndNonExtensibleMode() {
        String expected = "[1,2,3]";
        String actual = "[1,2,3]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER,
                CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareWithJsonArrayStrictOrderAndNonExtensibleMode_negative() {
        String expected = "[1,2,3]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER,
                CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareWithJsonArrayStrictOrderModeAndDoNotFindUseCase() {
        String expected = "[1,2,\"!4\"]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
        JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER, CompareMode.REGEX_DISABLED)));

    }

    @Test
    public void compareWithJsonArrayStrictOrderModeAndDoNotFindUseCase_negative() {
        String expected = "[1,2,\"!3\"]";
        String actual = "[1,2,3,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareArraysWithDuplicatedElementsAndStrictOrderMode() {
        String expected = "[2,2,4,4,4]";
        String actual = "[2,2,4,4,4,4]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareArraysWithDuplicatedElementsAndStrictOrderMode_negative() {
        String expected = "[2,4,4,4,4]";
        String actual = "[2,2,4,4,4,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER)));
    }

    @Test
    public void compareArraysWithDuplicatedElementsAndNonExtensibleMode() {
        String expected = "[2,2,4,4,2]";
        String actual = "[2,2,2,4,4]";
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareArraysWithDuplicatedElementsAndNonExtensibleMode_negative() {
        String expected = "[2,2,4,4]";
        String actual = "[2,2,2,4,4]";
        JSONCompare.assertNotMatches(expected, actual, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_NON_EXTENSIBLE)));
    }

    @Test
    public void compareFieldsViaCaseInsensitiveMode() {
        String expected = "{\"firstname\":\"text to match\"}";
        String actual = "{\"firstName\":\"text to match\"}";
        JSONCompare.assertNotMatches(expected, actual);
        expected = "{\"(?i)firstname(?-i)\":\"text to match\"}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareValuesViaCaseInsensitiveMode() {
        String expected = "{\"a\":\"Text To Match\",\"b\":[\"Some Text\"]}";
        String actual = "{\"a\":\"text to match\",\"b\":[\"some text\"]}";
        JSONCompare.assertNotMatches(expected, actual);
        expected = "{\"a\":\"text to match\",\"b\":[\"some text\"]}";
        JSONCompare.assertMatches(expected, actual);
        expected = "{\"a\":\"(?i)Text To Match(?-i)\",\"b\":[\"(?i)Some T(?-i)ext\"]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void compareValuesViaDoNotUseRegexModeAndInvalidRegexPatternsInsideValues() {
        String expected = "{\"a\":\"text to (match\"}";
        String actual = "{\"a\":\"text to (match\"}";
        JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareValuesViaDoNotUseRegexModeAndInvalidRegexPatternsInsideValues_negative() {
        String expected = "{\"a\":\"!text to (match\"}";
        String actual = "{\"a\":\"text to (match\"}";
        JSONCompare.assertNotMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
    }

    @Test
    public void compareJsonsWithRegexDisabledMode() {
        String expected = "[1, \"(.*)\", 3, 4, \"!.*\"]";
        String actual = "[4, \"(.*)\", 1, 3]";
        JSONCompare.assertNotMatches(expected, actual);
        JSONCompare.assertMatches(expected, actual, new HashSet<>(Collections.singletonList(CompareMode.REGEX_DISABLED)));
    }
}
