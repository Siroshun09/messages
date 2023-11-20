package com.github.siroshun09.messages.minimessage.localization;

import com.github.siroshun09.messages.api.localize.AbstractLocalization;
import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.minimessage.source.FallingBackMiniMessageSource;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

/**
 * A {@link Localization} implementation for {@link MiniMessageSource}.
 */
public class MiniMessageLocalization extends AbstractLocalization<Component, MiniMessageSource, FallingBackMiniMessageSource> {

    /**
     * A constructor of {@link MiniMessageLocalization}.
     *
     * @param defaultSource the default {@link MiniMessageSource}
     */
    public MiniMessageLocalization(@NotNull MiniMessageSource defaultSource) {
        super(defaultSource);
    }

    @Override
    public @NotNull FallingBackMiniMessageSource findSource(@NotNull Locale locale) {
        var source = Objects.requireNonNullElse(getSource(locale), defaultSource());
        return new FallingBackMiniMessageSource(source, defaultSource());
    }
}
