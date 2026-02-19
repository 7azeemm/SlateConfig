package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.DrawContext;

public class GuiHelper {
    public static final int X_PAD = 4;
    public static final int Y_PAD = 5;

    public static void renderBlock(DrawContext context, Box box) {
        Renderer.drawBorder(context, box.x, box.y, box.w, box.h, 0, 1, 0xFF000000);
        Renderer.drawBorder(context, box.x+1, box.y+1, box.w-2, box.h-2, 0, 1, 0xFF434343);
        context.fill(box.x, box.y, box.x + box.w, box.y + box.h, 0x35000000);
    }
}
