package ro.engineering.util;

public class StringUtil {

    private static final byte MAX_CROPPED_LENGTH = 18;

    public static String crop(String msg) {

        if (msg.length() > MAX_CROPPED_LENGTH) {
            return msg.substring(0, MAX_CROPPED_LENGTH) + "...";
        }
        return msg;
    }
}
