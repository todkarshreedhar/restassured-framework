package utils;

public class LogUtils {

    public static boolean isSensitive(String headerName) {
        return headerName.equalsIgnoreCase("Authorization") ||
                headerName.equalsIgnoreCase("x-api-key");
    }

    public static String mask(String value) {
        if (value == null || value.length() < 4) return "****";
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }
}
