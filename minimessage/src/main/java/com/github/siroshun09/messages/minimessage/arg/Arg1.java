package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An interface to create {@link MiniMessageBase} from an argument.
 * 
 * @param <A1> the type of the 1st argument
 */
@FunctionalInterface
public interface Arg1<A1> extends Function<A1, MiniMessageBase> {

    /**
     * Creates {@link Arg1} from the message key and the function.
     * 
     * @param key the message key
     * @param a1 the {@link Function} for the 1st argument
     * @param <A1> the type of the 1st argument
     * @return a new {@link Arg1}
     */
    @Contract("_, _ -> new")
    static <A1> @NotNull Arg1<A1> arg1(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(a1);
        return new Arg1Impl<>(key, a1);
    }

    /**
     * Creates {@link MiniMessageBase} from an argument.
     * 
     * @param a1 the 1st argument
     * @return the {@link MiniMessageBase}
     */
    @Override
    @Contract("_ -> new")
    @NotNull MiniMessageBase apply(A1 a1);

}
