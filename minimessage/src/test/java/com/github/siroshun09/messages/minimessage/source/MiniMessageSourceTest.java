package com.github.siroshun09.messages.minimessage.source;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@SuppressWarnings("DataFlowIssue")
class MiniMessageSourceTest {

    private static final String MINI_MESSAGE_HELLO_WORLD = "<green>Hello</green><gray>, </gray><#123456>World</#123456><red><bold>!!</bold></red>";
    private static final String MINI_MESSAGE_HELLO_WORLD_WITH_PLACEHOLDER = "<green><hello></green><gray>, </gray><#123456>World</#123456><red><bold>!!</bold></red>";
    private static final String MINI_MESSAGE_HELLO_WORLD_WITH_TWO_PLACEHOLDERS = "<green><hello></green><gray>, </gray><#123456><world></#123456><red><bold>!!</bold></red>";
    private static final Component EXPECTED_HELLO_WORLD =
            Component.text()
                    .append(Component.text("Hello", NamedTextColor.GREEN))
                    .append(Component.text(", ", NamedTextColor.GRAY))
                    .append(Component.text("World", TextColor.fromHexString("#123456")))
                    .append(Component.text("!!", Style.style(NamedTextColor.RED, TextDecoration.BOLD))).build();


    @Test
    void testCreate() {
        Assertions.assertDoesNotThrow(() -> MiniMessageSource.create(StringMessageMap.create()));
        Assertions.assertDoesNotThrow(() -> MiniMessageSource.create(StringMessageMap.create(), MiniMessage.miniMessage()));
        Assertions.assertThrows(NullPointerException.class, () -> MiniMessageSource.create(null));
        Assertions.assertThrows(NullPointerException.class, () -> MiniMessageSource.create(StringMessageMap.create(), null));
    }

    @Test
    void testHas() {
        var source = MiniMessageSource.create(StringMessageMap.create(Map.of("a", MINI_MESSAGE_HELLO_WORLD)));
        Assertions.assertTrue(source.hasMessage("a"));

        Assertions.assertThrows(NullPointerException.class, () -> source.hasMessage(null));
    }

    @Test
    void testGet() {
        var source = MiniMessageSource.create(StringMessageMap.create(Map.of("a", MINI_MESSAGE_HELLO_WORLD, "b", MINI_MESSAGE_HELLO_WORLD_WITH_PLACEHOLDER, "c", MINI_MESSAGE_HELLO_WORLD_WITH_TWO_PLACEHOLDERS)));
        Assertions.assertEquals(EXPECTED_HELLO_WORLD, source.getMessage("a"));
        Assertions.assertEquals(EXPECTED_HELLO_WORLD, source.getMessage("b", Placeholder.unparsed("hello", "Hello")));
        Assertions.assertEquals(EXPECTED_HELLO_WORLD, source.getMessage("c", Placeholder.unparsed("hello", "Hello"), Placeholder.parsed("world", "World")));

        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage(null));
        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage("a", (TagResolver) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.getMessage("a", (TagResolver[]) null));
    }
}
