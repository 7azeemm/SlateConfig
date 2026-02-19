package dev.hazem.slateconfig.options;

import dev.hazem.slateconfig.options.adapters.BooleanAdapter;
import dev.hazem.slateconfig.options.adapters.EnumAdapter;
import dev.hazem.slateconfig.options.adapters.NumberAdapter;
import dev.hazem.slateconfig.options.adapters.StringAdapter;
import dev.hazem.slateconfig.utils.ReflectionUtils;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class OptionRegistry {
    private static final Map<Class<?>, Class<? extends OptionAdapter<?>>> ADAPTERS = new ConcurrentHashMap<>();

    public static void init() {
        ADAPTERS.putAll(Map.ofEntries(
                Map.entry(String.class, StringAdapter.class),
                Map.entry(Enum.class, EnumAdapter.class),

                Map.entry(Boolean.class, BooleanAdapter.class),
                Map.entry(boolean.class, BooleanAdapter.class),

                Map.entry(Integer.class, NumberAdapter.class),
                Map.entry(int.class, NumberAdapter.class),
                Map.entry(Long.class, NumberAdapter.class),
                Map.entry(long.class, NumberAdapter.class),
                Map.entry(Float.class, NumberAdapter.class),
                Map.entry(float.class, NumberAdapter.class),
                Map.entry(Double.class, NumberAdapter.class),
                Map.entry(double.class, NumberAdapter.class),
                Map.entry(Short.class, NumberAdapter.class),
                Map.entry(short.class, NumberAdapter.class),
                Map.entry(Byte.class, NumberAdapter.class),
                Map.entry(byte.class, NumberAdapter.class)
        ));
    }

    public static @Nullable Class<?> resolve(Class<?> type) {
        return ADAPTERS.get(type.isEnum() ? Enum.class : type);
    }
}
