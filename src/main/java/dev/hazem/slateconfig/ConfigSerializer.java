package dev.hazem.slateconfig;

import com.google.gson.*;
import dev.hazem.slateconfig.utils.LoggerUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class ConfigSerializer<T> {
    private final Gson gson;
    private final Path path;
    private final Class<T> configClass;
    private final ConfigManager<T> configManager;

    public ConfigSerializer(ConfigManager<T> configManager, Path path) {
        this.path = path;
        this.configClass = configManager.getConfigClass();
        this.configManager = configManager;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .disableHtmlEscaping()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    public void load() {
        if (!Files.exists(path)) {
            LoggerUtils.info("Config not found for {}. Creating default config...", configClass.toString());
            configManager.createInstance();
            save();
            return;
        }

        try {
            JsonElement json = JsonParser.parseString(Files.readString(path));
            T instance = Objects.requireNonNull(gson.fromJson(json, configClass));

            configManager.setInstance(instance);
            LoggerUtils.info("Successfully loaded {}.", configClass);
//            System.out.println(gson.toJson(instance));
        } catch (Exception e) {
            LoggerUtils.error(e, "Failed to load {}! Creating default config...", configClass);
            configManager.createInstance();
            save();
        }
    }

    public void save() {
        try {
            Files.createDirectories(path.getParent());

            String json = gson.toJson(configManager.getInstance(), configClass);
            Files.writeString(path, json, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

            LoggerUtils.info("Successfully saved {}.", configClass);
        } catch (Exception e) {
            LoggerUtils.error(e, "Failed to save {}!", configClass);
        }
    }
}
