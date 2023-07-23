package com.github.siroshun09.messages.api.source.fallback;

import com.github.siroshun09.messages.api.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A record that implements {@link FallingBackMessageSource} for {@link MiniMessageSource}.
 *
 * @param primarySource  the primary {@link MiniMessageSource}
 * @param fallbackSource the secondary {@link MiniMessageSource}
 */
public record FallingBackMiniMessageSource(@NotNull MiniMessageSource primarySource,
                                           @NotNull MiniMessageSource fallbackSource) implements MiniMessageSource, FallingBackMessageSource<Component, MiniMessageSource> {

    /**
     * Creates a new {@link FallingBackMiniMessageSource}.
     *
     * @param primarySource  the primary {@link MiniMessageSource}
     * @param fallbackSource the secondary {@link MiniMessageSource}
     */
    public FallingBackMiniMessageSource {
        Objects.requireNonNull(primarySource);
        Objects.requireNonNull(fallbackSource);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver tagResolver) {
        if (primarySource.hasMessage(key)) {
            return primarySource.getMessage(key, tagResolver);
        } else {
            return fallbackSource.getMessage(key, tagResolver);
        }
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver... tagResolvers) {
        if (primarySource.hasMessage(key)) {
            return primarySource.getMessage(key, tagResolvers);
        } else {
            return fallbackSource.getMessage(key, tagResolvers);
        }
    }
}
