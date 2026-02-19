package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.ConfigMetadata;
import dev.hazem.slateconfig.SlateConfig;
import dev.hazem.slateconfig.gui.components.ScrollableWidget;
import dev.hazem.slateconfig.gui.components.Scrollbar;
import dev.hazem.slateconfig.nodes.*;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class PageView extends ScrollableWidget {
    private final ConfigMetadata metadata;

    public PageView(ConfigMetadata metadata) {
        super(Scrollbar.Orientation.VERTICAL);
        this.metadata = metadata;
    }

    @Override
    protected void init() {
        scrollbar.init(x + width - 5, y, height, totalExtent);
        scrollbar.setPersistentScroll(metadata.currentPage.getPersistentScroll());

        List<ConfigNode> children = metadata.currentPage.getChildren();

        int y = this.y + GuiHelper.Y_PAD;
        for (ConfigNode child : children) {
            y += GuiHelper.Y_PAD;

            child.init(this.x + GuiHelper.X_PAD, y, width - (GuiHelper.X_PAD * 2) - 3);
            y += child.pos.h;
        }
        y += GuiHelper.Y_PAD;

        updateScrollbar(y - this.y);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        for (ConfigNode child : metadata.currentPage.getChildren()) {
            child.hovered = !scrollbar.isDragging() && child.pos.isHovered(scissorBox, mouseX, mouseY);
            child.render(context, mouseX, mouseY, scissorBox, deltaTicks);
        }
    }

    @Override
    public void postRender(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        for (OptionNode<?> option : metadata.currentPage.getOptions()) {
//            if (option.hovered) option.renderTooltip(context);
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for (OptionNode<?> option : metadata.currentPage.getOptions()) {
            if (option.adapter.hovered) {
                option.adapter.onClick(click);
                SlateConfig.CONFIG_MANAGER.save();
                return true;
            }
        }

        return super.mouseClicked(click, doubled);
    }
}
