package dev.hazem.slateconfig.gui.components;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public abstract class ScrollableWidget extends Widget {
    protected final Scrollbar scrollbar;
    protected int maxHeight;

    public ScrollableWidget() {
        this.scrollbar = new Scrollbar();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        float scrollOffset = scrollbar.getScrollOffset();

        this.hovered = context.scissorContains(mouseX, mouseY)
                && mouseX >= this.x
                && mouseY >= this.y
                && mouseX < this.x + this.width
                && mouseY < this.y + this.height;

        context.enableScissor(x, y, x + width, y + height);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(0, -scrollOffset);

        this.renderWidget(context, mouseX, (int) (mouseY + scrollOffset), deltaTicks);

        context.getMatrices().popMatrix();
        context.disableScissor();

        scrollbar.render(context, mouseX, mouseY);
    }

    public void updateMaxHeight(int maxHeight) {
        int oldValue = this.maxHeight;
        this.maxHeight = maxHeight;
        if (oldValue != maxHeight) {
            scrollbar.updateOffset(height, maxHeight);
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        return scrollbar.mouseClicked(click);
    }

    @Override
    public boolean mouseReleased(Click click) {
        return scrollbar.mouseReleased();
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        return scrollbar.mouseDragged(offsetY, height, maxHeight);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return scrollbar.mouseScrolled(verticalAmount, height, maxHeight);
    }
}
