package ro.engineering.util;

public class StringUtil {

    private static final int CROPPED_LENGTH_S = 32;
    private static final int CROPPED_LENGTH_M = 256;
    private static final int CROPPED_LENGTH_L = 2048;

    public static String cropSmall(String msg) {
        return crop(msg, CROPPED_LENGTH_S);
    }

    public static String cropMedium(String msg) {
        return crop(msg, CROPPED_LENGTH_M);
    }

    public static String cropLarge(String msg) {
        return crop(msg, CROPPED_LENGTH_L);
    }

    private static String crop(String msg, int size) {
        if (msg.length() > size) {
            return msg.substring(0, size) + "...";
        }
        return msg;
    }
}
