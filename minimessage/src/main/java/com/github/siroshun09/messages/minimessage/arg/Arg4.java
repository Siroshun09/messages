package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link MiniMessageBase} from the 4 arguments.
 * 
 * @param <A1> the type of the 1st argument
 * @param <A2> the type of the 2nd argument
 * @param <A3> the type of the 3rd argument
 * @param <A4> the type of the 4th argument
 */
@FunctionalInterface
public interface Arg4<A1, A2, A3, A4> {

    /**
     * Creates {@link Arg4} from the message key and the functions.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param a2 the {@link Function} for the 2nd argument
     * @param a3 the {@link Function} for the 3rd argument
     * @param a4 the {@link Function} for the 4th argument
     * @param <A1> the type of the 1st argument
     * @param <A2> the type of the 2nd argument
     * @param <A3> the type of the 3rd argument
     * @param <A4> the type of the 4th argument
     * @return a new {@link Arg4}
     */
    @Contract("_, _, _, _, _ -> new")
    static <A1, A2, A3, A4> @NotNull Arg4<A1, A2, A3, A4> arg4(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1, @NotNull Function<? super A2, ? extends TagResolverBase> a2, @NotNull Function<? super A3, ? extends TagResolverBase> a3, @NotNull Function<? super A4, ? extends TagResolverBase> a4) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        Objects.requireNonNull(a2);
        Objects.requireNonNull(a3);
        Objects.requireNonNull(a4);
        return new Arg4Impl<>(key, a1, a2, a3, a4);
    }

    /**
     * Creates {@link MiniMessageBase} from the 4 arguments.
     * 
     * @param a1 the 1st argument
     * @param a2 the 2nd argument
     * @param a3 the 3rd argument
     * @param a4 the 4th argument
     * @return the {@link MiniMessageBase}
     */
    @Contract("_, _, _, _ -> new")
    @NotNull MiniMessageBase apply(A1 a1, A2 a2, A3 a3, A4 a4);

}
