package dev.hazem.slateconfig.options;

import dev.hazem.slateconfig.options.adapters.BooleanAdapter;
import dev.hazem.slateconfig.options.adapters.EnumAdapter;
import dev.hazem.slateconfig.options.adapters.NumberAdapter;
import dev.hazem.slateconfig.options.adapters.StringAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class OptionTypeRegistry {
    private static final Map<Class<?>, OptionTypeAdapter<?>> ADAPTERS = new ConcurrentHashMap<>();

    public static void init() {
        ADAPTERS.putAll(Map.ofEntries(
                Map.entry(String.class, new StringAdapter()),
                Map.entry(Enum.class, new EnumAdapter()),

                Map.entry(Boolean.class, new BooleanAdapter()),
                Map.entry(boolean.class, new BooleanAdapter()),

                Map.entry(Integer.class, new NumberAdapter<>(Integer.class)),
                Map.entry(int.class, new NumberAdapter<>(Integer.class)),
                Map.entry(Long.class, new NumberAdapter<>(Long.class)),
                Map.entry(long.class, new NumberAdapter<>(Long.class)),
                Map.entry(Float.class, new NumberAdapter<>(Float.class)),
                Map.entry(float.class, new NumberAdapter<>(Float.class)),
                Map.entry(Double.class, new NumberAdapter<>(Double.class)),
                Map.entry(double.class, new NumberAdapter<>(Double.class)),
                Map.entry(Short.class, new NumberAdapter<>(Short.class)),
                Map.entry(short.class, new NumberAdapter<>(Short.class)),
                Map.entry(Byte.class, new NumberAdapter<>(Byte.class)),
                Map.entry(byte.class, new NumberAdapter<>(Byte.class))
        ));
    }

    public static boolean isSupported(Class<?> type) {
        return resolve(type) != null;
    }

    @SuppressWarnings("unchecked")
    public static <T> OptionTypeAdapter<T> resolve(Class<T> type) {
        if (type.isEnum()) return (OptionTypeAdapter<T>) ADAPTERS.get(Enum.class);

        OptionTypeAdapter<?> adapter = ADAPTERS.get(type);
        if (adapter != null) {
            return (OptionTypeAdapter<T>) adapter;
        }

        return null;
    }
}
