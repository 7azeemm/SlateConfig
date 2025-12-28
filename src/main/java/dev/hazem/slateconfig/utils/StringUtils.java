package dev.hazem.slateconfig.utils;

import dev.hazem.slateconfig.SlateConfig;

public class StringUtils {

    public static String addModPrefix(String message) {
        return String.format("[%s] %s", SlateConfig.MOD_NAME, message);
    }
}
