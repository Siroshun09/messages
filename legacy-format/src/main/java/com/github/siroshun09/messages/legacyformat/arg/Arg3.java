package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link LegacyFormatMessageBase} from the 3 arguments.
 * 
 * @param <A1> the type of the 1st argument
 * @param <A2> the type of the 2nd argument
 * @param <A3> the type of the 3rd argument
 */
@FunctionalInterface
public interface Arg3<A1, A2, A3> {

    /**
     * Creates {@link Arg3} from the message key and the functions.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param a2 the {@link Function} for the 2nd argument
     * @param a3 the {@link Function} for the 3rd argument
     * @param <A1> the type of the 1st argument
     * @param <A2> the type of the 2nd argument
     * @param <A3> the type of the 3rd argument
     * @return a new {@link Arg3}
     */
    @Contract("_, _, _, _ -> new")
    static <A1, A2, A3> @NotNull Arg3<A1, A2, A3> arg3(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1, @NotNull Function<? super A2, ? extends ReplacementBase> a2, @NotNull Function<? super A3, ? extends ReplacementBase> a3) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        Objects.requireNonNull(a2);
        Objects.requireNonNull(a3);
        return new Arg3Impl<>(key, a1, a2, a3);
    }

    /**
     * Creates {@link LegacyFormatMessageBase} from the 3 arguments.
     * 
     * @param a1 the 1st argument
     * @param a2 the 2nd argument
     * @param a3 the 3rd argument
     * @return the {@link LegacyFormatMessageBase}
     */
    @Contract("_, _, _ -> new")
    @NotNull LegacyFormatMessageBase apply(A1 a1, A2 a2, A3 a3);

}
