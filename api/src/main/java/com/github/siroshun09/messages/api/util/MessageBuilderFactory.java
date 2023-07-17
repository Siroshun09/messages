package com.github.siroshun09.messages.api.util;

import com.github.siroshun09.messages.api.builder.MessageBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Represents the {@link Supplier} that create a new {@link MessageBuilder}.
 *
 * @param <B> the builder type
 */
@FunctionalInterface
public interface MessageBuilderFactory<B extends MessageBuilder<?, B>> extends Supplier<B> {

    /**
     * Creates a new {@link MessageBuilder}.
     *
     * @return a new {@link MessageBuilder}
     */
    @NotNull B create();

    @Override
    default @NotNull B get() {
        return create();
    }
}
