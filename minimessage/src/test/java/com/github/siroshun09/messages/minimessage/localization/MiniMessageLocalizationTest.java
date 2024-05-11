package com.github.siroshun09.messages.minimessage.localization;

import com.github.siroshun09.messages.api.localize.Localization;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import com.github.siroshun09.messages.test.shared.localization.AbstractLocalizationTest;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("DataFlowIssue")
class MiniMessageLocalizationTest extends AbstractLocalizationTest<Component, MiniMessageSource> {

    @Test
    void testCreate() {
        Assertions.assertThrows(NullPointerException.class, () -> new MiniMessageLocalization(null));
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
    protected @NotNull Localization<Component, MiniMessageSource, ?> createLocalization() {
        return new MiniMessageLocalization(createSource());
    }

    @Override
    protected @NotNull MiniMessageSource createSource() {
        return MiniMessageSource.create(StringMessageMap.create());
    }

    @Test
    void testTranslator() {
        var key = "test";
        var format = "<aqua>Arguments: <argument:0>, <arg:1>";
        var children = List.of(Component.text("Additional", NamedTextColor.RED), Component.text("Component", NamedTextColor.WHITE, TextDecoration.BOLD));
        var component = Component.translatable(key, Component.text(1), Component.text(2)).children(children);
        var localization = new MiniMessageLocalization(MiniMessageSource.create(StringMessageMap.create(Map.of(key, format))));

        var expected = Component.text("Arguments: 1, 2", NamedTextColor.AQUA).children(children);
        var translated = localization.toTranslator(Key.key("test")).translate(component, Locale.getDefault());
        Assertions.assertEquals(expected, translated);
    }
}
