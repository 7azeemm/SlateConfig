package dev.hazem.slateconfig.options.adapters;

import dev.hazem.slateconfig.options.OptionType;
import dev.hazem.slateconfig.options.OptionTypeAdapter;

public class NumberAdapter<T extends Number> implements OptionTypeAdapter<T> {
    private final Class<T> type;

    public NumberAdapter(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public OptionType getOptionType() {
        return OptionType.NUMBER;
    }
}
