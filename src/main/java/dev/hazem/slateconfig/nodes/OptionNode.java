package dev.hazem.slateconfig.nodes;

import dev.hazem.slateconfig.gui.GuiHelper;
import dev.hazem.slateconfig.gui.components.Box;
import dev.hazem.slateconfig.options.OptionAdapter;
import dev.hazem.slateconfig.render.Renderer;
import dev.hazem.slateconfig.utils.ReflectionUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class OptionNode<T> extends ConfigNode {
    public final OptionAdapter<T> adapter;
    public final T defaultValue;
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final String description;
    private List<OrderedText> descriptionList;

    @SuppressWarnings("unchecked")
    public OptionNode(String title, String description, Class<T> adapter, T defaultValue, Supplier<T> getter, Consumer<T> setter) {
        super(ConfigNodeType.OPTION, title);
        this.description = description;
        this.adapter = (OptionAdapter<T>) ReflectionUtils.createClassInstance(adapter, this);
        this.defaultValue = defaultValue;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public void init(int x, int y, int width) {
        adapter.init();

        int height = 9 + 5;
//        if (description != null && !description.isEmpty()) {
//            descriptionList = textRenderer().wrapLines(StringVisitable.plain(description), width - 20 - adapter.width);
//            height += 3 + (descriptionList.size() * 9);
//        }
        height = Math.max(height, adapter.height + 10);

        pos = new Box(x, y, width, height);

        int adapterX = pos.x + (pos.w - adapter.width - 10);
        int adapterY = pos.y + 5;
        adapter.setPos(adapterX, adapterY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, Box scissorBox, float deltaTicks) {
//        GuiHelper.renderBlock(context, pos);

        int textX = pos.x + 6;
        context.drawText(textRenderer(), title, textX, pos.y, 0xFFFFFFFF, false);

//        if (descriptionList != null && !descriptionList.isEmpty()) {
//            int lineY = pos.y + 9 + 3;
//            for (OrderedText line : descriptionList) {
//                context.drawText(textRenderer(), line, textX, lineY, 0xFF8B8D8F, false);
//                lineY += 9;
//            }
//        }

        adapter.render(context, mouseX, mouseY, scissorBox, deltaTicks);

//        if (hovered) {
//            Renderer.drawRect(context, pos.x, pos.y, pos.w, pos.h, 0x10FFFFFF);
//        }
    }

    public void renderTooltip(DrawContext context) {
        if (description == null || description.isEmpty()) return;
        List<String> list = new ArrayList<>(List.of(description));
        int maxWidth = list.stream().mapToInt(l -> textRenderer().getWidth(l)).max().orElse(0);

        int tth = (list.size() * 12) + 5 + 1;
        int tty = pos.y - 3 - tth;
        int ttx = pos.x + 20;
        int ttw = maxWidth + 5 + 5;

        Box tPos = new Box(ttx, tty, ttw, tth);
        Renderer.drawRect(context, tPos.x, tPos.y, tPos.w, tPos.h, 0xFF1c1c1c);
        GuiHelper.renderBlock(context, tPos);

        int oY = tty + 5;
        for (String s : list) {
            context.drawText(textRenderer(), s, ttx + 5, oY, 0xFFFFFFFF, false);
            oY += 12;
        }
    }

    public T get() {
        return getter.get();
    }

    public void set(T value) {
        setter.accept(value);
    }
}
