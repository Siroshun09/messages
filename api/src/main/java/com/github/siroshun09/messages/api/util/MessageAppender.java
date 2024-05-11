package com.github.siroshun09.messages.api.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * An interface to append messages to something.
 *
 * @param <T> the type of destination that will be appended to
 * @param <M> the type of messages
 */
@FunctionalInterface
public interface MessageAppender<T, M> {

    /**
     * Appends messages to the target.
     *
     * @param target     a target
     * @param messageMap a message map to append
     * @throws IOException if I/O error occurred
     */
    void append(@NotNull T target, @NotNull Map<String, M> messageMap) throws IOException;

}
