package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

class MessageProcessorsTest {

    @Test
    void testAppendMessageSources(@TempDir Path dir) throws IOException {
        var filepath = dir.resolve("test.properties");
        var loaded = new LoadedMessageSource<>(filepath, Locale.ENGLISH, StringMessageMap.create(Map.of("a", "b")));
        MessageProcessors.appendMissingMessagesToPropertiesFile(ignored -> Map.of("a", "B", "c", "d")).load(loaded);

        Assertions.assertEquals("b", loaded.messageSource().getMessage("a"));
        Assertions.assertEquals("d", loaded.messageSource().getMessage("c"));

        Assertions.assertEquals("c=d", Files.readString(filepath).trim());
    }
}
