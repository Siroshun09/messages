package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link LegacyFormatMessageBase} from an argument.
 * 
 * @param <A1> the type of the 1st argument
 */
@FunctionalInterface
public interface Arg1<A1> extends Function<A1, LegacyFormatMessageBase> {

    /**
     * Creates {@link Arg1} from the message key and the function.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param <A1> the type of the 1st argument
     * @return a new {@link Arg1}
     */
    @Contract("_, _ -> new")
    static <A1> @NotNull Arg1<A1> arg1(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        return new Arg1Impl<>(key, a1);
    }

    /**
     * Creates {@link LegacyFormatMessageBase} from an argument.
     * 
     * @param a1 the 1st argument
     * @return the {@link LegacyFormatMessageBase}
     */
    @Override
    @Contract("_ -> new")
    @NotNull LegacyFormatMessageBase apply(A1 a1);

}
