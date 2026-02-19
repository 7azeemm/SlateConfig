package dev.hazem.slateconfig.gui.components;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public abstract class ScrollableWidget extends Widget {
    protected final Scrollbar scrollbar;
    protected int totalExtent;
    protected Box scissorBox;

    public ScrollableWidget(Scrollbar.Orientation orientation) {
        this.scrollbar = new Scrollbar(orientation);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int scrollOffset = (int) scrollbar.getScrollOffset();

        preRender(context, mouseX, mouseY, deltaTicks);

        Scrollbar.Orientation orientation = scrollbar.getOrientation();
        int xOffset = orientation == Scrollbar.Orientation.HORIZONTAL ? scrollOffset : 0;
        int yOffset = orientation == Scrollbar.Orientation.VERTICAL ? scrollOffset : 0;
        scissorBox = new Box(x + xOffset, y + yOffset, width, height);
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        context.getMatrices().pushMatrix();
        context.enableScissor(x, y, x + width, y + height);
        context.getMatrices().translate(-xOffset, -yOffset);

        this.renderWidget(context, mouseX + xOffset, mouseY + yOffset, deltaTicks);

        context.disableScissor();
        context.getMatrices().popMatrix();

        scrollbar.render(context, mouseX, mouseY);

        context.getMatrices().pushMatrix();
        context.getMatrices().translate(-xOffset, -yOffset);
        postRender(context, mouseX, mouseY, deltaTicks);
        context.getMatrices().popMatrix();
    }

    public void preRender(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    public void postRender(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    public void updateScrollbar(int totalExtent) {
        int oldValue = this.totalExtent;
        this.totalExtent = totalExtent;
        if (oldValue != totalExtent) {
            scrollbar.updateOffset(getExtent(), totalExtent);
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
        double offset = scrollbar.getOrientation() == Scrollbar.Orientation.VERTICAL ? offsetY : offsetX;
        return scrollbar.mouseDragged(offset, getExtent(), totalExtent);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return scrollbar.mouseScrolled(verticalAmount, getExtent(), totalExtent);
    }

    private int getExtent() {
        return scrollbar.getOrientation() == Scrollbar.Orientation.VERTICAL ? height : width;
    }
}
