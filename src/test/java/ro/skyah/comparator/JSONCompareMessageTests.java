package ro.skyah.comparator;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class JSONCompareMessageTests {

    @Test
    public void checkMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.assertEquals("JSONs are not equal", expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("JSONs are not equal"));
        }
    }

    @Test
    public void checkNullMessageFromJSONObjectFailedCompare() {
        String expected = "{\"a\":true}";
        String actual = "{\"ab\":true}";
        try {
            JSONCompare.assertEquals(expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("Field a was not found"));
        }
    }

}
