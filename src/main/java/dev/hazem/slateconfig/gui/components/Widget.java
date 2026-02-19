package dev.hazem.slateconfig.gui.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class Widget implements Drawable, Element, Selectable {
    protected final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private final List<Widget> children = new ArrayList<>();
    protected int x, y, width, height;
    protected boolean hovered;
    private boolean focused;

    public Widget() {}

    public void init(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.children.clear();
        init();
    }

    protected void init() {
    }

    protected abstract void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks);

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.hovered = context.scissorContains(mouseX, mouseY)
                && mouseX >= this.x
                && mouseY >= this.y
                && mouseX < this.x + this.width
                && mouseY < this.y + this.height;
        this.renderWidget(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for (Widget child : children) {
            if (child.isHovered() && child.mouseClicked(click, doubled)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(Click click) {
        for (Widget child : children) {
            if (child.mouseReleased(click)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        for (Widget child : children) {
            if (child.isHovered() && child.mouseDragged(click, offsetX, offsetY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (Widget child : children) {
            if (child.isHovered() && child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return hovered;
    }

    @Override
    public SelectionType getType() {
        if (this.isFocused()) {
            return Selectable.SelectionType.FOCUSED;
        } else {
            return this.hovered ? Selectable.SelectionType.HOVERED : Selectable.SelectionType.NONE;
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovered() {
        return hovered;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public void addChild(Widget child) {
        children.add(child);
    }
}
