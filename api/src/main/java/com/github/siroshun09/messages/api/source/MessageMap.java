package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

/**
 * An interface to hold the messages.
 *
 * @param <T> the message type
 */
public interface MessageMap<T> extends MessageSource<T> {

    /**
     * Adds the message to this {@link MessageMap}.
     *
     * @param key     the key of the message
     * @param message the message to add
     * @return this instance
     */
    @Contract("_, _ -> this")
    @NotNull MessageMap<T> addMessage(@NotNull String key, @NotNull T message);

    /**
     * Removes the message from this {@link MessageMap}.
     *
     * @param key the message key to remove
     * @return this instance
     */
    @Contract("_ -> this")
    @NotNull MessageMap<T> removeMessage(@NotNull String key);

    /**
     * Merges messages from the given {@link Map}.
     * <p>
     * This method adds messages that are not included in this {@link MessageMap}.
     *
     * @param other the {@link Map} to merge
     */
    void merge(@NotNull Map<String, T> other);

    /**
     * Merges messages from the given {@link Map} and collect missing messages.
     *
     * @param other the {@link Map} to merge
     * @return the messages that were not included in this {@link MessageMap}
     */
    @NotNull @Unmodifiable Map<String, T> mergeAndCollectMissingMessages(@NotNull Map<String, T> other);

    /**
     * Returns the unmodifiable message map.
     *
     * @return the unmodifiable message map
     */
    @NotNull @UnmodifiableView Map<String, T> asMap();

}
