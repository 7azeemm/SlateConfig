package dev.hazem.slateconfig.nodes;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.gui.components.Scrollbar;
import net.minecraft.client.gui.DrawContext;

public final class TabNode extends ConfigNode {
    public final Scrollbar.PersistentScroll pageScroll = new Scrollbar.PersistentScroll();

    public TabNode(String title) {
        super(ConfigNodeType.TAB, title);
    }

    @Override
    public void init(int x, int y, int width) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
    }
}
