package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.nodes.OptionNode;
import dev.hazem.slateconfig.options.OptionPosition;
import dev.hazem.slateconfig.options.OptionAdapter;
import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public class BooleanAdapter extends OptionAdapter<Boolean> {

    public BooleanAdapter(OptionNode<Boolean> option) {
        super(option, 28, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
        boolean value = option.get();

        int bgSize = 13;
        int bgX = x + (value ? 0 : bgSize);
        int bgY = y + 2;
        int bgColor = value ? 0xFF3C8428 : 0xFF8B8D8F;
        boolean bgHovered = new Box(bgX, bgY, bgSize, bgSize).isHovered(scissorBox, mouseX, mouseY);

        Renderer.drawRect(context, bgX, bgY, bgSize+1, bgSize, 0, bgColor, 0xFF000000, 1);
        Renderer.drawBorder(context, bgX+1, bgY+1, bgSize-1, bgSize-2, 0, 1, 0x60FFFFFF);

        int buttonSize = bgSize + 2;
        int buttonX = x + (value ? bgSize - 1 : 0);
        int buttonY = y;
        boolean buttonHovered = new Box(buttonX, buttonY, buttonSize, buttonSize).isHovered(scissorBox, mouseX, mouseY);

        Renderer.drawRect(context, buttonX, buttonY, buttonSize, buttonSize, 0, 0xFFcccccc, 0xFF000000, 1);
        Renderer.drawBorder(context, buttonX+1, buttonY+1, buttonSize-2, buttonSize-4, 0, 1, 0x60FFFFFF);
        Renderer.drawRect(context, buttonX+1, buttonY+buttonSize-3, buttonSize-2, 2, 0xFF6E7272);

        hovered = bgHovered || buttonHovered;
        if (hovered) {
            Renderer.drawRect(context, bgX, bgY, bgSize, bgSize, 0x30FFFFFF);
            Renderer.drawRect(context, buttonX, buttonY, buttonSize, buttonSize, 0x30FFFFFF);
        }
    }

    @Override
    public void onClick(Click click) {
        option.set(!option.get());
    }

    @Override
    public OptionPosition position() {
        return OptionPosition.LEFT;
    }
}
