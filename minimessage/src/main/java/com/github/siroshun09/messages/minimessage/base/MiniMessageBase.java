package com.github.siroshun09.messages.minimessage.base;

import com.github.siroshun09.messages.api.base.ComponentMessageBase;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link ComponentMessageBase} for {@link MiniMessageSource}.
 */
public interface MiniMessageBase extends ComponentMessageBase<MiniMessageSource> {

    /**
     * Creates a new {@link MiniMessageBase} from the message key.
     *
     * @param messageKey the message key
     * @return a new {@link MiniMessageBase}
     * @see MiniMessageSource#getMessage(String)
     */
    @Contract("_ -> new")
    static @NotNull MiniMessageBase messageKey(@NotNull String messageKey) {
        return new BaseImpls.MessageKey(Objects.requireNonNull(messageKey));
    }

    /**
     * Creates a new {@link MiniMessageBase} from the message key and the {@link TagResolver}.
     *
     * @param messageKey the message key
     * @param resolver   the {@link TagResolver}
     * @return a new {@link MiniMessageBase}
     * @see MiniMessageSource#getMessage(String, TagResolver)
     */
    @Contract("_, _ -> new")
    static @NotNull MiniMessageBase withTagResolver(@NotNull String messageKey, @NotNull TagResolver resolver) {
        return new BaseImpls.WithTagResolver1(Objects.requireNonNull(messageKey), Objects.requireNonNull(resolver));
    }

    /**
     * Creates a new {@link MiniMessageBase} from the message key and the {@link TagResolver}s.
     *
     * @param messageKey the message key
     * @param resolvers  the {@link TagResolver}s
     * @return a new {@link MiniMessageBase}
     * @see MiniMessageSource#getMessage(String, TagResolver[])
     */
    @Contract("_, _ -> new")
    static @NotNull MiniMessageBase withTagResolver(@NotNull String messageKey, @NotNull TagResolver @NotNull ... resolvers) {
        return switch (resolvers.length) {
            case 0 -> messageKey(messageKey);
            case 1 -> withTagResolver(messageKey, resolvers[0]);
            default -> new BaseImpls.WithTagResolverN(Objects.requireNonNull(messageKey), Objects.requireNonNull(resolvers));
        };
    }

    /**
     * Creates a new {@link MiniMessageBase} from the message key and the {@link TagResolverBase}.
     *
     * @param messageKey   the message key
     * @param resolverBase the {@link TagResolverBase}
     * @return a new {@link MiniMessageBase}
     * @see MiniMessageSource#getMessage(String, TagResolver)
     */
    @Contract("_, _ -> new")
    static @NotNull MiniMessageBase withTagResolverBase(@NotNull String messageKey, @NotNull TagResolverBase resolverBase) {
        return new BaseImpls.WithTagResolverBase1(Objects.requireNonNull(messageKey), Objects.requireNonNull(resolverBase));
    }

    /**
     * Creates a new {@link MiniMessageBase} from the message key and the {@link TagResolverBase}s.
     *
     * @param messageKey    the message key
     * @param resolverBases the {@link TagResolverBase}s
     * @return a new {@link MiniMessageBase}
     * @see MiniMessageSource#getMessage(String, TagResolver[])
     */
    @Contract("_, _ -> new")
    static @NotNull MiniMessageBase withTagResolverBase(@NotNull String messageKey, @NotNull TagResolverBase @NotNull ... resolverBases) {
        return switch (resolverBases.length) {
            case 0 -> messageKey(messageKey);
            case 1 -> withTagResolverBase(messageKey, resolverBases[0]);
            default -> new BaseImpls.WithTagResolverBaseN(Objects.requireNonNull(messageKey), Objects.requireNonNull(resolverBases));
        };
    }

}
