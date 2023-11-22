package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link MiniMessageBase} from the 8 arguments.
 * 
 * @param <A1> the type of the 1st argument
 * @param <A2> the type of the 2nd argument
 * @param <A3> the type of the 3rd argument
 * @param <A4> the type of the 4th argument
 * @param <A5> the type of the 5th argument
 * @param <A6> the type of the 6th argument
 * @param <A7> the type of the 7th argument
 * @param <A8> the type of the 8th argument
 */
@FunctionalInterface
public interface Arg8<A1, A2, A3, A4, A5, A6, A7, A8> {

    /**
     * Creates {@link Arg8} from the message key and the functions.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param a2 the {@link Function} for the 2nd argument
     * @param a3 the {@link Function} for the 3rd argument
     * @param a4 the {@link Function} for the 4th argument
     * @param a5 the {@link Function} for the 5th argument
     * @param a6 the {@link Function} for the 6th argument
     * @param a7 the {@link Function} for the 7th argument
     * @param a8 the {@link Function} for the 8th argument
     * @param <A1> the type of the 1st argument
     * @param <A2> the type of the 2nd argument
     * @param <A3> the type of the 3rd argument
     * @param <A4> the type of the 4th argument
     * @param <A5> the type of the 5th argument
     * @param <A6> the type of the 6th argument
     * @param <A7> the type of the 7th argument
     * @param <A8> the type of the 8th argument
     * @return a new {@link Arg8}
     */
    @Contract("_, _, _, _, _, _, _, _, _ -> new")
    static <A1, A2, A3, A4, A5, A6, A7, A8> @NotNull Arg8<A1, A2, A3, A4, A5, A6, A7, A8> arg8(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1, @NotNull Function<? super A2, ? extends TagResolverBase> a2, @NotNull Function<? super A3, ? extends TagResolverBase> a3, @NotNull Function<? super A4, ? extends TagResolverBase> a4, @NotNull Function<? super A5, ? extends TagResolverBase> a5, @NotNull Function<? super A6, ? extends TagResolverBase> a6, @NotNull Function<? super A7, ? extends TagResolverBase> a7, @NotNull Function<? super A8, ? extends TagResolverBase> a8) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        Objects.requireNonNull(a2);
        Objects.requireNonNull(a3);
        Objects.requireNonNull(a4);
        Objects.requireNonNull(a5);
        Objects.requireNonNull(a6);
        Objects.requireNonNull(a7);
        Objects.requireNonNull(a8);
        return new Arg8Impl<>(key, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    /**
     * Creates {@link MiniMessageBase} from the 8 arguments.
     * 
     * @param a1 the 1st argument
     * @param a2 the 2nd argument
     * @param a3 the 3rd argument
     * @param a4 the 4th argument
     * @param a5 the 5th argument
     * @param a6 the 6th argument
     * @param a7 the 7th argument
     * @param a8 the 8th argument
     * @return the {@link MiniMessageBase}
     */
    @Contract("_, _, _, _, _, _, _, _ -> new")
    @NotNull MiniMessageBase apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8);

}
