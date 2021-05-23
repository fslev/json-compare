package ro.skyah.util;

public class MessageUtil {

    private static final int CROPPED_LENGTH_S = 32;
    private static final int CROPPED_LENGTH_M = 256;
    private static final int CROPPED_LENGTH_L = 2048;
    private static final int CROPPED_LENGTH_XL = 8192;
    private static final int CROPPED_LENGTH_XXL = 65535;

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

    public static String cropXXL(String msg) {
        return crop(msg, CROPPED_LENGTH_XXL);
    }

    private static String crop(String msg, int size) {
        if (msg != null && msg.length() > size) {
            String start = msg.substring(0, size / 2) + "<...>";
            String end = msg.substring(size / 2, size);
            return start + end;
        }
        return msg;
    }
}
