package dev.hazem.slateconfig.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import dev.hazem.slateconfig.SlateConfig;
import net.minecraft.client.gl.RenderPipelines;

public class CustomPipelines {
    public static final RenderPipeline RECT_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withVertexFormat(VertexFormat.builder()
                    .add("Position", VertexFormatElement.POSITION)
                    .add("UV0", VertexFormatElement.UV0)
                    .add("UV1", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.UV, 2))
                    .add("Roundness", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.UV, 4))
                    .add("Color", VertexFormatElement.COLOR)
                    .add("BorderColor", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 4))
                    .add("BorderThickness", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1))
                    .build(), VertexFormat.DrawMode.QUADS)
            .withCull(true)
            .withFragmentShader(SlateConfig.id("rect_fragment"))
            .withVertexShader(SlateConfig.id("rect_vertex"))
            .withLocation(SlateConfig.id("rect"))
            .build());

    public static final RenderPipeline CIRCLE_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withVertexFormat(VertexFormat.builder()
                    .add("Position", VertexFormatElement.POSITION)
                    .add("UV0", VertexFormatElement.UV0)
                    .add("Color", VertexFormatElement.COLOR)
                    .add("BorderColor", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 4))
                    .add("BorderThickness", VertexFormatElement.register(getNextVFId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1))
                    .build(), VertexFormat.DrawMode.QUADS)
            .withCull(true)
            .withFragmentShader(SlateConfig.id("circle_fragment"))
            .withVertexShader(SlateConfig.id("circle_vertex"))
            .withLocation(SlateConfig.id("circle"))
            .build());

    private static int getNextVFId() {
        for (int i = 0; i < VertexFormatElement.MAX_COUNT; i++) {
            if (VertexFormatElement.byId(i) == null) return i;
        }
        throw new IllegalStateException("No more free VertexFormatElement slots");
    }
}
