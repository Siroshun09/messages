package com.github.siroshun09.messages.minimessage.builder;

import com.github.siroshun09.messages.api.builder.MessageBuilder;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A {@link MessageBuilder} for {@link MiniMessageSource}.
 */
public final class MiniMessageBuilder implements MessageBuilder<Component, MiniMessageBuilder> {

    /**
     * Creates a new {@link MiniMessageBuilder}.
     *
     * @param source the {@link MiniMessageSource} to build a message
     * @return a new {@link MiniMessageBuilder}
     */
    @Contract("_ -> new")
    public static @NotNull MiniMessageBuilder create(@NotNull MiniMessageSource source) {
        return new MiniMessageBuilder(source);
    }

    private final MiniMessageSource source;

    private final List<TagResolver> tagResolvers = new ArrayList<>();
    private String key;

    private MiniMessageBuilder(@NotNull MiniMessageSource source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    @Contract("_ -> this")
    public @NotNull MiniMessageBuilder key(@NotNull String key) {
        this.key = Objects.requireNonNull(key);
        return this;
    }

    /**
     * Adds a {@link TagResolver}.
     *
     * @param tagResolver {@link TagResolver} to add
     * @return this {@link MiniMessageBuilder} instance
     */
    @Contract("_ -> this")
    public @NotNull MiniMessageBuilder tagResolver(@NotNull TagResolver tagResolver) {
        tagResolvers.add(Objects.requireNonNull(tagResolver));
        return this;
    }

    /**
     * Adds {@link TagResolver}s.
     *
     * @param tagResolvers {@link TagResolver}s to add
     * @return this {@link MiniMessageBuilder} instance
     */
    @Contract("_ -> this")
    public @NotNull MiniMessageBuilder tagResolvers(@NotNull TagResolver @NotNull ... tagResolvers) {
        Objects.requireNonNull(tagResolvers);
        for (var tagResolver : tagResolvers) {
            this.tagResolver(tagResolver);
        }
        return this;
    }

    /**
     * Adds {@link TagResolver}s.
     *
     * @param tagResolvers {@link TagResolver}s to add
     * @return this {@link MiniMessageBuilder} instance
     */
    @Contract("_ -> this")
    public @NotNull MiniMessageBuilder tagResolvers(@NotNull Collection<? extends TagResolver> tagResolvers) {
        Objects.requireNonNull(tagResolvers).forEach(this::tagResolver);
        return this;
    }

    @Override
    public @NotNull Component build() {
        Objects.requireNonNull(key, "key is not set");

        if (tagResolvers.isEmpty()) {
            return source.getMessage(key);
        } else if (tagResolvers.size() == 1) {
            return source.getMessage(key, tagResolvers.get(0));
        } else {
            return source.getMessage(key, tagResolvers.toArray(TagResolver[]::new));
        }
    }

    /**
     * Build the message as a {@link TagResolver}.
     *
     * @param placeholder the placeholder that passed to {@link Placeholder#component(String, ComponentLike)}
     * @return the created {@link TagResolver}
     */
    @Contract("_ -> new")
    public @NotNull TagResolver asPlaceholder(@TagPattern @NotNull String placeholder) {
        return Placeholder.component(Objects.requireNonNull(placeholder), build());
    }

    @Override
    public void send(@NotNull Audience target) {
        Objects.requireNonNull(target);
        target.sendMessage(build());
    }
}
