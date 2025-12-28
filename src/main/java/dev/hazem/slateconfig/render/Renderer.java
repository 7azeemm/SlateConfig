package dev.hazem.slateconfig.render;


import dev.hazem.slateconfig.mixins.accessors.DrawContextAccessor;
import dev.hazem.slateconfig.render.states.CircleShapeRenderState;
import dev.hazem.slateconfig.render.states.RectangleShapeRenderState;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector4f;

public class Renderer {
    public static void drawCircle(DrawContext context, int x, int y, int width, int height, int color, int borderColor, float borderThickness) {
        ((DrawContextAccessor) context).getRenderState().addSimpleElement(new CircleShapeRenderState(
                context, x, y, width, height, color,
                borderColor, (float) ((borderThickness * 1.5) / Math.min(width, height))
        ));
    }

    public static void drawCircle(DrawContext context, int x, int y, int width, int height, int color) {
        drawCircle(context, x, y, width, height, color, 0, 0f);
    }

    public static void drawRect(DrawContext context, int x, int y, int width, int height, Vector4f roundness, int color, int borderColor, float borderThickness) {
        ((DrawContextAccessor) context).getRenderState().addSimpleElement(new RectangleShapeRenderState(
                context, x, y, width, height,
                roundness, color, borderColor, borderThickness
        ));
    }

    public static void drawRect(DrawContext context, int x, int y, int width, int height, float roundness, int color, int borderColor, float borderThickness) {
        drawRect(context, x, y, width, height, new Vector4f(roundness), color, borderColor, borderThickness);
    }

    public static void drawRect(DrawContext context, int x, int y, int width, int height, Vector4f roundness, int color) {
        drawRect(context, x, y, width, height, roundness, color, 0, 0f);
    }

    public static void drawRect(DrawContext context, int x, int y, int width, int height, float roundness, int color) {
        drawRect(context, x, y, width, height, new Vector4f(roundness), color, 0, 0f);
    }

    public static void drawBorder(DrawContext context, int x, int y, int width, int height, Vector4f roundness, float thickness, int color) {
        drawRect(context, x, y, width, height, roundness, 0, color, thickness);
    }

    public static void drawBorder(DrawContext context, int x, int y, int width, int height, float roundness, float thickness, int color) {
        drawRect(context, x, y, width, height, new Vector4f(roundness), 0, color, thickness);
    }
}