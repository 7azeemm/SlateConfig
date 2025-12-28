package dev.hazem.slateconfig.gui.components;

import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public class Scrollbar {
    private static final float SCROLL_SPEED = 20f;
    private static final int BAR_WIDTH = 3;
    private static final int MIN_HEIGHT = 8;
    private static final int OFFSET = 3;
    private final float defaultScrollPercentage;
    private final float defaultScrollOffset;
    private float x, y;
    private int minY, maxY;
    private int height;
    private boolean isVisible;
    private boolean isDragging;
    private float scrollPercentage;
    private float scrollOffset;

    public Scrollbar() {
        this(0f, 0f);
    }

    public Scrollbar(float scrollPercentage, float scrollOffset) {
        this.defaultScrollPercentage = scrollPercentage;
        this.defaultScrollOffset = scrollOffset;
        this.scrollPercentage = scrollPercentage;
        this.scrollOffset = scrollOffset;
    }

    public void init(int x, int y, int height, int maxHeight) {
        this.x = x;
        this.minY = y + OFFSET;
        this.maxY = y + height - OFFSET;
        updateOffset(height, maxHeight);
    }

    public void updateOffset(int height, int maxHeight) {
        boolean newValue = maxHeight > height;
        if (isVisible && !newValue) reset();
        isVisible = newValue;

        if (isVisible) {
            calculateScrollOffset(height, maxHeight);
            update(height, maxHeight);
        }
    }

    public void calculateScrollOffset(int height, int maxHeight) {
        float maxScroll = maxHeight - height;
        scrollPercentage = MathHelper.clamp(scrollPercentage, 0.0f, 1.0f);
        scrollOffset = MathHelper.clamp(scrollPercentage * maxScroll, 0.0f, maxScroll);
    }

    public void update(int height, int maxHeight) {
        int actualHeight = this.maxY - this.minY;
        this.height = Math.max(MIN_HEIGHT, (int) ((double) height / maxHeight * actualHeight));
        this.y = this.minY + (scrollPercentage * (actualHeight - this.height));
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        if (!isVisible) return;

        Renderer.drawRect(context, (int) x, (int) y, BAR_WIDTH, height, 2, 0xFF4A5050);
        if (isHovered(mouseX, mouseY) || isDragging) {
            Renderer.drawRect(context, (int) x, (int) y, BAR_WIDTH, height, 2, 0x20FFFFFF);
        }
    }

    public boolean mouseClicked(Click click) {
        if (isHovered(click.x(), click.y())) {
            isDragging = true;
            return true;
        }

        return false;
    }

    public boolean mouseScrolled(double verticalAmount, int height, int maxHeight) {
        if (isVisible) {
            float scrollDelta = (float) verticalAmount * SCROLL_SPEED / (maxHeight - height);
            scrollPercentage = MathHelper.clamp(scrollPercentage - scrollDelta, 0.0f, 1.0f);
            updateOffset(height, maxHeight);
            return true;
        }
        return false;
    }

    public boolean mouseDragged(double offsetY, int height, int maxHeight) {
        if (isDragging) {
            float newBarY = (float) MathHelper.clamp(y + offsetY, minY, maxY - this.height);
            scrollPercentage = (newBarY - minY) / ((maxY - minY) - this.height);
            scrollOffset = scrollPercentage * (maxHeight - height);

            update(height, maxHeight);
            return true;
        }
        return false;
    }

    public boolean mouseReleased() {
        if (isDragging) {
            isDragging = false;
            return true;
        }
        return false;
    }

    public void reset() {
        scrollPercentage = defaultScrollPercentage;
        scrollOffset = defaultScrollOffset;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return isVisible && mouseX >= x && mouseX <= x + BAR_WIDTH && mouseY >= y && mouseY <= y + height;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public float getScrollOffset() {
        return scrollOffset;
    }
}