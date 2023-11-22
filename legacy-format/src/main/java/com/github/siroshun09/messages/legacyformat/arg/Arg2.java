package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.BiFunction;

/**
 * An interface to create {@link LegacyFormatMessageBase} from the 2 arguments.
 * 
 * @param <A1> the type of the 1st argument
 * @param <A2> the type of the 2nd argument
 */
@FunctionalInterface
public interface Arg2<A1, A2> extends BiFunction<A1, A2, LegacyFormatMessageBase> {

    /**
     * Creates {@link Arg2} from the message key and the functions.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param a2 the {@link Function} for the 2nd argument
     * @param <A1> the type of the 1st argument
     * @param <A2> the type of the 2nd argument
     * @return a new {@link Arg2}
     */
    @Contract("_, _, _ -> new")
    static <A1, A2> @NotNull Arg2<A1, A2> arg2(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1, @NotNull Function<? super A2, ? extends ReplacementBase> a2) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        Objects.requireNonNull(a2);
        return new Arg2Impl<>(key, a1, a2);
    }

    /**
     * Creates {@link LegacyFormatMessageBase} from the 2 arguments.
     * 
     * @param a1 the 1st argument
     * @param a2 the 2nd argument
     * @return the {@link LegacyFormatMessageBase}
     */
    @Override
    @Contract("_, _ -> new")
    @NotNull LegacyFormatMessageBase apply(A1 a1, A2 a2);

}
