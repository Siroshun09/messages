package com.github.siroshun09.messages.api.base;

import com.github.siroshun09.messages.api.source.MessageSource;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that creates the message from the {@link MessageSource}.
 *
 * @param <M>  the message type
 * @param <MS> the message source type
 */
public interface MessageBase<M, MS extends MessageSource<M>> {

    /**
     * Creates the message and gets it as {@link PreparedMessage}.
     *
     * @param source the {@link MessageSource}
     * @return the {@link PreparedMessage}
     * @see #create(MessageSource)
     */
    @NotNull PreparedMessage<M> source(@NotNull MS source);

    /**
     * Creates the message
     *
     * @param source the {@link MessageSource}
     * @return the created message
     */
    @NotNull M create(@NotNull MS source);

}
