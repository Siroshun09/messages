package com.github.siroshun09.messages.api.localization;

import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.api.source.MessageSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@SuppressWarnings("DataFlowIssue")
abstract class AbstractLocalizationTest<T, M extends MessageSource<T>> {

    protected AbstractLocalizationTest() {
    }

    @Test
    void testModification() {
        var localization = createLocalization();
        var source = createSource();
        localization.addSource(Locale.JAPANESE, source);
        Assertions.assertSame(source, localization.getSource(Locale.JAPANESE)); // ja
        Assertions.assertSame(source, localization.getSource(Locale.JAPAN)); // ja_JP

        localization.removeSource(Locale.JAPANESE);
        Assertions.assertNull(localization.getSource(Locale.JAPANESE));

        localization.addSource(Locale.JAPANESE, source);
        Assertions.assertSame(source, localization.getSource(Locale.JAPANESE));
        localization.addSource(Locale.UK, source);
        Assertions.assertSame(source, localization.getSource(Locale.UK));
        localization.clearSources();
        Assertions.assertTrue(localization.sourceMap().isEmpty());

        Assertions.assertThrows(NullPointerException.class, () -> localization.getSource(null));
        Assertions.assertThrows(NullPointerException.class, () -> localization.addSource(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> localization.addSource(Locale.JAPANESE, null));
        Assertions.assertThrows(NullPointerException.class, () -> localization.removeSource(null));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> localization.sourceMap().remove(Locale.JAPANESE));
    }

    protected abstract @NotNull Localization<T, M, ?> createLocalization();

    protected abstract @NotNull M createSource();

}
