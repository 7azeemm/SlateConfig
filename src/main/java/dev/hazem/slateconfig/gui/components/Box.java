package dev.hazem.slateconfig.gui.components;

import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.DrawContext;

public class Box {
    public int x, y, w, h;

    public Box(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    public void render(DrawContext context, float radius, int color) {
        Renderer.drawRect(context, x, y, w, h, radius, color);
    }

    public void render(DrawContext context, int color) {
        render(context, 1f, color);
    }

    public void render(DrawContext context) {
        render(context, 1f, 0xFFFFFFFF);
    }
}
