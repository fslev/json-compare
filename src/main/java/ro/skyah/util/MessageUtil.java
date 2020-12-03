package ro.skyah.util;

public class MessageUtil {

    private static final int CROPPED_LENGTH_S = 32;
    private static final int CROPPED_LENGTH_M = 256;
    private static final int CROPPED_LENGTH_L = 2048;
    private static final int CROPPED_LENGTH_XL = 8192;

    public static String cropS(String msg) {
        return crop(msg, CROPPED_LENGTH_S);
    }

    public static String cropM(String msg) {
        return crop(msg, CROPPED_LENGTH_M);
    }

    public static String cropL(String msg) {
        return crop(msg, CROPPED_LENGTH_L);
    }

    public static String cropXL(String msg) {
        return crop(msg, CROPPED_LENGTH_XL);
    }

    private static String crop(String msg, int size) {
        if (msg == null) {
            return null;
        }
        if (msg.length() > size) {
            return msg.substring(0, size) + "...";
        }
        return msg;
    }
}
