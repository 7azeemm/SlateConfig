package dev.hazem.slateconfig.nodes;

import dev.hazem.slateconfig.gui.ConfigScreen;
import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.gui.components.Scrollbar;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public final class PageNode extends ConfigNode {
    public final List<TabNode> tabs = new ArrayList<>();
    public final Scrollbar.PersistentScroll pageScroll = new Scrollbar.PersistentScroll();
    public final Scrollbar.PersistentScroll tabsScroll = new Scrollbar.PersistentScroll();
    public boolean hasTabs;
    public TabNode selectedTab;

    public PageNode(String title) {
        super(ConfigNodeType.PAGE, title);
    }

    @Override
    public void init(int x, int y, int width) {
    }

    public void init() {
        hasTabs = !tabs.isEmpty();
        if (hasTabs) {
            selectedTab = tabs.getFirst();
        }
    }

    public void selectTab(TabNode tab) {
        if (selectedTab != tab) {
            selectedTab = tab;
            ConfigScreen.getInstance().updateLayout();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
    }

    public List<ConfigNode> getChildren() {
        return selectedTab != null ? selectedTab.children : children;
    }

    public Scrollbar.PersistentScroll getPersistentScroll() {
        return selectedTab != null ? selectedTab.pageScroll : pageScroll;
    }

    public List<OptionNode<?>> getOptions() {
        List<OptionNode<?>> options = new ArrayList<>();
        for (ConfigNode child : getChildren()) {
            if (child instanceof OptionNode<?> option) {
                options.add(option);
            } else if (child instanceof GroupNode group) {
                for (ConfigNode groupChild : group.children) {
                    if (groupChild instanceof OptionNode<?> option) {
                        options.add(option);
                    }
                }
            }
        }
        return options;
    }
}
