package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.api.util.PropertiesFile;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

class DirectorySourceTest {

    private static <T> @NotNull Consumer<T> doNothing() {
        return t -> {
        };
    }

    @Test
    void testExistingDirectory(@TempDir Path dir) throws IOException {
        var directory = dir.resolve("sources");
        var source = createSource(directory);
        createJapaneseFile(directory);

        var loaded = new HashMap<Locale, StringMessageMap>();

        source.load(loadedSource -> loaded.put(loadedSource.locale(), loadedSource.messageSource()));

        Assertions.assertEquals(2, loaded.size());

        var en = loaded.get(Locale.ENGLISH);
        Assertions.assertNotNull(en);
        Assertions.assertTrue(en.asMap().isEmpty());

        var jp = loaded.get(Locale.JAPANESE);
        Assertions.assertNotNull(jp);
        Assertions.assertEquals(2, jp.asMap().size());
        Assertions.assertEquals("あ", jp.asMap().get("a"));
        Assertions.assertEquals("い", jp.asMap().get("b"));
    }

    @Test
    void testNewDirectoryWithDefaultLocales(@TempDir Path dir) throws IOException {
        var directory = dir.resolve("sources");
        var source = createSource(directory);

        var loaded = new HashMap<Locale, StringMessageMap>();

        source.load(loadedSource -> loaded.put(loadedSource.locale(), loadedSource.messageSource()));

        Assertions.assertEquals(1, loaded.size());

        var en = loaded.get(Locale.ENGLISH);
        Assertions.assertNotNull(en);
        Assertions.assertTrue(en.asMap().isEmpty());

        Assertions.assertTrue(Files.exists(directory));
    }

    @Test
    void testNewDirectoryWithoutDefaultLocales(@TempDir Path dir) throws IOException {
        var directory = dir.resolve("sources");
        var source = DirectorySource.propertiesFiles(directory);

        var loaded = new HashMap<Locale, StringMessageMap>();

        source.load(loadedSource -> loaded.put(loadedSource.locale(), loadedSource.messageSource()));

        Assertions.assertTrue(loaded.isEmpty());
        Assertions.assertFalse(Files.exists(directory));
    }

    @Test
    void testDefaultLocales(@TempDir Path dir) throws IOException {
        var directory = dir.resolve("sources");
        var locales = new ArrayList<>(List.of(Locale.ENGLISH, Locale.FRENCH, Locale.GERMAN, Locale.JAPANESE, Locale.TRADITIONAL_CHINESE, Locale.SIMPLIFIED_CHINESE));

        DirectorySource.forStringMessageMap(directory)
            .fileExtension(PropertiesFile.FILE_EXTENSION)
            .defaultLocale(locales.get(0))
            .defaultLocale(locales.subList(1, 3).toArray(Locale[]::new))
            .defaultLocale(locales.subList(3, 6))
            .messageLoader(PropertiesFile.DEFAULT_LOADER)
            .load(loaded -> Assertions.assertTrue(locales.remove(loaded.locale())));
    }

    @Test
    void testLoaderAndProcessor(@TempDir Path dir) throws IOException {
        var directory = dir.resolve("sources");
        var counter = new AtomicInteger();

        DirectorySource.forStringMessageMap(directory)
            .fileExtension(PropertiesFile.FILE_EXTENSION)
            .defaultLocale(Locale.ENGLISH)
            .messageLoader(filepath -> {
                Assertions.assertEquals(1, counter.incrementAndGet());
                return PropertiesFile.DEFAULT_LOADER.load(filepath);
            })
            .messageProcessor(loaded -> {
                Assertions.assertEquals(2, counter.incrementAndGet());
                return loaded.messageSource();
            })
            .messageProcessor(loaded -> {
                Assertions.assertEquals(3, counter.incrementAndGet());
                return loaded.messageSource();
            }).load(doNothing());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testIllegalArguments() {
        var dir = Path.of("example");
        var source = DirectorySource.create(dir);

        Assertions.assertThrows(NullPointerException.class, () -> DirectorySource.create(null));
        Assertions.assertThrows(NullPointerException.class, () -> source.fileExtension(null));
        Assertions.assertThrows(NullPointerException.class, () -> source.defaultLocale((Locale) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.defaultLocale((Locale[]) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.defaultLocale((Collection<Locale>) null));
        Assertions.assertThrows(NullPointerException.class, () -> source.defaultLocale(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> source.defaultLocale(Collections.singletonList(null)));
        Assertions.assertThrows(NullPointerException.class, () -> source.messageLoader(null));
        Assertions.assertThrows(NullPointerException.class, () -> createSource(dir).load(null));
    }

    @Test
    void testIllegalState() {
        var dir = Path.of("example");
        Assertions.assertThrows(IllegalStateException.class, () -> DirectorySource.create(dir).load(doNothing()));
        Assertions.assertThrows(IllegalStateException.class, () -> DirectorySource.create(dir).fileExtension(PropertiesFile.FILE_EXTENSION).load(doNothing()));
        Assertions.assertThrows(IllegalStateException.class, () -> DirectorySource.create(dir).messageLoader(PropertiesFile.DEFAULT_LOADER).load(doNothing()));
    }

    private static void createJapaneseFile(@NotNull Path directory) throws IOException {
        Files.createDirectories(directory);
        try (var writer = Files.newBufferedWriter(directory.resolve(Locale.JAPANESE + ".properties"))) {
            writer.write("a=あ");
            writer.newLine();
            writer.write("b=い");
            writer.newLine();
        }
    }

    private static DirectorySource<StringMessageMap> createSource(Path directory) {
        return DirectorySource.propertiesFiles(directory).defaultLocale(Locale.ENGLISH);
    }
}
