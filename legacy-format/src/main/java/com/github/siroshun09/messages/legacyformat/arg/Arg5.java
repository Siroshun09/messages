package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link LegacyFormatMessageBase} from the 5 arguments.
 * 
 * @param <A1> the type of the 1st argument
 * @param <A2> the type of the 2nd argument
 * @param <A3> the type of the 3rd argument
 * @param <A4> the type of the 4th argument
 * @param <A5> the type of the 5th argument
 */
@FunctionalInterface
public interface Arg5<A1, A2, A3, A4, A5> {

    /**
     * Creates {@link Arg5} from the message key and the functions.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param a2 the {@link Function} for the 2nd argument
     * @param a3 the {@link Function} for the 3rd argument
     * @param a4 the {@link Function} for the 4th argument
     * @param a5 the {@link Function} for the 5th argument
     * @param <A1> the type of the 1st argument
     * @param <A2> the type of the 2nd argument
     * @param <A3> the type of the 3rd argument
     * @param <A4> the type of the 4th argument
     * @param <A5> the type of the 5th argument
     * @return a new {@link Arg5}
     */
    @Contract("_, _, _, _, _, _ -> new")
    static <A1, A2, A3, A4, A5> @NotNull Arg5<A1, A2, A3, A4, A5> arg5(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1, @NotNull Function<? super A2, ? extends ReplacementBase> a2, @NotNull Function<? super A3, ? extends ReplacementBase> a3, @NotNull Function<? super A4, ? extends ReplacementBase> a4, @NotNull Function<? super A5, ? extends ReplacementBase> a5) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        Objects.requireNonNull(a2);
        Objects.requireNonNull(a3);
        Objects.requireNonNull(a4);
        Objects.requireNonNull(a5);
        return new Arg5Impl<>(key, a1, a2, a3, a4, a5);
    }

    /**
     * Creates {@link LegacyFormatMessageBase} from the 5 arguments.
     * 
     * @param a1 the 1st argument
     * @param a2 the 2nd argument
     * @param a3 the 3rd argument
     * @param a4 the 4th argument
     * @param a5 the 5th argument
     * @return the {@link LegacyFormatMessageBase}
     */
    @Contract("_, _, _, _, _ -> new")
    @NotNull LegacyFormatMessageBase apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5);

}
