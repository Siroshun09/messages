package com.github.siroshun09.messages.api.util;

import com.github.siroshun09.messages.api.builder.LegacyFormatMessageBuilder;
import com.github.siroshun09.messages.api.builder.MiniMessageBuilder;
import com.github.siroshun09.messages.api.source.LegacyFormatSource;
import com.github.siroshun09.messages.api.source.MiniMessageSource;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageBuilderFactoryTest {

    @Test
    void test() {
        var legacyFormatMessageBuilder = LegacyFormatMessageBuilder.create(LegacyFormatSource.create(StringMessageMap.create()));
        MessageBuilderFactory<LegacyFormatMessageBuilder> legacyFactory = () -> legacyFormatMessageBuilder;

        Assertions.assertSame(legacyFormatMessageBuilder, legacyFactory.create());
        Assertions.assertSame(legacyFormatMessageBuilder, legacyFactory.get());

        var miniMessageBuilder = MiniMessageBuilder.create(MiniMessageSource.create(StringMessageMap.create()));
        MessageBuilderFactory<MiniMessageBuilder> miniMessageFactory = () -> miniMessageBuilder;

        Assertions.assertSame(miniMessageBuilder, miniMessageFactory.create());
        Assertions.assertSame(miniMessageBuilder, miniMessageFactory.get());
    }
}
