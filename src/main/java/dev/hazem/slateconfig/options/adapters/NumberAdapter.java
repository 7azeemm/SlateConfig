package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.nodes.OptionNode;
import dev.hazem.slateconfig.options.OptionPosition;
import dev.hazem.slateconfig.options.OptionAdapter;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public class NumberAdapter extends OptionAdapter<Number> {

    public NumberAdapter(OptionNode<Number> option) {
        super(option, 0, 0);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
    }

    @Override
    public void onClick(Click click) {
    }

    @Override
    public OptionPosition position() {
        return OptionPosition.RIGHT;
    }
}
