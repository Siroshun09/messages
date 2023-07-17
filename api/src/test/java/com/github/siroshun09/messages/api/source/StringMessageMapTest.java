package com.github.siroshun09.messages.api.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

@SuppressWarnings("DataFlowIssue")
class StringMessageMapTest {

    @Test
    void testCreate() {
        Assertions.assertTrue(StringMessageMap.create().asMap().isEmpty());
        Assertions.assertFalse(StringMessageMap.create(Map.of("a", "b")).asMap().isEmpty());
        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create(null));
    }

    @Test
    void testHas() {
        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create().hasMessage(null));
    }

    @Test
    void testGet() {
        Assertions.assertEquals("b", StringMessageMap.create(Map.of("a", "b")).getMessage("a"));
        Assertions.assertTrue(StringMessageMap.create().getMessage("a").isEmpty());

        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create().getMessage(null));
    }

    @Test
    void testAdd() {
        Assertions.assertTrue(StringMessageMap.create().addMessage("a", "b").hasMessage("a"));

        // Overwrite the message
        Assertions.assertEquals(StringMessageMap.create(Map.of("a", "b")).addMessage("a", "c").getMessage("a"), "c");

        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create().addMessage(null, ""));
        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create().addMessage("", null));
    }

    @Test
    void testRemove() {
        Assertions.assertFalse(StringMessageMap.create(Map.of("a", "b")).removeMessage("a").hasMessage("a"));

        Assertions.assertThrows(NullPointerException.class, () -> StringMessageMap.create().removeMessage(null));
    }
}
