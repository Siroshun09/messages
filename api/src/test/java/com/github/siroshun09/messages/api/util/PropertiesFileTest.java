package com.github.siroshun09.messages.api.util;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class PropertiesFileTest {

    private static final Map<String, String> EXPECTED_MAP = new LinkedHashMap<>();
    private static final String EXPECTED_CONTENT = "a=1" + System.lineSeparator() + "b=2" + System.lineSeparator();

    static {
        EXPECTED_MAP.put("a", "1");
        EXPECTED_MAP.put("b", "2");
    }

    @Test
    void testLoad() throws IOException {
        try (var reader = content(StringReader::new)) {
            Assertions.assertEquals(EXPECTED_MAP, PropertiesFile.load(reader));
        }

        var closed = new AtomicBoolean();

        try (var input = content(in -> new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public void close() {
                closed.set(true);
            }
        })) {
            Assertions.assertEquals(EXPECTED_MAP, PropertiesFile.load(input));
            Assertions.assertTrue(closed.get());
        }

        var path = Path.of("properties-file-test-load.properties");

        try {
            content(input -> Files.writeString(path, input, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));
            Assertions.assertEquals(EXPECTED_MAP, PropertiesFile.load(path));
        } finally {
            Files.deleteIfExists(path);
        }

        Assertions.assertTrue(PropertiesFile.load(path).isEmpty());
    }

    @Test
    void testAppend() throws IOException {
        try (var writer = new StringWriter()) {
            PropertiesFile.append(writer, EXPECTED_MAP);
            Assertions.assertEquals(EXPECTED_CONTENT, writer.toString());
        }

        var closed = new AtomicBoolean();

        try (var output = new ByteArrayOutputStream() {
            @Override
            public void close() {
                closed.set(true);
            }
        }) {
            PropertiesFile.append(output, EXPECTED_MAP);
            Assertions.assertEquals(EXPECTED_CONTENT, output.toString(StandardCharsets.UTF_8));
            Assertions.assertTrue(closed.get());
        }

        var path = Path.of("properties-file-test-append.properties");

        try {
            PropertiesFile.append(path, Map.of("a", "1"));
            PropertiesFile.append(path, Map.of("b", "2"));
            Assertions.assertEquals(EXPECTED_CONTENT, Files.readString(path));
        } finally {
            Files.deleteIfExists(path);
        }
    }

    private <T> @NotNull T content(@NotNull Loader<String, T> loader) {
        /*
          a=1
          b=2
          #c=3
         */
        return loader.apply(EXPECTED_CONTENT + "#c=3" + System.lineSeparator());
    }

    @Test
    void testFileExtension() {
        var extension = PropertiesFile.FILE_EXTENSION;

        Assertions.assertEquals("en.properties", extension.toFilename(Locale.ENGLISH));
        Assertions.assertEquals(Locale.ENGLISH, extension.parse("en.properties"));

        Assertions.assertNull(extension.parse(""));
        Assertions.assertNull(extension.parse(".properties"));
    }

    @Test
    void testDefaultLoader(@TempDir Path directory) throws IOException {
        var filepath = directory.resolve("default-loader-test.properties");

        try (var writer = Files.newBufferedWriter(filepath)) {
            content(src -> {
                writer.write(src);
                return null;
            });
        }

        Assertions.assertEquals(EXPECTED_MAP, PropertiesFile.DEFAULT_LOADER.load(filepath).asMap());
    }
}
