package dev.hazem.slateconfig.utils;

import com.mojang.brigadier.Command;
import dev.hazem.slateconfig.SlateConfig;
import dev.hazem.slateconfig.gui.GuiManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandUtils {

    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                literal(SlateConfig.NAMESPACE)
                        .then(literal("save").executes(ctx -> {
                            Text text = Text.literal(StringUtils.addModPrefix("Saving Config..."));
                            ctx.getSource().sendFeedback(text);
                            SlateConfig.CONFIG_MANAGER.save();
                            return Command.SINGLE_SUCCESS;
                        }))
                        .then(literal("load").executes(ctx -> {
                            Text text = Text.literal(StringUtils.addModPrefix("Loading Config..."));
                            ctx.getSource().sendFeedback(text);
                            SlateConfig.CONFIG_MANAGER.load();
                            return Command.SINGLE_SUCCESS;
                        }))
                        .then(literal("gui").executes(ctx -> {
                            GuiManager.createGUI();
                            return Command.SINGLE_SUCCESS;
                        }))
        ));
    }
}
