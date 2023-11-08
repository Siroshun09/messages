package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An abstract implementation of {@link MessageMap}.
 *
 * @param <T> the message type
 */
public abstract class AbstractMessageMap<T> implements MessageMap<T> {

    private final Map<String, T> messageMap;

    /**
     * A constructor of this class.
     *
     * @param messageMap a map to hold messages
     */
    protected AbstractMessageMap(@NotNull Map<String, T> messageMap) {
        this.messageMap = Objects.requireNonNull(messageMap);
    }

    @Override
    public boolean hasMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        return messageMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method will return {@link #empty()} if the message is not found.
     *
     * @param key {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull T getMessage(@NotNull String key) {
        return messageMap.getOrDefault(key, empty());
    }

    @Override
    public @NotNull MessageMap<T> addMessage(@NotNull String key, @NotNull T message) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(message);
        messageMap.put(key, message);
        return this;
    }

    @Override
    public @NotNull MessageMap<T> removeMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        messageMap.remove(key);
        return this;
    }

    @Override
    public void merge(@NotNull Map<String, T> other) {
        for (var entry : other.entrySet()) {
            this.messageMap.putIfAbsent(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public @NotNull @Unmodifiable Map<String, T> mergeAndCollectMissingMessages(@NotNull Map<String, T> other) {
        var missingMessages = new LinkedHashMap<String, T>();

        for (var entry : other.entrySet()) {
            if (this.messageMap.putIfAbsent(entry.getKey(), entry.getValue()) == null) {
                missingMessages.put(entry.getKey(), entry.getValue());
            }
        }

        return Collections.unmodifiableMap(missingMessages);
    }

    @Override
    public @NotNull @UnmodifiableView Map<String, T> asMap() {
        return Collections.unmodifiableMap(messageMap);
    }

    /**
     * Returns the empty content to use when the message is not found.
     *
     * @return the empty content
     */
    protected abstract @NotNull T empty();
}
