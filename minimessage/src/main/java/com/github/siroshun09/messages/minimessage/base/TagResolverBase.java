package com.github.siroshun09.messages.minimessage.base;

import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * An interface to create the {@link TagResolver} from the {@link MiniMessageSource}.
 */
public interface TagResolverBase extends Function<MiniMessageSource, TagResolver> {

    /**
     * Creates a new {@link TagResolverBase} from the key and the message key.
     *
     * @param key        the key
     * @param messageKey the message key
     * @return a new {@link TagResolverBase}
     * @see Placeholder#component(String, ComponentLike)
     * @see MiniMessageSource#getMessage(String)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull TagResolverBase messageKey(@TagPattern @NotNull String key, @NotNull String messageKey) {
        return new BaseImpls.MessageKeyTagResolverBase(key, messageKey);
    }

    /**
     * Creates a new {@link TagResolverBase} from the key and the {@link MiniMessageBase}.
     *
     * @param key  the key
     * @param base the {@link MiniMessageBase}
     * @return a new {@link TagResolverBase}
     * @see Placeholder#component(String, ComponentLike)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull TagResolverBase messageBase(@TagPattern @NotNull String key, @NotNull MiniMessageBase base) {
        return new BaseImpls.MessageBaseTagResolverBase(key, base);
    }

    /**
     * Creates a new {@link TagResolverBase} from the key and the {@link Component}.
     *
     * @param key       the key
     * @param component the {@link Component}
     * @return a new {@link TagResolverBase}
     * @see Placeholder#component(String, ComponentLike)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull TagResolverBase component(@TagPattern @NotNull String key, @NotNull Component component) {
        return tagResolver(Placeholder.component(key, component));
    }

    /**
     * Creates a new {@link TagResolverBase} from the {@link TagResolver}.
     *
     * @param resolver the {@link TagResolver}
     * @return a new {@link TagResolverBase}
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull TagResolverBase tagResolver(@NotNull TagResolver resolver) {
        return new BaseImpls.StaticTagResolverBase(resolver);
    }

    @Override
    @NotNull TagResolver apply(@NotNull MiniMessageSource source);

}
