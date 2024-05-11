package com.github.siroshun09.messages.legacyformat.source;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.legacyformat.replacement.StringReplacement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@SuppressWarnings("DataFlowIssue")
class LegacyFormatSourceTest {

    private static final List<StringReplacement> PLACEHOLDERS = List.of(new StringReplacement("%hello%", "Hello"), new StringReplacement("{world}", "World"));

    private static final String LEGACY_FORMAT_HELLO_WORLD = "&aHello&7, &#123456World&c&l!!";
    private static final String LEGACY_FORMAT_HELLO_WORLD_WITH_PLACEHOLDERS = "&a%hello%&7, &#123456{world}&c&l!!";
    private static final Component EXPECTED_HELLO_WORLD =
        Component.text()
            .append(Component.text("Hello", NamedTextColor.GREEN))
            .append(Component.text(", ", NamedTextColor.GRAY))
            .append(Component.text("World", TextColor.fromHexString("#123456")))
            .append(Component.text("!!", Style.style(NamedTextColor.RED, TextDecoration.BOLD))).build();

    @Test
    void testCreate() {
        Assertions.assertDoesNotThrow(() -> LegacyFormatSource.create(StringMessageMap.create()));
        Assertions.assertDoesNotThrow(() -> LegacyFormatSource.create(StringMessageMap.create(), LegacyComponentSerializer.legacySection()));
        Assertions.assertThrows(NullPointerException.class, () -> LegacyFormatSource.create(null));
        Assertions.assertThrows(NullPointerException.class, () -> LegacyFormatSource.create(StringMessageMap.create(), null));
    }

    @Test
    void testHas() {
        var source = LegacyFormatSource.create(StringMessageMap.create(Map.of("a", LEGACY_FORMAT_HELLO_WORLD)));
        Assertions.assertTrue(source.hasMessage("a"));

        Assertions.assertThrows(NullPointerException.class, () -> source.hasMessage(null));
    }

    @Test
    void testGet() {
        var source = LegacyFormatSource.create(StringMessageMap.create(Map.of("a", LEGACY_FORMAT_HELLO_WORLD, "b", LEGACY_FORMAT_HELLO_WORLD_WITH_PLACEHOLDERS)));
        Assertions.assertEquals(EXPECTED_HELLO_WORLD, source.getMessage("a"));
        Assertions.assertEquals(EXPECTED_HELLO_WORLD, source.getMessage("b", PLACEHOLDERS));

        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage(null));
        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage("a", (StringReplacement) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage("a", (StringReplacement[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage("a", (Iterable<StringReplacement>) null));
    }
}
