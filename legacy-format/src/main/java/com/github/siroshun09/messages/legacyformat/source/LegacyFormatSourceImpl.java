package com.github.siroshun09.messages.legacyformat.source;

import com.github.siroshun09.messages.api.source.StringMessageSource;
import com.github.siroshun09.messages.legacyformat.replacement.StringReplacement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class LegacyFormatSourceImpl implements LegacyFormatSource {

    private final StringMessageSource source;
    private final LegacyComponentSerializer serializer;

    LegacyFormatSourceImpl(@NotNull StringMessageSource source, @NotNull LegacyComponentSerializer serializer) {
        this.source = Objects.requireNonNull(source);
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public boolean hasMessage(@NotNull String key) {
        return source.hasMessage(key);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key) {
        return serializer.deserialize(source.getMessage(key));
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull StringReplacement replacement) {
        Objects.requireNonNull(replacement);
        return serializer.deserialize(source.getMessage(key).replace(replacement.target(), replacement.replacement()));
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull StringReplacement @NotNull ... replacements) {
        Objects.requireNonNull(replacements);
        var message = source.getMessage(key);

        for (var replacement : replacements) {
            message = message.replace(replacement.target(), replacement.replacement());
        }

        return serializer.deserialize(message);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull Iterable<StringReplacement> replacements) {
        Objects.requireNonNull(replacements);
        var message = source.getMessage(key);

        for (StringReplacement replacement : replacements) {
            message = message.replace(replacement.target(), replacement.replacement());
        }

        return serializer.deserialize(message);
    }

    @Override
    public @NotNull String getRawMessage(@NotNull String key) {
        return source.getMessage(key);
    }
}
