package dev.hazem.slateconfig;

import dev.hazem.slateconfig.config.TestConfig;
import dev.hazem.slateconfig.nodes.*;
import dev.hazem.slateconfig.utils.CommandUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

public class SlateConfig implements ClientModInitializer {
    public final static String NAMESPACE = "slateconfig";
    public final static String MOD_NAME = "SlateConfig";

    private final static Path PATH = FabricLoader.getInstance().getConfigDir().resolve("test.json");
    public final static ConfigManager<TestConfig> CONFIG_MANAGER = new ConfigManager<>(TestConfig.class, PATH);

    @Override
    public void onInitializeClient() {
        CONFIG_MANAGER.load();
        CommandUtils.registerCommands();
    }

    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }
}
