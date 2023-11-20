package com.github.siroshun09.messages.minimessage.builder;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.minimessage.source.MiniMessageSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class MiniMessageBuilderTest {

    private static final TagResolver HELLO = Placeholder.component("hello", Component.text("Hello"));
    private static final TagResolver WORLD = Placeholder.component("world", Component.text("World"));
    private static final Component EXPECTED_HELLO_WORLD = Component.text("Hello, World!");

    @Test
    void testReturnValue() {
        var builder = newBuilder();
        Assertions.assertSame(builder, builder.key("test"));
        Assertions.assertSame(builder, builder.tagResolver(HELLO));

        var tagResolvers = List.of(HELLO, WORLD);

        Assertions.assertSame(builder, builder.tagResolvers(tagResolvers.toArray(TagResolver[]::new)));
        Assertions.assertSame(builder, builder.tagResolvers(tagResolvers));
    }

    @Test
    void testBuild() {
        Assertions.assertEquals(
                Component.text("Hello, <world>!"),
                newBuilder()
                        .key("key")
                        .tagResolver(HELLO)
                        .build()
        );

        Assertions.assertEquals(
                EXPECTED_HELLO_WORLD,
                newBuilder()
                        .key("key")
                        .tagResolvers(HELLO, WORLD)
                        .build()
        );

        Assertions.assertEquals(
                EXPECTED_HELLO_WORLD,
                newBuilder()
                        .key("key")
                        .tagResolvers(List.of(HELLO, WORLD))
                        .build()
        );
    }

    @Test
    void testAsPlaceholder() {
        Assertions.assertEquals(
                Placeholder.component("placeholder", EXPECTED_HELLO_WORLD),
                newBuilder()
                        .key("key")
                        .tagResolvers(HELLO, WORLD)
                        .asPlaceholder("placeholder")
        );
    }

    @Test
    void testSend() {
        var received = new AtomicBoolean();

        newBuilder()
                .key("key")
                .tagResolvers(HELLO, WORLD)
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
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().tagResolver(null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().tagResolvers((TagResolver[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().tagResolvers((Collection<? extends TagResolver>) null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().build());
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().asPlaceholder(null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().asPlaceholder("test"));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().key("test").send(null));
        Assertions.assertThrows(NullPointerException.class, () -> newBuilder().send(Audience.empty()));
    }

    private @NotNull MiniMessageBuilder newBuilder() {
        return MiniMessageBuilder.create(MiniMessageSource.create(StringMessageMap.create(Map.of("key", "<hello>, <world>!"))));
    }
}
