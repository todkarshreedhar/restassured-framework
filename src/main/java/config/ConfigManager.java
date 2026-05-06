package config;

import constants.ServiceType;

public class ConfigManager {
    // 🔹 Base URL
    public static String getBaseUrl(ServiceType serviceType) {
        return switch (serviceType) {

            case USER ->
                    ConfigReader.get("user.base.url");

            case AUTH ->
                    ConfigReader.get("auth.base.url");

            case PAYMENT ->
                    ConfigReader.get("payment.base.url");
        };
    }

    // 🔹 API Key
    public static String getApiKey() {
        return ConfigReader.get("api.key");
    }

    // 🔹 Timeout
    public static int getTimeout() {

        String timeout = ConfigReader.get("timeout");

        if (timeout == null || timeout.isBlank()) {
            return 5000; // default fallback
        }

        return Integer.parseInt(timeout);
    }

    // 🔹 Current Environment
    public static String getEnvironment() {
        return System.getProperty("env", "dev");
    }


}
