package io.json.compare.util;

public final class MessageUtil {

    private MessageUtil() {

    }

    private static final int AFTER_CROP_SIZE = 2048;
    private static final int S = 4096;
    private static final int M = 8192;
    private static final int L = 65535;

    public static String cropS(String msg) {
        return crop(msg, S);
    }

    public static String cropM(String msg) {
        return crop(msg, M);
    }

    public static String cropL(String msg) {
        return crop(msg, L);
    }

    private static String crop(String msg, int limit) {
        if (msg != null && msg.length() > limit) {
            String start = msg.substring(0, AFTER_CROP_SIZE / 2) + System.lineSeparator() + System.lineSeparator() +
                    "<...cropped content...>" + System.lineSeparator() + System.lineSeparator();
            String end = msg.substring(msg.length() - AFTER_CROP_SIZE / 2);
            return start + end;
        }
        return msg;
    }
}
