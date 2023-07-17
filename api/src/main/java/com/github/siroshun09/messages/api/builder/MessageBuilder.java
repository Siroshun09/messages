package com.github.siroshun09.messages.api.builder;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for building the message.
 *
 * @param <M> the message type
 * @param <B> the builder type
 */
public interface MessageBuilder<M, B extends MessageBuilder<M, B>> {

    /**
     * Sets the message key.
     *
     * @param key the message key
     * @return this {@link MessageBuilder} instance
     */
    @Contract("_ -> this")
    @NotNull B key(@NotNull String key);

    /**
     * Builds a message.
     *
     * @return the message
     */
    @NotNull M build();

    /**
     * Sends a created message to the {@link Audience}.
     *
     * @param target the {@link Audience} who receives the message
     * @see #build()
     */
    void send(@NotNull Audience target);

}
