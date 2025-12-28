package dev.hazem.slateconfig.render.states;

import dev.hazem.slateconfig.render.CustomPipelines;
import dev.hazem.slateconfig.render.DirectVertexConsumer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Vector4f;

public class RectangleShapeRenderState extends SimpleShapeRenderState {
    private final Vector4f roundness;

    public RectangleShapeRenderState(DrawContext context, int x, int y, int w, int h, Vector4f roundness, int color, int borderColor, float borderThickness) {
        super(CustomPipelines.RECT_PIPELINE, context, x, y, w, h, color, borderColor, borderThickness);
        this.roundness = roundness;
    }

    @Override
    public void setupVertices(VertexConsumer vertices) {
        DirectVertexConsumer dvc = new DirectVertexConsumer((BufferBuilder) vertices);
        dvc.vertex(pose, x, y + h).texture(0, 0).texture(w, h).putVector4f(roundness).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x + w, y + h).texture(1, 0).texture(w, h).putVector4f(roundness).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x + w, y).texture(1, 1).texture(w, h).putVector4f(roundness).color(color).color2(borderColor).putFloat(borderThickness);
        dvc.vertex(pose, x, y).texture(0, 1).texture(w, h).putVector4f(roundness).color(color).color2(borderColor).putFloat(borderThickness);
    }
}
