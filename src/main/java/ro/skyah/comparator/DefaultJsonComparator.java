package ro.skyah.comparator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DefaultJsonComparator implements JsonComparator {

    public boolean compareValues(Object expected, Object actual) {
        try {
            Pattern pattern = Pattern.compile(expected.toString(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
            return pattern.matcher(actual.toString()).matches();
        } catch (PatternSyntaxException e) {
            return expected.toString().equalsIgnoreCase(actual.toString());
        }
    }

    public boolean compareFields(String expected, String actual) {
        try {
            Pattern pattern = Pattern.compile(expected, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
            return pattern.matcher(actual).matches();
        } catch (PatternSyntaxException e) {
            return expected.equalsIgnoreCase(actual);
        }
    }
}
