package dev.hazem.slateconfig;

import dev.hazem.slateconfig.gui.ConfigScreen;
import dev.hazem.slateconfig.nodes.*;

import java.util.List;

public class ConfigMetadata {
    private final List<PageNode> pages;
    public PageNode currentPage;

    public ConfigMetadata(List<PageNode> pages) {
        this.pages = pages;
        this.currentPage = pages.getFirst();
    }

    public void selectPage(PageNode page) {
        if (currentPage != page) {
            currentPage = page;
            ConfigScreen.getInstance().updateLayout();
        }
    }

    public List<PageNode> getPages() {
        return pages;
    }
}
