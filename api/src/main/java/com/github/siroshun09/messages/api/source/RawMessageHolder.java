package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.NotNull;

/**
 * An interface indicating that messages are held in another type.
 *
 * @param <T> the "raw" message type
 */
public interface RawMessageHolder<T> {

    /**
     * Get the "raw" message from the key.
     *
     * @param key the key to get
     * @return the "raw" message
     */
    @NotNull T getRawMessage(@NotNull String key);

}
