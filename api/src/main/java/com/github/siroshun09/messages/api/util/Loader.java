package com.github.siroshun09.messages.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.io.IOException;
import java.util.function.Function;

/**
 * Represents the {@link Function} that do IO related operations, especially loading.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface Loader<T, R> extends Function<T, R> {

    /**
     * Creates a {@link Loader}.
     *
     * @param loader an implementation of {@link Loader}
     * @param <T>    the type of the input to the function
     * @param <R>    the type of the result of the function
     * @return the given {@link Loader}
     */
    @SuppressWarnings("unchecked")
    static <T, R> @NotNull Loader<T, R> loader(@NotNull Loader<? super T, ? extends R> loader) {
        return (Loader<T, R>) loader;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param input the function argument
     * @return the function result
     * @throws IOException if I/O error occurred
     */
    @UnknownNullability R load(@NotNull T input) throws IOException;

    /**
     * {@inheritDoc}
     * <p>
     * This method <b>sneaky throws</b> {@link IOException}.
     *
     * @param t {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    default @UnknownNullability R apply(T t) {
        try {
            return load(t);
        } catch (IOException e) {
            return sneakyThrow(e);
        }
    }

    @Override
    default <V> @NotNull Loader<T, V> andThen(@NotNull Function<? super R, ? extends V> after) {
        return (T input) -> after.apply(apply(input));
    }

    @Override
    default <V> @NotNull Loader<V, R> compose(@NotNull Function<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    @SuppressWarnings("unchecked")
    private <A, E extends Throwable> A sneakyThrow(Throwable t) throws E {
        throw (E) t;
    }
}
