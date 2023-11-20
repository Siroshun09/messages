package com.github.siroshun09.messages.legacyformat.localization;

import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import com.github.siroshun09.messages.test.shared.localization.AbstractLocalizationTest;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@SuppressWarnings("DataFlowIssue")
class LegacyFormatLocalizationTest extends AbstractLocalizationTest<Component, LegacyFormatSource> {

    @Test
    void testCreate() {
        Assertions.assertThrows(NullPointerException.class, () -> new LegacyFormatLocalization(null));
    }

    @Test
    void testFallback() {
        var localization = createLocalization();
        var source = createSource();
        localization.addSource(Locale.JAPANESE, source);

        Assertions.assertSame(source, localization.findSource(Locale.JAPANESE).primarySource());
        Assertions.assertSame(localization.defaultSource(), localization.findSource(Locale.UK).primarySource());

        Assertions.assertThrows(NullPointerException.class, () -> localization.findSource(null));
    }

    @Override
    protected @NotNull Localization<Component, LegacyFormatSource, ?> createLocalization() {
        return new LegacyFormatLocalization(createSource());
    }

    @Override
    protected @NotNull LegacyFormatSource createSource() {
        return LegacyFormatSource.create(StringMessageMap.create());
    }
}
