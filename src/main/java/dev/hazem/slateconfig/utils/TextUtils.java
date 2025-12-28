package dev.hazem.slateconfig.utils;

import net.minecraft.client.font.TextRenderer;

public class TextUtils {

    public static String trimString(TextRenderer textRenderer, String text, int maxWidth) {
        if (textRenderer.getWidth(text) <= maxWidth) return text;

        String ellipsis = "...";
        int ellipsisWidth = textRenderer.getWidth(ellipsis);

        // Binary search to find the longest prefix that fits
        int low = 0;
        int high = text.length();

        while (low < high) {
            int mid = (low + high) / 2;
            String sub = text.substring(0, mid);
            if (textRenderer.getWidth(sub) + ellipsisWidth <= maxWidth) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        // Ensure we don't go out of bounds
        int cutOff = Math.max(0, low - 1);
        return text.substring(0, cutOff).trim() + ellipsis;
    }
}
