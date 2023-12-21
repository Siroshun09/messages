package com.github.siroshun09.messages.minimessage.localization;

import com.github.siroshun09.messages.api.localize.AbstractLocalization;
import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.minimessage.source.FallingBackMiniMessageSource;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
}
