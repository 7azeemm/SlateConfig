package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.options.OptionType;
import dev.hazem.slateconfig.options.OptionTypeAdapter;

public class StringAdapter implements OptionTypeAdapter<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.STRING;
    }
}
