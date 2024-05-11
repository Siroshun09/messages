package com.github.siroshun09.messages.minimessage.base;

import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

final class BaseImpls {

    record MessageKey(@NotNull String key) implements MiniMessageBase {
        @Override
        public @NotNull Component create(@NotNull MiniMessageSource source) {
            return source.getMessage(this.key);
        }
    }

    record WithTagResolverBase1(@NotNull String key,
                                @NotNull TagResolverBase resolverBase) implements MiniMessageBase {
        @Override
        public @NotNull Component create(@NotNull MiniMessageSource source) {
            return source.getMessage(this.key, this.resolverBase.apply(source));
        }
    }

    record WithTagResolverBaseN(@NotNull String key,
                                @NotNull TagResolverBase @NotNull [] resolverBases) implements MiniMessageBase {
        @Override
        public @NotNull Component create(@NotNull MiniMessageSource source) {
            var resolvers = new TagResolver[resolverBases.length];

            for (int i = 0; i < this.resolverBases.length; i++) {
                resolvers[i] = this.resolverBases[i].apply(source);
            }

            return source.getMessage(this.key, resolvers);
        }
    }

    record WithTagResolver1(@NotNull String key, @NotNull TagResolver resolver) implements MiniMessageBase {
        @Override
        public @NotNull Component create(@NotNull MiniMessageSource source) {
            return source.getMessage(this.key, this.resolver);
        }
    }

    record WithTagResolverN(@NotNull String key,
                            @NotNull TagResolver @NotNull [] resolvers) implements MiniMessageBase {
        @Override
        public @NotNull Component create(@NotNull MiniMessageSource source) {
            return source.getMessage(this.key, this.resolvers);
        }
    }

    record MessageKeyTagResolverBase(@TagPattern @NotNull String key,
                                     @NotNull String messageKey) implements TagResolverBase {
        @Override
        public @NotNull TagResolver apply(@NotNull MiniMessageSource source) {
            return net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component(this.key, source.getMessage(this.messageKey));
        }
    }

    record MessageBaseTagResolverBase(@TagPattern @NotNull String key,
                                      @NotNull MiniMessageBase base) implements TagResolverBase {
        @Override
        public @NotNull TagResolver apply(@NotNull MiniMessageSource source) {
            return net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component(this.key, this.base.create(source));
        }
    }

    record StaticTagResolverBase(@NotNull TagResolver tagResolver) implements TagResolverBase {
        @Override
        public @NotNull TagResolver apply(@NotNull MiniMessageSource source) {
            return this.tagResolver;
        }
    }

    record MessageBasePlaceholder<T>(@TagPattern @NotNull String key,
                                     @NotNull Function<? super T, ? extends MiniMessageBase> function) implements Placeholder<T> {
        @Override
        public @NotNull TagResolverBase apply(T t) {
            return TagResolverBase.messageBase(this.key, this.function.apply(t));
        }
    }

    record ComponentPlaceholder<T>(@TagPattern @NotNull String key,
                                   @NotNull Function<? super T, ? extends Component> function) implements Placeholder<T> {
        @Override
        public @NotNull TagResolverBase apply(T t) {
            return TagResolverBase.component(this.key, this.function.apply(t));
        }
    }

    record ComponentWithSourcePlaceholder<T>(@TagPattern @NotNull String key,
                                             @NotNull BiFunction<? super T, ? super MiniMessageSource, ? extends Component> function) implements Placeholder<T> {
        @Override
        public @NotNull TagResolverBase apply(T t) {
            return source -> net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component(this.key, this.function.apply(t, source));
        }
    }

    private BaseImpls() {
        throw new UnsupportedOperationException();
    }
}
