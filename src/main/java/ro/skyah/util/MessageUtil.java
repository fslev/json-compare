package ro.skyah.util;

public class MessageUtil {

    private static final int AFTER_CROP_SIZE = 2048;
    private static final int LENGTH_S = 4096;
    private static final int LENGTH_M = 8192;
    private static final int LENGTH_L = 65535;

    public static String cropS(String msg) {
        return crop(msg, LENGTH_S);
    }

    public static String cropM(String msg) {
        return crop(msg, LENGTH_M);
    }

    public static String cropL(String msg) {
        return crop(msg, LENGTH_L);
    }

    private static String crop(String msg, int size) {
        if (msg != null && msg.length() > size) {
            String start = msg.substring(0, AFTER_CROP_SIZE / 2) + "\n<...cropped content...>";
            String end = msg.substring(size - AFTER_CROP_SIZE / 2);
            return start + end;
        }
        return msg;
    }
}
