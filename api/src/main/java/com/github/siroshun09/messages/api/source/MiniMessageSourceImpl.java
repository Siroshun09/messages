package com.github.siroshun09.messages.api.source;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class MiniMessageSourceImpl implements MiniMessageSource {

    private final StringMessageSource source;
    private final MiniMessage deserializer;

    MiniMessageSourceImpl(@NotNull StringMessageSource source, @NotNull MiniMessage deserializer) {
        this.source = Objects.requireNonNull(source);
        this.deserializer = Objects.requireNonNull(deserializer);
    }

    @Override
    public boolean hasMessage(@NotNull String key) {
        return source.hasMessage(key);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key) {
        return deserializer.deserialize(source.getMessage(key));
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver tagResolver) {
        Objects.requireNonNull(tagResolver);
        return deserializer.deserialize(source.getMessage(key), tagResolver);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver... tagResolvers) {
        Objects.requireNonNull(tagResolvers);
        return deserializer.deserialize(source.getMessage(key), tagResolvers);
    }
}
