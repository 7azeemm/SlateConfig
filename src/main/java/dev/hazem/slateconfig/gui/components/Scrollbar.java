package dev.hazem.slateconfig.gui.components;

import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public class Scrollbar {
    private static final float SCROLL_SPEED = 20f;
    private static final int BAR_THICKNESS = 3;
    private static final int MIN_THUMB_SIZE = 8;
    private static final int OFFSET = 3;
    private final Orientation orientation;
    private PersistentScroll persistentScroll;
    private float x, y;
    private int minPos, maxPos;
    private int thumbSize;
    private boolean isVisible;
    private boolean isDragging;
    private float scrollOffset;

    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public static class PersistentScroll {
        public float scrollPercentage;

        public void reset() {
            scrollPercentage = 0f;
        }
    }

    public Scrollbar(Orientation orientation) {
        this.orientation = orientation;
        this.persistentScroll = new PersistentScroll();
    }

    public void init(int x, int y, int extent, int totalExtent) {
        this.x = x;
        this.y = y;
        this.minPos = (orientation == Orientation.VERTICAL ? y : x) + OFFSET;
        this.maxPos = (orientation == Orientation.VERTICAL ? y : x) + extent - OFFSET;
        updateOffset(extent, totalExtent);
    }

    public void updateOffset(int extent, int totalExtent) {
        boolean newValue = totalExtent > extent;
        if (isVisible && !newValue) {
            persistentScroll.reset();
            scrollOffset = persistentScroll.scrollPercentage;
        }
        isVisible = newValue;

        if (isVisible) {
            scrollOffset = persistentScroll.scrollPercentage * (totalExtent - extent);
            update(extent, totalExtent);
        }
    }

    public void update(int extent, int totalExtent) {
        int length = this.maxPos - this.minPos;
        this.thumbSize = Math.max(MIN_THUMB_SIZE, (int) ((double) extent / totalExtent * length));
        float pos = this.minPos + (persistentScroll.scrollPercentage * (length - this.thumbSize));
        switch (orientation) {
            case HORIZONTAL -> this.x = pos;
            case VERTICAL -> this.y = pos;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        if (!isVisible) return;

        int barWidth = orientation == Orientation.VERTICAL ? BAR_THICKNESS : thumbSize;
        int barHeight = orientation == Orientation.VERTICAL ? thumbSize : BAR_THICKNESS;

        Renderer.drawRect(context, (int) x, (int) y, barWidth, barHeight, 2, 0xFF4A5050);
        if (isHovered(mouseX, mouseY) || isDragging) {
            Renderer.drawRect(context, (int) x, (int) y, barWidth, barHeight, 2, 0x20FFFFFF);
        }
    }

    public boolean mouseClicked(Click click) {
        if (isHovered(click.x(), click.y())) {
            isDragging = true;
            return true;
        }

        return false;
    }

    public boolean mouseScrolled(double verticalAmount, int extent, int totalExtent) {
        if (isVisible) {
            float scrollDelta = (float) verticalAmount * SCROLL_SPEED / (totalExtent - extent);
            persistentScroll.scrollPercentage = MathHelper.clamp(persistentScroll.scrollPercentage - scrollDelta, 0.0f, 1.0f);
            updateOffset(extent, totalExtent);
            return true;
        }
        return false;
    }

    public boolean mouseDragged(double offset, int extent, int totalExtent) {
        if (isDragging) {
            int pos = (int) (orientation == Orientation.VERTICAL ? y : x);
            float newPos = (float) MathHelper.clamp(pos + offset, minPos, maxPos - this.thumbSize);
            persistentScroll.scrollPercentage = (newPos - minPos) / ((maxPos - minPos) - this.thumbSize);
            updateOffset(extent, totalExtent);
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

    public boolean isHovered(double mouseX, double mouseY) {
        if (!isVisible) return false;
        int barWidth = orientation == Orientation.VERTICAL ? BAR_THICKNESS : thumbSize;
        int barHeight = orientation == Orientation.VERTICAL ? thumbSize : BAR_THICKNESS;
        return mouseX >= x && mouseX <= x + barWidth && mouseY >= y && mouseY <= y + barHeight;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public float getScrollOffset() {
        return scrollOffset;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setPersistentScroll(PersistentScroll persistentScroll) {
        this.persistentScroll = persistentScroll;
    }
}