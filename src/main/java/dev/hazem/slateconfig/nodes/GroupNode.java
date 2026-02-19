package dev.hazem.slateconfig.nodes;

import dev.hazem.slateconfig.gui.GuiHelper;
import dev.hazem.slateconfig.gui.components.Box;
import net.minecraft.client.gui.DrawContext;

public final class GroupNode extends ConfigNode {
    public Box titlePos;

    public GroupNode(String title) {
        super(ConfigNodeType.GROUP, title);
    }

    @Override
    public void init(int x, int y, int width) {
        pos = new Box(x, y);

        x += (GuiHelper.X_PAD / 2) * 3;
        y += 7;
        titlePos = new Box(x, y);

        y += 7;
        for (ConfigNode option : children) {
            y += GuiHelper.Y_PAD;
            option.init(x, y, width - (GuiHelper.X_PAD * 3));
            y += option.pos.h;
        }

        y += GuiHelper.Y_PAD + 1;
        pos.setSize(width, y - pos.y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
        context.drawText(textRenderer(), title, titlePos.x, titlePos.y, 0xFFFFFFFF, false);
        GuiHelper.renderBlock(context, pos);

        for (ConfigNode child : children) {
            child.hovered = hovered && child.pos.isHovered(scissorBox, mouseX, mouseY);
            child.render(context, mouseX, mouseY, scissorBox, deltaTicks);
        }
    }
}
