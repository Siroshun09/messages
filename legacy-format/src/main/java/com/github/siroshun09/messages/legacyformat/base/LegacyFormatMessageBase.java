package com.github.siroshun09.messages.legacyformat.base;

import com.github.siroshun09.messages.api.base.ComponentMessageBase;
import com.github.siroshun09.messages.legacyformat.replacement.StringReplacement;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link ComponentMessageBase} for {@link LegacyFormatSource}.
 */
public interface LegacyFormatMessageBase extends ComponentMessageBase<LegacyFormatSource> {

    /**
     * Creates a new {@link LegacyFormatMessageBase} from the message key.
     *
     * @param messageKey the message key
     * @return a new {@link LegacyFormatMessageBase}
     * @see LegacyFormatSource#getMessage(String)
     */
    @Contract("_ -> new")
    static @NotNull LegacyFormatMessageBase messageKey(@NotNull String messageKey) {
        return new BaseImpls.MessageKey(Objects.requireNonNull(messageKey));
    }

    /**
     * Creates a new {@link LegacyFormatMessageBase} from the message key and the {@link StringReplacement}.
     *
     * @param messageKey the message key
     * @param replacement   the {@link StringReplacement}
     * @return a new {@link LegacyFormatMessageBase}
     * @see LegacyFormatSource#getMessage(String, StringReplacement)
     */
    @Contract("_, _ -> new")
    static @NotNull LegacyFormatMessageBase withStringReplacement(@NotNull String messageKey, @NotNull StringReplacement replacement) {
        return new BaseImpls.WithStringReplacement1(Objects.requireNonNull(messageKey), Objects.requireNonNull(replacement));
    }

    /**
     * Creates a new {@link LegacyFormatMessageBase} from the message key and the {@link StringReplacement}s.
     *
     * @param messageKey the message key
     * @param replacements  the {@link StringReplacement}s
     * @return a new {@link LegacyFormatMessageBase}
     * @see LegacyFormatSource#getMessage(String, StringReplacement[])
     */
    @Contract("_, _ -> new")
    static @NotNull LegacyFormatMessageBase withStringReplacement(@NotNull String messageKey, @NotNull StringReplacement @NotNull ... replacements) {
        return switch (replacements.length) {
            case 0 -> messageKey(messageKey);
            case 1 -> withStringReplacement(messageKey, replacements[0]);
            default -> new BaseImpls.WithStringReplacementN(Objects.requireNonNull(messageKey), Objects.requireNonNull(replacements));
        };
    }

    /**
     * Creates a new {@link LegacyFormatMessageBase} from the message key and the {@link ReplacementBase}.
     *
     * @param messageKey   the message key
     * @param replacementBase the {@link ReplacementBase}
     * @return a new {@link LegacyFormatMessageBase}
     * @see LegacyFormatSource#getMessage(String, StringReplacement)
     */
    @Contract("_, _ -> new")
    static @NotNull LegacyFormatMessageBase withReplacementBase(@NotNull String messageKey, @NotNull ReplacementBase replacementBase) {
        return new BaseImpls.WithReplacementBase1(Objects.requireNonNull(messageKey), Objects.requireNonNull(replacementBase));
    }

    /**
     * Creates a new {@link LegacyFormatMessageBase} from the message key and the {@link ReplacementBase}s.
     *
     * @param messageKey    the message key
     * @param replacementBases the {@link ReplacementBase}s
     * @return a new {@link LegacyFormatMessageBase}
     * @see LegacyFormatSource#getMessage(String, StringReplacement[])
     */
    @Contract("_, _ -> new")
    static @NotNull LegacyFormatMessageBase withReplacementBase(@NotNull String messageKey, @NotNull ReplacementBase @NotNull ... replacementBases) {
        return switch (replacementBases.length) {
            case 0 -> messageKey(messageKey);
            case 1 -> withReplacementBase(messageKey, replacementBases[0]);
            default ->
                    new BaseImpls.WithReplacementBaseN(Objects.requireNonNull(messageKey), Objects.requireNonNull(replacementBases));
        };
    }

}
