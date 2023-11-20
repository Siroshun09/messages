package com.github.siroshun09.messages.legacyformat.builder;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class LegacyFormatMessageBuilderTest {

    private static final Map<String, String> PLACEHOLDERS = Map.of("%hello%", "Hello", "{world}", "World");
    private static final Component EXPECTED_HELLO_WORLD = Component.text("Hello, World!");

    @Test
    void testReturnValue() {
        var builder = newBuilder();
        Assertions.assertSame(builder, builder.key("test"));
        Assertions.assertSame(builder, builder.placeholder("%hello%", "Hello"));
        Assertions.assertSame(builder, builder.placeholders(PLACEHOLDERS));
    }

    @Test
    void testBuild() {
        Assertions.assertEquals(
                Component.text("Hello, {world}!"),
                newBuilder()
                        .key("key")
                        .placeholder("%hello%", "Hello")
                        .build()
        );

        Assertions.assertEquals(
                EXPECTED_HELLO_WORLD,
                newBuilder()
                        .key("key")
                        .placeholders(PLACEHOLDERS)
                        .build()
        );
    }

    @Test
    void testSend() {
        var received = new AtomicBoolean();

        newBuilder()
                .key("key")
                .placeholders(PLACEHOLDERS)
                .send(new Audience() {
                    @Override
                    public void sendMessage(@NotNull Component message) {
                        Assertions.assertEquals(EXPECTED_HELLO_WORLD, message);
                        received.set(true);
                    }
                });

        Assertions.assertTrue(received.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testNull() {
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().key(null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().placeholder(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().placeholder("test", null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().placeholders(null));

        var nullKeyMap = new HashMap<String, String>();
        nullKeyMap.put(null, "test");
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().placeholders(nullKeyMap));

        var nullValueMap = new HashMap<String, String>();
        nullValueMap.put("test", null);
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().placeholders(nullValueMap));

        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().build());
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().key("test").send(null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().send(Audience.empty()));
    }

    private @NotNull LegacyFormatMessageBuilder newBuilder() {
        return LegacyFormatMessageBuilder.create(LegacyFormatSource.create(StringMessageMap.create(Map.of("key", "%hello%, {world}!"))));
    }
}
