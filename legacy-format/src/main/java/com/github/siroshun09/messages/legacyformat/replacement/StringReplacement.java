package com.github.siroshun09.messages.legacyformat.replacement;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A record that holds strings for {@link String#replace(CharSequence, CharSequence)}.
 *
 * @param target the sequence of char values to be replaced
 * @param replacement the replacement
 */
public record StringReplacement(@NotNull String target, @NotNull String replacement) {

    /**
     * A constructor of {@link StringReplacement}.
     *
     * @param target the sequence of char values to be replaced
     * @param replacement the replacement
     */
    public StringReplacement {
        Objects.requireNonNull(target);
        Objects.requireNonNull(replacement);
    }

}
