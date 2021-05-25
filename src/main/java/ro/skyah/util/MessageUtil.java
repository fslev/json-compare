package ro.skyah.util;

public class MessageUtil {

    private static final int CROP_SIZE = 1024;
    private static final int LENGTH_S = 2048;
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
            String start = msg.substring(0, CROP_SIZE / 2) + "\n<...>";
            String end = msg.substring(size - CROP_SIZE / 2, size);
            return start + end;
        }
        return msg;
    }
}
