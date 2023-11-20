package com.github.siroshun09.messages.legacyformat.source;

import com.github.siroshun09.messages.api.source.FallingBackMessageSource;
import com.github.siroshun09.messages.legacyformat.relacement.StringReplacement;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A record that implements {@link FallingBackMessageSource} for {@link LegacyFormatSource}.
 *
 * @param primarySource  the primary {@link LegacyFormatSource}
 * @param fallbackSource the secondary {@link LegacyFormatSource}
 */
public record FallingBackLegacyFormatSource(@NotNull LegacyFormatSource primarySource,
                                            @NotNull LegacyFormatSource fallbackSource) implements LegacyFormatSource, FallingBackMessageSource<Component, LegacyFormatSource> {

    /**
     * Creates a new {@link FallingBackLegacyFormatSource}.
     *
     * @param primarySource  the primary {@link LegacyFormatSource}
     * @param fallbackSource the secondary {@link LegacyFormatSource}
     */
    public FallingBackLegacyFormatSource {
        Objects.requireNonNull(primarySource);
        Objects.requireNonNull(fallbackSource);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull StringReplacement replacement) {
        if (primarySource.hasMessage(key)) {
            return primarySource.getMessage(key, replacement);
        } else {
            return fallbackSource.getMessage(key, replacement);
        }
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull StringReplacement @NotNull ... replacements) {
        if (primarySource.hasMessage(key)) {
            return primarySource.getMessage(key, replacements);
        } else {
            return fallbackSource.getMessage(key, replacements);
        }
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull Iterable<StringReplacement> replacements) {
        if (primarySource.hasMessage(key)) {
            return primarySource.getMessage(key, replacements);
        } else {
            return fallbackSource.getMessage(key, replacements);
        }
    }
}
