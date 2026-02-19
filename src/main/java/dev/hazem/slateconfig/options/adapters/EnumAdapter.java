package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.nodes.OptionNode;
import dev.hazem.slateconfig.options.OptionPosition;
import dev.hazem.slateconfig.options.OptionAdapter;
import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

import java.util.Arrays;

public class EnumAdapter extends OptionAdapter<Enum<?>> {
    public boolean leftArrowHovered;
    public boolean rightArrowHovered;

    public EnumAdapter(OptionNode<Enum<?>> option) {
        super(option);
    }

    @Override
    public void init() {
        this.height = 12;
        this.width = 16 + 20 + Arrays.stream(option.get().getDeclaringClass().getEnumConstants())
                .mapToInt(v -> textRenderer().getWidth(v.name()))
                .max()
                .orElse(0);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
        hovered = new Box(x, y, width, height).isHovered(scissorBox, mouseX, mouseY);
        Enum<?> value = option.get();
        int index = value.ordinal();
        int length = value.getDeclaringClass().getEnumConstants().length;

        // Left Arrow
        Box arrowPos = new Box(x, y, 8, height);
        leftArrowHovered = arrowPos.isHovered(scissorBox, mouseX, mouseY);
        int arrowColor = leftArrowHovered ? 0xFF05FFFF : 0xFFFFFFFF;

        context.fill(arrowPos.x + 4, arrowPos.y + 5, arrowPos.x + 5, arrowPos.y + 10, arrowColor);
        context.fill(arrowPos.x + 3, arrowPos.y + 6, arrowPos.x + 4, arrowPos.y + 9, arrowColor);
        context.fill(arrowPos.x + 2, arrowPos.y + 7, arrowPos.x + 3, arrowPos.y + 8, arrowColor);

        // Right Arrow
        arrowPos = new Box(x + width - 8, y, 8, height);
        rightArrowHovered = arrowPos.isHovered(scissorBox, mouseX, mouseY);
        arrowColor = rightArrowHovered ? 0xFF05FFFF : 0xFFFFFFFF;

        context.fill(arrowPos.x + 4, arrowPos.y + 7, arrowPos.x + 5, arrowPos.y + 8, arrowColor);
        context.fill(arrowPos.x + 3, arrowPos.y + 6, arrowPos.x + 4, arrowPos.y + 9, arrowColor);
        context.fill(arrowPos.x + 2, arrowPos.y + 5, arrowPos.x + 3, arrowPos.y + 10, arrowColor);

        int x = this.x + 8;
        int width = this.width - (8 * 2);

        int textX = x + (width / 2) - (textRenderer().getWidth(value.name()) / 2);
        context.drawText(textRenderer(), value.name(), textX, y, 0xFFFFFFFF, false);

        int xOff = x;
        int lineWidth = width / length;
        for (int i = 0; i < length; i++) {
            int color = i == index ? 0xFF05FFFF : 0xFF000000;
            context.drawHorizontalLine(xOff, xOff + lineWidth - 2, y + (height - 1), color);
            xOff += lineWidth + 1;
        }
    }

    @Override
    public void onClick(Click click) {
        Enum<?> current = option.get();
        Enum<?>[] values = current.getDeclaringClass().getEnumConstants();
        int length = values.length;

        int next = (current.ordinal() + (leftArrowHovered ? -1 + length : 1)) % length;
        option.set(values[next]);
    }

    @Override
    public OptionPosition position() {
        return OptionPosition.RIGHT;
    }
}
