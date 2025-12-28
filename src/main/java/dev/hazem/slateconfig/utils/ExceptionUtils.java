package dev.hazem.slateconfig.utils;

import dev.hazem.slateconfig.SlateConfig;

public class ExceptionUtils {

    public static RuntimeException throwException(String message, Throwable cause) {
        return new RuntimeException("[" + SlateConfig.MOD_NAME + "] " + cause.getClass().getSimpleName() + " - " + message, cause);
    }

    public static RuntimeException throwException(String message) {
        return new RuntimeException("[" + SlateConfig.MOD_NAME + "] " + message);
    }
}
