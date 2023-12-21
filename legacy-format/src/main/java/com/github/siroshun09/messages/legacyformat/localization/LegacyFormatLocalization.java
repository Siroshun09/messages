package com.github.siroshun09.messages.legacyformat.localization;

import com.github.siroshun09.messages.api.localize.AbstractLocalization;
import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.legacyformat.source.FallingBackLegacyFormatSource;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Localization} implementation for {@link LegacyFormatSource}.
 */
public class LegacyFormatLocalization extends AbstractLocalization<Component, LegacyFormatSource, FallingBackLegacyFormatSource> {

    private final Function<@Nullable Object, @Nullable Locale> localeGetter;

    /**
     * A constructor of {@link LegacyFormatLocalization}.
     *
     * @param defaultSource the default {@link LegacyFormatSource}
     */
    public LegacyFormatLocalization(@NotNull LegacyFormatSource defaultSource) {
        this(defaultSource, obj -> null);
    }

    /**
     * A constructor of {@link LegacyFormatLocalization}.
     *
     * @param defaultSource the default {@link LegacyFormatSource}
     * @param localeGetter  the {@link Function} to get a {@link Locale} from the object
     */
    public LegacyFormatLocalization(@NotNull LegacyFormatSource defaultSource, @NotNull Function<@Nullable Object, @Nullable Locale> localeGetter) {
        super(defaultSource);
        this.localeGetter = localeGetter;
    }

    @Override
    public @NotNull FallingBackLegacyFormatSource findSource(@NotNull Locale locale) {
        var source = Objects.requireNonNullElse(getSource(locale), defaultSource());
        return new FallingBackLegacyFormatSource(source, defaultSource());
    }

    @Override
    public @NotNull FallingBackLegacyFormatSource findSource(@Nullable Object obj) {
        var locale = this.localeGetter.apply(obj);
        return locale != null ? this.findSource(locale) : new FallingBackLegacyFormatSource(defaultSource(), defaultSource());
    }
}
