package com.github.siroshun09.messages.legacyformat.localization;

import com.github.siroshun09.messages.api.localize.AbstractLocalization;
import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import com.github.siroshun09.messages.legacyformat.source.FallingBackLegacyFormatSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

/**
 * A {@link Localization} implementation for {@link LegacyFormatSource}.
 */
public class LegacyFormatLocalization extends AbstractLocalization<Component, LegacyFormatSource, FallingBackLegacyFormatSource> {

    /**
     * A constructor of {@link LegacyFormatLocalization}.
     *
     * @param defaultSource the default {@link LegacyFormatSource}
     */
    public LegacyFormatLocalization(@NotNull LegacyFormatSource defaultSource) {
        super(defaultSource);
    }

    @Override
    public @NotNull FallingBackLegacyFormatSource findSource(@NotNull Locale locale) {
        var source = Objects.requireNonNullElse(getSource(locale), defaultSource());
        return new FallingBackLegacyFormatSource(source, defaultSource());
    }
}
