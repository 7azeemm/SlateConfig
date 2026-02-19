package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.gui.components.ScrollableWidget;
import dev.hazem.slateconfig.gui.components.Scrollbar;
import dev.hazem.slateconfig.nodes.PageNode;
import dev.hazem.slateconfig.nodes.TabNode;
import dev.hazem.slateconfig.render.Renderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jspecify.annotations.Nullable;

public class TabsView extends ScrollableWidget {
    private static final int TAB_HEIGHT = 18;
    private final PageNode page;
    private @Nullable TabNode hoveredTab;

    public TabsView(PageNode page, int x, int y, int width) {
        super(Scrollbar.Orientation.HORIZONTAL);
        this.page = page;

        int maxWidth = 3 + page.tabs.stream().mapToInt(tab -> 10 + textRenderer.getWidth(tab.title) + 8 + 5).sum();
        init(x, y, width, TAB_HEIGHT + (maxWidth > width ? 8 : 3) + 3);

        scrollbar.setPersistentScroll(page.tabsScroll);
        scrollbar.init(x + 2, y + 3, width - 4, maxWidth);
        updateScrollbar(maxWidth);
    }

    @Override
    public void preRender(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawHorizontalLine(x, x+width, y+height-3, 0xFF000000);
        context.drawHorizontalLine(x, x+width, y+height-2, 0xFF434343);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int tabY = y + (scrollbar.isVisible() ? 8 : 3);
        int tabTextY = tabY + (TAB_HEIGHT / 2) - (textRenderer.fontHeight / 2) + 1;

        hoveredTab = null;

        int tabX = x + 3;
        for (TabNode tab : page.tabs) {
            int tabWidth = 10 + textRenderer.getWidth(tab.title) + 8;

            boolean hovered = !scrollbar.isDragging() && new Box(tabX, tabY, tabWidth, TAB_HEIGHT).isHovered(scissorBox, mouseX, mouseY);
            if (hovered) hoveredTab = tab;

            if (page.selectedTab != tab) {
                context.fill(tabX+2, tabY+2, tabX+tabWidth-1, tabY+ TAB_HEIGHT, 0xFF1c1c1c);
            }

            context.drawHorizontalLine(tabX+1, tabX+tabWidth-1, tabY+1, 0xFF434343);
            context.drawHorizontalLine(tabX, tabX+tabWidth, tabY, 0xFF000000);

            context.drawVerticalLine(tabX+1, tabY+1, tabY+ TAB_HEIGHT +(page.selectedTab != tab ? 0 : 1), 0xFF434343);
            context.drawVerticalLine(tabX, tabY, tabY+ TAB_HEIGHT, 0xFF000000);

            context.drawVerticalLine(tabX+tabWidth-1, tabY+1, tabY+ TAB_HEIGHT +(page.selectedTab != tab ? 0 : 1), 0xFF434343);
            context.drawVerticalLine(tabX+tabWidth, tabY, tabY+ TAB_HEIGHT, 0xFF000000);

            if (page.selectedTab == tab) {
                context.fill(tabX+2, tabY+ TAB_HEIGHT, tabX+tabWidth-1, tabY+ TAB_HEIGHT +2, 0xFF282828);
            } else if (hovered) {
                Renderer.drawRect(context, tabX+1, tabY+1, tabWidth-1, TAB_HEIGHT-1, 0x20FFFFFF);
            }

            context.drawText(textRenderer, tab.title, tabX+10, tabTextY, 0xFFFFFFFF, false);
            tabX += tabWidth + 5;
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (hoveredTab != null) {
            page.selectTab(hoveredTab);
            return true;
        }
        return super.mouseClicked(click, doubled);
    }
}
