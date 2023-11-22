package com.github.siroshun09.messages.minimessage.base;

import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An interface to create a {@link TagResolverBase} from the argument.
 *
 * @param <T> the type of the argument
 */
public interface Placeholder<T> extends Function<T, TagResolverBase> {

    /**
     * Creates a new {@link Placeholder} from the key and the {@link Function} that creates {@link MiniMessageBase} from the argument.
     *
     * @param key      the key
     * @param function the {@link Function} that creates {@link MiniMessageBase} from the argument
     * @param <T>      the type of the argument
     * @return a new {@link Placeholder}
     * @see TagResolverBase#messageBase(String, MiniMessageBase)
     */
    @Contract("_, _ -> new")
    static <T> @NotNull Placeholder<T> messageBase(@TagPattern @NotNull String key, @NotNull Function<? super T, ? extends MiniMessageBase> function) {
        return new BaseImpls.MessageBasePlaceholder<>(Objects.requireNonNull(key), Objects.requireNonNull(function));
    }

    /**
     * Creates a new {@link Placeholder} from the key and the {@link Function} that creates {@link Component} from the argument.
     *
     * @param key      the key
     * @param function the {@link Function} that creates {@link Component} from the argument
     * @param <T>      the type of the argument
     * @return a new {@link Placeholder}
     * @see TagResolverBase#component(String, Component)
     */
    @Contract("_, _ -> new")
    static <T> @NotNull Placeholder<T> component(@TagPattern @NotNull String key, @NotNull Function<? super T, ? extends Component> function) {
        return new BaseImpls.ComponentPlaceholder<>(key, function);
    }

    /**
     * Creates a new {@link Placeholder} from the key and the {@link Function} that creates {@link Component} from the argument and {@link MiniMessageSource}.
     *
     * @param key      the key
     * @param function the {@link Function} that creates {@link Component} from the argument and {@link MiniMessageSource}
     * @param <T>      the type of the argument
     * @return a new {@link Placeholder}
     * @see net.kyori.adventure.text.minimessage.tag.resolver.Placeholder#component(String, ComponentLike)
     */
    @Contract("_, _ -> new")
    static <T> @NotNull Placeholder<T> componentWithSource(@TagPattern @NotNull String key, @NotNull BiFunction<? super T, ? super MiniMessageSource, ? extends Component> function) {
        return new BaseImpls.ComponentWithSourcePlaceholder<>(key, function);
    }

    @Override
    @NotNull TagResolverBase apply(T t);

}
