package dev.hazem.slateconfig.options;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.nodes.OptionNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public abstract class OptionAdapter<T> {
    protected final OptionNode<T> option;
    public boolean hovered;
    public int x, y, width, height;

    protected OptionAdapter(OptionNode<T> option) {
        this.option = option;
    }

    protected OptionAdapter(OptionNode<T> option, int width, int height) {
        this.option = option;
        this.width = width;
        this.height = height;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void init() {
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks);
    public abstract void onClick(Click click);

    public abstract OptionPosition position();

    public boolean onLeft() {
        return position() == OptionPosition.LEFT;
    }

    public TextRenderer textRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }
}
