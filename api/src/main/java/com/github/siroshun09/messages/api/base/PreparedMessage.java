package com.github.siroshun09.messages.api.base;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * An interface to hold the message that is ready for sending to {@link Audience}s.
 *
 * @param <M> the message type
 */
public interface PreparedMessage<M> {

    /**
     * Gets the message.
     *
     * @return the message
     */
    @NotNull M message();

    /**
     * Sends the message to the {@link Audience}.
     *
     * @param target the {@link Audience}
     */
    void send(@NotNull Audience target);

    /**
     * Sends the message to the {@link Audience} using {@link Audience#sendActionBar(Component)}
     *
     * @param target the {@link Audience}
     */
    void sendActionBar(@NotNull Audience target);

}
