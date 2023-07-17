package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.NotNull;

/**
 * An interface to get the message.
 *
 * @param <T> the message type
 */
public interface MessageSource<T> {

    /**
     * Checks if the {@code key} is contained in this source.
     *
     * @param key the key to check
     * @return {@code true} if the {@code key} is contained in this source, otherwise {@code false}
     */
    boolean hasMessage(@NotNull String key);

    /**
     * Gets the message of the specified key.
     * <p>
     * If the message is not found, this method will return the alternate value (see implementation's docs).
     *
     * @param key the key to get the message
     * @return the message or the alternate value
     */
    @NotNull T getMessage(@NotNull String key);

}
