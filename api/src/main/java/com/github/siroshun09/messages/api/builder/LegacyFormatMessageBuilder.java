package com.github.siroshun09.messages.api.builder;

import com.github.siroshun09.messages.api.source.LegacyFormatSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A {@link MessageBuilder} for {@link LegacyFormatSource}.
 */
public final class LegacyFormatMessageBuilder implements MessageBuilder<Component, LegacyFormatMessageBuilder> {

    /**
     * Creates a new {@link LegacyFormatMessageBuilder}.
     *
     * @param source the {@link LegacyFormatSource} to build a message
     * @return a new {@link LegacyFormatMessageBuilder}
     */
    @Contract("_ -> new")
    public static @NotNull LegacyFormatMessageBuilder create(@NotNull LegacyFormatSource source) {
        return new LegacyFormatMessageBuilder(source);
    }

    private final LegacyFormatSource source;

    private final Map<String, String> placeholders = new HashMap<>();
    private String key;

    private LegacyFormatMessageBuilder(@NotNull LegacyFormatSource source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    @Contract("_ -> this")
    public @NotNull LegacyFormatMessageBuilder key(@NotNull String key) {
        this.key = Objects.requireNonNull(key);
        return this;
    }

    /**
     * Adds a placeholder.
     *
     * @param key   the string to replace
     * @param value the replacement
     * @return this {@link LegacyFormatMessageBuilder} instance
     * @see LegacyFormatSource#getMessage(String, Map)
     */
    @Contract("_, _ -> this")
    public @NotNull LegacyFormatMessageBuilder placeholder(@NotNull String key, @NotNull String value) {
        this.placeholders.put(Objects.requireNonNull(key), Objects.requireNonNull(value));
        return this;
    }

    /**
     * Adds placeholders.
     *
     * @param placeholders the placeholder map
     * @return this {@link LegacyFormatMessageBuilder} instance
     * @see LegacyFormatSource#getMessage(String, Map)
     */
    @Contract("_ -> this")
    public @NotNull LegacyFormatMessageBuilder placeholders(@NotNull Map<String, String> placeholders) {
        Objects.requireNonNull(placeholders);
        for (var entry : placeholders.entrySet()) {
            placeholder(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public @NotNull Component build() {
        return source.getMessage(key, placeholders);
    }

    @Override
    public void send(@NotNull Audience target) {
        Objects.requireNonNull(target);
        target.sendMessage(build());
    }
}
