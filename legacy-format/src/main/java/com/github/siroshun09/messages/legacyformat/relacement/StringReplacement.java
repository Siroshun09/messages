package com.github.siroshun09.messages.legacyformat.relacement;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record StringReplacement(@NotNull String target, @NotNull String replacement) {

    public StringReplacement {
        Objects.requireNonNull(target);
        Objects.requireNonNull(replacement);
    }

}
