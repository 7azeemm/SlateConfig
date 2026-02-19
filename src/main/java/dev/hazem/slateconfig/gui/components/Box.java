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

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public boolean isHovered(Box scissorBox, int mouseX, int mouseY) {
        return Box.isBoxHovered(scissorBox, mouseX, mouseY) && isBoxHovered(this, mouseX, mouseY);
    }

    public static boolean isBoxHovered(Box box, int mouseX, int mouseY) {
        return mouseX >= box.x && mouseX < box.x + box.w && mouseY >= box.y && mouseY < box.y + box.h;
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
