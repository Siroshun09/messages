package com.github.siroshun09.messages.legacyformat.builder;

import com.github.siroshun09.messages.api.builder.MessageBuilder;
import com.github.siroshun09.messages.legacyformat.relacement.StringReplacement;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

    private final List<StringReplacement> replacements = new ArrayList<>();
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
     * Adds a {@link StringReplacement}.
     *
     * @param key   the string to replace
     * @param value the replacement
     * @return this {@link LegacyFormatMessageBuilder} instance
     */
    @Contract("_, _ -> this")
    public @NotNull LegacyFormatMessageBuilder placeholder(@NotNull String key, @NotNull String value) {
        this.replacements.add(new StringReplacement(key, value));
        return this;
    }


    /**
     * Adds a {@link StringReplacement}.
     *
     * @param replacement the replacement
     * @return this {@link LegacyFormatMessageBuilder} instance
     */
    @Contract("_-> this")
    public @NotNull LegacyFormatMessageBuilder placeholder(@NotNull StringReplacement replacement) {
        this.replacements.add(Objects.requireNonNull(replacement));
        return this;
    }

    /**
     * Adds placeholders.
     *
     * @param replacements the replacements
     * @return this {@link LegacyFormatMessageBuilder} instance
     */
    @Contract("_ -> this")
    public @NotNull LegacyFormatMessageBuilder placeholders(@NotNull StringReplacement @NotNull ... replacements) {
        Objects.requireNonNull(replacements);
        this.replacements.addAll(Arrays.asList(replacements));
        return this;
    }

    /**
     * Adds placeholders.
     *
     * @param replacements the replacements
     * @return this {@link LegacyFormatMessageBuilder} instance
     */
    @Contract("_ -> this")
    public @NotNull LegacyFormatMessageBuilder placeholders(@NotNull Collection<StringReplacement> replacements) {
        this.replacements.addAll(Objects.requireNonNull(replacements));
        return this;
    }

    @Override
    public @NotNull Component build() {
        return source.getMessage(key, replacements);
    }

    @Override
    public void send(@NotNull Audience target) {
        Objects.requireNonNull(target);
        target.sendMessage(build());
    }
}
