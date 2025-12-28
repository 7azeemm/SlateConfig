package dev.hazem.slateconfig.options;

public interface OptionTypeAdapter<T> {
    Class<T> getType();

    OptionType getOptionType();
}
