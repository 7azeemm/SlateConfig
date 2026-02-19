package dev.hazem.slateconfig.nodes;

import dev.hazem.slateconfig.gui.components.Box;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigNode {
    public final ConfigNodeType type;
    public final String title;
    public final List<ConfigNode> children = new ArrayList<>();
    public Box pos;
    public boolean hovered;

    public ConfigNode(ConfigNodeType type, String title) {
        this.type = type;
        this.title = title;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks);
    public abstract void init(int x, int y, int width);

    public TextRenderer textRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }
}
