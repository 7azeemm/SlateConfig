package dev.hazem.slateconfig.render.states;

import dev.hazem.slateconfig.render.CustomPipelines;
import dev.hazem.slateconfig.render.DirectVertexConsumer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;

public class CircleShapeRenderState extends SimpleShapeRenderState {
    public CircleShapeRenderState(DrawContext context, int x, int y, int w, int h, int color, int borderColor, float borderThickness) {
        super(CustomPipelines.CIRCLE_PIPELINE, context, x, y, w, h, color, borderColor, borderThickness);
    }

    @Override
    public void setupVertices(VertexConsumer vertices) {
        DirectVertexConsumer dvc = new DirectVertexConsumer((BufferBuilder) vertices);
        dvc.vertex(pose, x, y + h).texture(0, 0).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x + w, y + h).texture(1, 0).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x + w, y).texture(1, 1).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x, y).texture(0, 1).color(color).color2(borderColor).putFloat(borderThickness);
    }
}
