package io.json.compare;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DefaultJsonComparator implements JsonComparator {

    private final Set<CompareMode> compareModes;

    public DefaultJsonComparator(Set<CompareMode> compareModes) {
        this.compareModes = compareModes;
    }

    public boolean compareValues(Object expected, Object actual) {
        if (compareModes != null && compareModes.contains(CompareMode.REGEX_DISABLED)) {
            return expected.toString().equals(actual.toString());
        } else {
            try {
                Pattern pattern = Pattern.compile(expected.toString(), Pattern.DOTALL | Pattern.MULTILINE);
                return pattern.matcher(actual.toString()).matches();
            } catch (PatternSyntaxException e) {
                return expected.toString().equals(actual.toString());
            }
        }
    }

    public boolean compareFields(String expected, String actual) {
        if (compareModes != null && compareModes.contains(CompareMode.REGEX_DISABLED)) {
            return expected.equals(actual);
        } else {
            try {
                Pattern pattern = Pattern.compile(expected, Pattern.DOTALL | Pattern.MULTILINE);
                return pattern.matcher(actual).matches();
            } catch (PatternSyntaxException e) {
                return expected.equals(actual);
            }
        }
    }
}
