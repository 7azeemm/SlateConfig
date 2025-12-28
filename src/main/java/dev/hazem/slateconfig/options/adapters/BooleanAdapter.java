package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.options.OptionType;
import dev.hazem.slateconfig.options.OptionTypeAdapter;

public class BooleanAdapter implements OptionTypeAdapter<Boolean> {
    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.BOOLEAN;
    }
}
