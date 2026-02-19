package dev.hazem.slateconfig.gui;

import dev.hazem.slateconfig.ConfigMetadata;
import dev.hazem.slateconfig.SlateConfig;
import dev.hazem.slateconfig.nodes.ConfigNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.stream.Collectors;

public class ConfigScreen extends Screen {
    private static final Identifier BACKGROUND = SlateConfig.id("background");
    private static final Identifier HORIZONTAL_DIVIDER = SlateConfig.id("horizontal_line");
    private static final Identifier VERTICAL_DIVIDER = SlateConfig.id("vertical_line");
    private static final int SCREEN_WIDTH = 520;
    private static final int SCREEN_HEIGHT = 280;
    private static ConfigScreen instance;
    private int x, y, screenWidth, screenHeight;
    private final Sidebar sidebar;
    private final PageView pageView;
    private TabsView tabsView;

    private final ConfigMetadata metadata;

    protected ConfigScreen() {
        super(Text.literal("Config Screen"));
        instance = this;
        metadata = SlateConfig.CONFIG_MANAGER.getMetadata();
        sidebar = new Sidebar(metadata);
        pageView = new PageView(metadata);
    }

    @Override
    protected void init() {
        screenWidth = Math.min(SCREEN_WIDTH, this.width - 160);
        screenHeight = Math.min(SCREEN_HEIGHT, this.height - 60);
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        sidebar.init(x+8, y+21, 100-8, screenHeight-29);
        updateLayout();

        addDrawableChild(sidebar);
        addDrawableChild(pageView);
    }

    public void updateLayout() {
        int viewX = x+100+6;
        int viewY = y+21;
        int viewWidth = screenWidth-108-6;
        int viewHeight = screenHeight-29;

        if (tabsView != null) {
            remove(tabsView);
            tabsView = null;
        }

        if (metadata.currentPage.hasTabs) {
            tabsView = new TabsView(metadata.currentPage, viewX, viewY, viewWidth);
            addDrawableChild(tabsView);
        }

        int pageY = tabsView != null ? viewY + tabsView.getHeight() : viewY;
        int pageHeight = tabsView != null ? viewHeight - tabsView.getHeight() : viewHeight;
        pageView.init(viewX, pageY, viewWidth, pageHeight);

        remove(pageView);
        addDrawableChild(pageView);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, screenWidth, screenHeight);
        context.fill(x+7, y+7, x+screenWidth-7, y+20, 0xFF1c1c1c);
        context.drawHorizontalLine(x+7, x+screenWidth-7, y+20, 0xFF000000);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, VERTICAL_DIVIDER, x+100, y+7+13, 6, screenHeight-14-13);

        super.render(context, mouseX, mouseY, deltaTicks);

        context.drawText(textRenderer, String.valueOf(MinecraftClient.getInstance().getCurrentFps()), 5, 5, 0xFFFFFFFF, true);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        boolean clicked = super.mouseClicked(click, doubled);
        if (clicked) playDownSound();
        return clicked;
    }

    private void playDownSound() {
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @Override
    public void removed() {
        super.removed();
        instance = null;
    }

    public static ConfigScreen getInstance() {
        return instance;
    }
}
