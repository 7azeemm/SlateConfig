package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.ConfigMetadata;
import dev.hazem.slateconfig.SlateConfig;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConfigScreen extends Screen {
    private final static Identifier BACKGROUND = SlateConfig.id("background");
    private final static Identifier HORIZONTAL_DIVIDER = SlateConfig.id("horizontal_line");
    private final static Identifier VERTICAL_DIVIDER = SlateConfig.id("vertical_line");
    private final static int SCREEN_WIDTH = 520;
    private final static int SCREEN_HEIGHT = 280;
    private static ConfigScreen instance;
    private int x, y, screenWidth, screenHeight;
    private final Sidebar sidebar;

    private final ConfigMetadata metadata;

    protected ConfigScreen() {
        super(Text.literal("Config Screen"));
        instance = this;
        metadata = SlateConfig.CONFIG_MANAGER.getMetadata();

        sidebar = new Sidebar(metadata);
    }

    @Override
    protected void init() {
        screenWidth = Math.min(SCREEN_WIDTH, this.width - 160);
        screenHeight = Math.min(SCREEN_HEIGHT, this.height - 60);
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        sidebar.init(x+8, y+21, 100-8, screenHeight-29);

        this.addDrawableChild(sidebar);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, screenWidth, screenHeight);
        context.fill(x+7, y+7, x+screenWidth-7, y+20, 0xFF1c1c1c);
        context.drawHorizontalLine(x+7, x+screenWidth-7, y+20, 0xFF000000);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, VERTICAL_DIVIDER, x+100, y+7+13, 6, screenHeight-14-13);

        super.render(context, mouseX, mouseY, deltaTicks);
    }

    public static ConfigScreen getInstance() {
        return instance;
    }
}
