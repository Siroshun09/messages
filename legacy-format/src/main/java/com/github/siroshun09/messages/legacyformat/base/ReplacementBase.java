package com.github.siroshun09.messages.legacyformat.base;

import com.github.siroshun09.messages.legacyformat.replacement.StringReplacement;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * An interface to create the {@link StringReplacement} from the {@link LegacyFormatSource}.
 */
public interface ReplacementBase extends Function<LegacyFormatSource, StringReplacement> {

    /**
     * Creates a new {@link ReplacementBase} from the key and the message key.
     *
     * @param key        the key
     * @param messageKey the message key
     * @return a new {@link ReplacementBase}
     * @see StringReplacement
     * @see LegacyFormatSource#getRawMessage(String)
     */
    @Contract(pure = true)
    static @NotNull ReplacementBase messageKey(@NotNull String key, @NotNull String messageKey) {
        return source -> new StringReplacement(key, source.getRawMessage(messageKey));
    }

    /**
     * Creates a new {@link ReplacementBase} from the key and the message key.
     *
     * @param key        the key
     * @param replacement the replacement
     * @return a new {@link ReplacementBase}
     * @see StringReplacement
     */
    @Contract(pure = true)
    static @NotNull ReplacementBase replacement(@NotNull String key, @NotNull String replacement) {
        return new BaseImpls.StaticReplacement(new StringReplacement(key, replacement));
    }

}
