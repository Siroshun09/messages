package com.github.siroshun09.messages.minimessage.localization;

import com.github.siroshun09.messages.api.localize.AbstractLocalization;
import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.minimessage.source.FallingBackMiniMessageSource;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Localization} implementation for {@link MiniMessageSource}.
 */
public class MiniMessageLocalization extends AbstractLocalization<Component, MiniMessageSource, FallingBackMiniMessageSource> {

    private final Function<@Nullable Object, @Nullable Locale> localeGetter;

    /**
     * A constructor of {@link MiniMessageLocalization}.
     *
     * @param defaultSource the default {@link MiniMessageSource}
     */
    public MiniMessageLocalization(@NotNull MiniMessageSource defaultSource) {
        this(defaultSource, obj -> null);
    }

    /**
     * A constructor of {@link MiniMessageLocalization}.
     *
     * @param defaultSource the default {@link MiniMessageSource}
     * @param localeGetter  the {@link Function} to get a {@link Locale} from the object
     */
    public MiniMessageLocalization(@NotNull MiniMessageSource defaultSource, @NotNull Function<@Nullable Object, @Nullable Locale> localeGetter) {
        super(defaultSource);
        this.localeGetter = localeGetter;
    }

    @Override
    public @NotNull FallingBackMiniMessageSource findSource(@NotNull Locale locale) {
        var source = Objects.requireNonNullElse(getSource(locale), defaultSource());
        return new FallingBackMiniMessageSource(source, defaultSource());
    }

    @Override
    public @NotNull FallingBackMiniMessageSource findSource(@Nullable Object obj) {
        var locale = this.localeGetter.apply(obj);
        return locale != null ? this.findSource(locale) : new FallingBackMiniMessageSource(defaultSource(), defaultSource());
    }

    /**
     * Creates a {@link Translator} that uses this {@link MiniMessageLocalization}.
     * <p>
     * Arguments of {@link TranslatableComponent} can be obtained by argument tags (e.g. <code>&lt;argument:0&gt;</code>, <code>&lt;arg:1&gt;</code>).
     *
     * @param key the {@link Key} for returning by {@link Translator#name()}
     * @return a {@link Translator}
     */
    // See https://github.com/KyoriPowered/adventure/pull/972
    @ApiStatus.Experimental
    public @NotNull Translator toTranslator(@NotNull Key key) {
        return new TranslatorImpl(key, this);
    }

    private record TranslatorImpl(@NotNull Key name,
                                  @NotNull MiniMessageLocalization localization) implements Translator {

        private static final boolean HAS_ARGUMENTS_METHOD;

        static {
            boolean hasArgumentsMethod;

            try {
                TranslatableComponent.class.getDeclaredMethod("arguments");
                hasArgumentsMethod = true;
            } catch (NoSuchMethodException ignored) {
                hasArgumentsMethod = false;
            }

            HAS_ARGUMENTS_METHOD = hasArgumentsMethod;
        }

        @SuppressWarnings("deprecation")
        private static @NotNull List<? extends ComponentLike> getArguments(@NotNull TranslatableComponent component) {
            return HAS_ARGUMENTS_METHOD ? component.arguments() : component.args();
        }

        @Override
        public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
            var source = this.localization.findSource(locale);
            return source.hasMessage(key) ? new MessageFormat(source.getRawMessage(key), locale) : null;
        }

        @Override
        public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
            var source = this.localization.findSource(locale);
            var key = component.key();
            return source.hasMessage(key) ? this.render(source, key, component) : null;
        }

        private Component render(@NotNull MiniMessageSource source, @NotNull String key, @NotNull TranslatableComponent component) {
            var arguments = getArguments(component);
            var result = arguments.isEmpty() ? source.getMessage(key) : source.getMessage(key, new ArgumentTag(arguments));

            return component.children().isEmpty() ? result : result.children(component.children());
        }

        private record ArgumentTag(List<? extends ComponentLike> arguments) implements TagResolver {

            private static final String ARGUMENT_NAME = "argument";
            private static final String ARG_NAME = "arg";

            @Override
            public @NotNull Tag resolve(final @NotNull String name, final @NotNull ArgumentQueue arguments, final @NotNull Context ctx) throws ParsingException {
                final int index = arguments.popOr("No argument number provided").asInt().orElseThrow(() -> ctx.newException("Invalid argument number", arguments));

                if (index < 0 || index >= this.arguments.size()) {
                    throw ctx.newException("Invalid argument number", arguments);
                }

                return Tag.inserting(this.arguments.get(index));
            }

            @Override
            public boolean has(final @NotNull String name) {
                return name.equals(ARGUMENT_NAME) || name.equals(ARG_NAME);
            }
        }
    }
}
