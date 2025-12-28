package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.options.OptionType;
import dev.hazem.slateconfig.options.OptionTypeAdapter;

public class EnumAdapter implements OptionTypeAdapter<Enum<?>> {
    @Override
    @SuppressWarnings("unchecked")
    public Class<Enum<?>> getType() {
        return (Class<Enum<?>>) (Class<?>) Enum.class;
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.ENUM;
    }
}
