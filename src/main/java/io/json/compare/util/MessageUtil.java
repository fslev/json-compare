package io.json.compare.util;

/**
 * Truncates long diff messages so a huge mismatched JSON does not dominate the
 * assertion error output. Keeps a window of the start and end of the message,
 * separated by a clearly marked placeholder.
 */
public final class MessageUtil {

    private static final String LS = System.lineSeparator();
    private static final int AFTER_CROP_SIZE = 2048;
    private static final int SMALL_LIMIT = 4096;
    private static final int MEDIUM_LIMIT = 8192;
    private static final int LARGE_LIMIT = 65535;
    private static final String ELLIPSIS = LS + LS + "<...cropped content...>" + LS + LS;

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

    private static String crop(String msg, int limit) {
        if (msg == null || msg.length() <= limit) {
            return msg;
        }
        int half = AFTER_CROP_SIZE / 2;
        return msg.substring(0, half) + ELLIPSIS + msg.substring(msg.length() - half);
    }
}
