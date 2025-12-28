package dev.hazem.slateconfig;

import dev.hazem.slateconfig.nodes.*;

import java.util.List;

public class ConfigMetadata {
    private final List<PageNode> pages;
    private PageNode currentPage;

    public ConfigMetadata(List<PageNode> pages) {
        this.pages = pages;
        if (!pages.isEmpty()) {
            currentPage = pages.getFirst();
        }
    }

    public void selectPage(PageNode page) {
        if (currentPage != page) {
            currentPage = page;
        }
    }

    public PageNode getCurrentPage() {
        return currentPage;
    }

    public List<PageNode> getPages() {
        return pages;
    }
}
