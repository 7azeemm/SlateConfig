package dev.hazem.slateconfig.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigNode {
    public final ConfigNodeType type;
    public final String title;
    public final List<ConfigNode> children = new ArrayList<>();
    public String description;

    public ConfigNode(ConfigNodeType type, String title) {
        this.type = type;
        this.title = title;
    }

    public void addChild(ConfigNode child) {
        children.add(child);
    }
}
