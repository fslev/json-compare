package io.json.compare.util;

/**
 * Formats diff messages. Truncates long ones so a huge mismatched JSON does not
 * dominate the assertion error output, keeping a window of the start and end of
 * the message, separated by a clearly marked placeholder.
 */
public final class MessageUtil {

    private static final String LS = System.lineSeparator();
    private static final int AFTER_CROP_SIZE = 2048;
    private static final int SMALL_LIMIT = 4096;
    private static final int MEDIUM_LIMIT = 8192;
    private static final int LARGE_LIMIT = 65535;
    private static final String ELLIPSIS = LS + LS + "<...cropped content...>" + LS + LS;
    private static final String EXPECTED_LABEL = "EXPECTED ";

    private MessageUtil() {
    }

    public static String cropS(String msg) {
        return crop(msg, SMALL_LIMIT);
    }

    public static String cropM(String msg) {
        return crop(msg, MEDIUM_LIMIT);
    }

    public static String cropL(String msg) {
        return crop(msg, LARGE_LIMIT);
    }

    /**
     * Joins {@code root} and {@code diff}, labelling the result as EXPECTED when the diff starts with a path
     * into the expected JSON (e.g. {@code .name} or {@code [2]}), so it is not mistaken for an actual path.
     */
    public static String labelExpected(String root, String diff) {
        boolean hasPath = diff.startsWith(".") || diff.startsWith("[");
        return (hasPath ? EXPECTED_LABEL : "") + root + diff;
    }

    private static String crop(String msg, int limit) {
        if (msg == null || msg.length() <= limit) {
            return msg;
        }
        int half = AFTER_CROP_SIZE / 2;
        return msg.substring(0, half) + ELLIPSIS + msg.substring(msg.length() - half);
    }
}
