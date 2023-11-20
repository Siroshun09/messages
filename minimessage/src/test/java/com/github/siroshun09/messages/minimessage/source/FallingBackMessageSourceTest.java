package com.github.siroshun09.messages.minimessage.source;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@SuppressWarnings("DataFlowIssue")
class FallingBackMessageSourceTest {

    @Test
    void testMiniMessage() {
        var primary = MiniMessageSource.create(StringMessageMap.create(Map.of("a", "1")));
        var secondary = MiniMessageSource.create(StringMessageMap.create(Map.of("b", "2")));
        var fallingBack = new FallingBackMiniMessageSource(primary, secondary);

        Assertions.assertTrue(fallingBack.hasMessage("a"));
        Assertions.assertTrue(fallingBack.hasMessage("b"));
        Assertions.assertFalse(fallingBack.hasMessage("c"));

        Assertions.assertEquals(Component.text("1"), fallingBack.getMessage("a"));
        Assertions.assertEquals(Component.text("2"), fallingBack.getMessage("b"));
        Assertions.assertEquals(Component.empty(), fallingBack.getMessage("c"));

        Assertions.assertEquals(Component.text("1"), fallingBack.builder().key("a").build());
        Assertions.assertEquals(Component.text("2"), fallingBack.builder().key("b").build());
        Assertions.assertEquals(Component.empty(), fallingBack.builder().key("c").build());

        Assertions.assertThrows(NullPointerException.class, () -> new FallingBackMiniMessageSource(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> new FallingBackMiniMessageSource(primary, null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.hasMessage(null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage(null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage(null, (TagResolver) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage(null, (TagResolver[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("a", (TagResolver) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("b", (TagResolver) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("c", (TagResolver) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("a", (TagResolver[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("b", (TagResolver[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> fallingBack.getMessage("c", (TagResolver[]) null));
    }
}
