package com.github.siroshun09.messages.api.base;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A record to holds {@link Component} message.
 *
 * @param message the message
 */
public record PreparedComponentMessage(@NotNull Component message) implements PreparedMessage<Component> {

    /**
     * A constructor of {@link PreparedComponentMessage}.
     *
     * @param message the message
     */
    public PreparedComponentMessage {
        Objects.requireNonNull(message);
    }

    @Override
    public void send(@NotNull Audience target) {
        target.sendMessage(this.message);
    }

    @Override
    public void sendActionBar(@NotNull Audience target) {
        target.sendActionBar(this.message);
    }
}
