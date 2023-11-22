package com.github.siroshun09.messages.legacyformat.base;

import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An interface to create a {@link ReplacementBase} from the argument.
 *
 * @param <T> the type of the argument
 */
public interface Placeholder<T> extends Function<T, ReplacementBase> {

    /**
     * Creates a new {@link Placeholder} from the key and the {@link Function} that creates {@link String} from the argument.
     *
     * @param key      the key
     * @param function the {@link Function} that creates {@link String} from the argument
     * @param <T>      the type of the argument
     * @return a new {@link Placeholder}
     * @see com.github.siroshun09.messages.legacyformat.relacement.StringReplacement
     */
    @Contract("_, _ -> new")
    static <T> @NotNull Placeholder<T> string(@NotNull String key, @NotNull Function<? super T, String> function) {
        return new BaseImpls.StringPlaceholder<>(key, function);
    }

    /**
     * Creates a new {@link Placeholder} from the key and the {@link Function} that creates {@link String} from the argument and {@link LegacyFormatSource}.
     *
     * @param key      the key
     * @param function the {@link Function} that creates {@link String} from the argument and {@link LegacyFormatSource}
     * @param <T>      the type of the argument
     * @return a new {@link Placeholder}
     * @see com.github.siroshun09.messages.legacyformat.relacement.StringReplacement
     */
    @Contract("_, _ -> new")
    static <T> @NotNull Placeholder<T> stringWithSource(@NotNull String key, @NotNull BiFunction<? super T, ? super LegacyFormatSource, String> function) {
        return new BaseImpls.StringWithSourcePlaceholder<>(key, function);
    }

    @Override
    @NotNull ReplacementBase apply(T t);

}
