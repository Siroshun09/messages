package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An interface to get the message from two {@link MessageSource}s.
 *
 * @param <T> the message type
 * @param <M> the {@link MessageSource} type
 */
public interface FallingBackMessageSource<T, M extends MessageSource<T>> extends MessageSource<T> {

    @Override
    default boolean hasMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        return primarySource().hasMessage(key) || fallbackSource().hasMessage(key);
    }

    @Override
    default @NotNull T getMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        return primarySource().hasMessage(key) ? primarySource().getMessage(key) : fallbackSource().getMessage(key);
    }

    /**
     * Gets the primary {@link MessageSource} that gets the message first.
     *
     * @return the primary {@link MessageSource} that gets the message first
     */
    @NotNull M primarySource();

    /**
     * Gets the secondary {@link MessageSource} that gets the message if {@link #primarySource()} does not have it.
     *
     * @return the secondary {@link MessageSource} that gets the message if {@link #primarySource()} does not have it
     */
    @NotNull M fallbackSource();

}
