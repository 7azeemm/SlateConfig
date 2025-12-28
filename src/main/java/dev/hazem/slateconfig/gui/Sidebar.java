package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.ConfigMetadata;
import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.gui.components.ScrollableWidget;
import dev.hazem.slateconfig.nodes.PageNode;
import dev.hazem.slateconfig.utils.TextUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.StandardCursors;
import org.jspecify.annotations.Nullable;

public class Sidebar extends ScrollableWidget {
    private final ConfigMetadata metadata;
    private @Nullable PageNode hoveredPage;

    public Sidebar(ConfigMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    protected void init() {
        this.scrollbar.init(x + width - 4, y, height, maxHeight);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        hoveredPage = null;

        int y = this.y + 10;
        int paddingY = 8;
        int paddingX = 10;
        int fontHeight = textRenderer.fontHeight;

        for (PageNode page : metadata.getPages()) {
            boolean isSelected = page == metadata.getCurrentPage();
            int textColor = isSelected ? 0xFFFFFFFF : 0xFFd1d1d1;
            String text = TextUtils.trimString(textRenderer, page.title, width - 15);
            context.drawText(textRenderer, text, x+paddingX, y+1, textColor, true);

            Box box = new Box(x+paddingX/2, y-(paddingY/2), width-paddingX, fontHeight+paddingY);

            if (isSelected) box.render(context, 4, 0x10FFFFFF);

            if (box.isHovered(mouseX, mouseY) && !scrollbar.isDragging()) {
                context.setCursor(StandardCursors.POINTING_HAND);
                box.render(context, 4, 0x20FFFFFF);
                hoveredPage = page;
            }

            y += fontHeight + paddingY;
        }

        updateMaxHeight(y-this.y);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (hoveredPage != null) {
            metadata.selectPage(hoveredPage);
            return true;
        }
        return super.mouseClicked(click, doubled);
    }
}
