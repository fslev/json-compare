package ro.skyah.comparator;

public class DefaultJsonComparator implements JsonComparator {

    public boolean compareValues(Object expected, Object actual) {
        return false;
    }

    public boolean compareFields(String expected, String actual) {
        return false;
    }
}
