package com.github.siroshun09.messages.api.base;

import com.github.siroshun09.messages.api.source.ComponentSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that extends {@link MessageBase} with {@link Component} message type and {@link ComponentSource}.
 *
 * @param <CS> the {@link ComponentSource} type
 */
@FunctionalInterface
public interface ComponentMessageBase<CS extends ComponentSource> extends MessageBase<Component, CS> {

    @Override
    default @NotNull PreparedComponentMessage source(@NotNull CS source) {
        return new PreparedComponentMessage(this.create(source));
    }

}
