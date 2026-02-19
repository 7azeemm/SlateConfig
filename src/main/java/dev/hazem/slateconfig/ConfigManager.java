package dev.hazem.slateconfig;

import dev.hazem.slateconfig.options.OptionRegistry;
import dev.hazem.slateconfig.utils.ReflectionUtils;

import java.nio.file.Path;
import java.util.Objects;

public class ConfigManager<T> {
    private final Class<T> configClass;
    private final ConfigSerializer<T> serializer;
    private T instance;
    private ConfigMetadata metadata;

    public ConfigManager(Class<T> configClass, Path path) {
        this.configClass = Objects.requireNonNull(configClass);
        this.serializer = new ConfigSerializer<>(this, Objects.requireNonNull(path));
        OptionRegistry.init();
        createInstance();

        this.metadata = MetadataScanner.scan(instance, configClass);
//        metadata.print();
    }

    public void createInstance() {
        this.instance = ReflectionUtils.createClassInstance(this.configClass);
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public void load() {
        this.serializer.load();
    }

    public void save() {
        this.serializer.save();
    }

    public T getInstance() {
        return instance;
    }

    public Class<T> getConfigClass() {
        return configClass;
    }

    public ConfigMetadata getMetadata() {
        return metadata;
    }
}
