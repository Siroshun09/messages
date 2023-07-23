package com.github.siroshun09.messages.api.source;

import com.github.siroshun09.messages.api.builder.MiniMessageBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that converting the string to {@link Component} using {@link MiniMessage}.
 */
public interface MiniMessageSource extends ComponentSource {

    /**
     * Creates a {@link MiniMessageSource} with {@link StringMessageSource}.
     * <p>
     * The created {@link MiniMessageSource} uses {@link MiniMessage#miniMessage()}.
     *
     * @param source the source of the string messages
     * @return new {@link MiniMessageSource}
     */
    @Contract("_ -> new")
    static @NotNull MiniMessageSource create(@NotNull StringMessageSource source) {
        return create(source, MiniMessage.miniMessage());
    }

    /**
     * Creates a {@link MiniMessageSource} with {@link StringMessageSource} and {@link MiniMessage}.
     *
     * @param source       the source of the string messages
     * @param deserializer the {@link MiniMessage}
     * @return new {@link MiniMessageSource}
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MiniMessageSource create(@NotNull StringMessageSource source, @NotNull MiniMessage deserializer) {
        return new MiniMessageSourceImpl(source, deserializer);
    }

    /**
     * Gets the message that deserialize the string by using {@link MiniMessage#deserialize(String, TagResolver)}.
     *
     * @param key         the key to get the message
     * @param tagResolver the {@link TagResolver} that is passed to {@link MiniMessage#deserialize(String, TagResolver)}
     * @return the message that deserialize the string by using {@link MiniMessage#deserialize(String, TagResolver)}
     */
    @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver tagResolver);

    /**
     * Gets the message that deserialize the string by using {@link MiniMessage#deserialize(String, TagResolver...)}.
     *
     * @param key          the key to get the message
     * @param tagResolvers the {@link TagResolver}s that are passed to {@link MiniMessage#deserialize(String, TagResolver...)}
     * @return the message that deserialize the string by using {@link MiniMessage#deserialize(String, TagResolver...)}
     */
    @NotNull Component getMessage(@NotNull String key, @NotNull TagResolver... tagResolvers);

    /**
     * Creates a {@link MiniMessageBuilder} using this {@link MiniMessageSource}.
     *
     * @return a {@link MiniMessageBuilder} using this {@link MiniMessageSource}
     */
    default @NotNull MiniMessageBuilder builder() {
        return MiniMessageBuilder.create(this);
    }
}
