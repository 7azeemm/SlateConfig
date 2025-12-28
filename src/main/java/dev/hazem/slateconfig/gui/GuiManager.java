package dev.hazem.slateconfig.gui;


import net.minecraft.client.MinecraftClient;

public class GuiManager {

    public static void createGUI() {
        MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new ConfigScreen()));
    }
}
