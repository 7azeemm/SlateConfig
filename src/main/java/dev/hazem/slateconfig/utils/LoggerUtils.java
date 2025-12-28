package dev.hazem.slateconfig.utils;

import dev.hazem.slateconfig.SlateConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

public class LoggerUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(SlateConfig.MOD_NAME);

    public static void info(String message, Object... arguments) {
        String formattedMessage = formatMessage(message, arguments);
        LOGGER.info(formattedMessage);
        sendToPlayer(formattedMessage);
    }

    public static void error(Throwable t, String message, Object... arguments) {
        String formattedMessage = formatMessage(message, arguments);
        LOGGER.error(formattedMessage, t);
        sendToPlayer(formattedMessage);
    }

    public static void error(String message, Object... arguments) {
        String formattedMessage = formatMessage(message, arguments);
        LOGGER.error(formattedMessage);
        sendToPlayer(formattedMessage);
    }

    private static String formatMessage(String message, Object... arguments) {
        String formatted = MessageFormatter.arrayFormat(message, arguments).getMessage();
        return StringUtils.addModPrefix(formatted);
    }

    private static void sendToPlayer(String message) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) player.sendMessage(Text.literal(message), false);
    }
}
