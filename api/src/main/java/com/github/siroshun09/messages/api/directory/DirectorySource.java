package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.MessageSource;
import com.github.siroshun09.messages.api.util.Loader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A class for loading {@link MessageSource}s from the directory.
 *
 * @param <S> the type of the {@link MessageSource}s.
 */
public final class DirectorySource<S extends MessageSource<?>> {

    /**
     * Creates a new {@link DirectorySource}.
     *
     * @param directory a directory to load sources.
     * @param <S>       the type of the {@link MessageSource}s
     * @return a new {@link DirectorySource}
     */
    @Contract("_ -> new")
    public static <S extends MessageSource<?>> @NotNull DirectorySource<S> create(@NotNull Path directory) {
        return new DirectorySource<>(Objects.requireNonNull(directory));
    }

    private final Path directory;
    private final Collection<Locale> defaultLocales = new ArrayList<>();
    private @Nullable FileExtension fileExtension;
    private @Nullable Loader<Path, ? extends S> loader;

    private DirectorySource(@NotNull Path directory) {
        this.directory = directory;
    }

    /**
     * Sets {@link FileExtension}.
     *
     * @param fileExtension the {@link FileExtension}
     * @return this {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> fileExtension(@NotNull FileExtension fileExtension) {
        this.fileExtension = Objects.requireNonNull(fileExtension);
        return this;
    }

    /**
     * Adds a default {@link Locale}.
     *
     * @param locale a default {@link Locale}
     * @return this {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Locale locale) {
        this.defaultLocales.add(Objects.requireNonNull(locale));
        return this;
    }

    /**
     * Adds default {@link Locale}s.
     *
     * @param locales default {@link Locale}s
     * @return this {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Locale @NotNull... locales) {
        Objects.requireNonNull(locales);
        this.defaultLocales.addAll(Arrays.asList(locales));
        return this;
    }

    /**
     * Adds default {@link Locale}s.
     *
     * @param locales default {@link Locale}s
     * @return this {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Collection<Locale> locales) {
        this.defaultLocales.addAll(Objects.requireNonNull(locales));
        return this;
    }

    /**
     * Sets the {@link Loader} to load {@link MessageSource} from the file.
     *
     * @param loader the {@link Loader} to load {@link MessageSource} from the file
     * @return this {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> messageLoader(@NotNull Loader<Path, ? extends S> loader) {
        this.loader = Objects.requireNonNull(loader);
        return this;
    }

    /**
     * Performs loading.
     * <p>
     * {@link #fileExtension} and {@link #loader} must be set.
     *
     * @param consumer a {@link Loader} to consume loaded {@link MessageSource}
     * @throws IOException if I/O error occurred
     */
    public void load(@NotNull Loader<LoadedMessageSource<S>, Void> consumer) throws IOException {
        if (this.fileExtension == null) {
            throw new IllegalStateException("localeParser is not set");
        }

        if (this.loader == null) {
            throw new IllegalStateException("loader is not set");
        }

        Objects.requireNonNull(consumer);

        var file2LocaleMap = collectPath(this.directory, this.fileExtension);

        if (file2LocaleMap.isEmpty()) {
            if (this.defaultLocales.isEmpty()) {
                return;
            } else {
                Files.createDirectories(this.directory); // At this time, the directory may not exist.
            }
        }

        for (var locale : this.defaultLocales) {
            Objects.requireNonNull(locale);
            var filepath = this.directory.resolve(this.fileExtension.toFilename(locale));
            file2LocaleMap.putIfAbsent(filepath, locale);
        }

        for (var entry : file2LocaleMap.entrySet()) {
            var filepath = entry.getKey();
            var locale = entry.getValue();
            consumer.apply(new LoadedMessageSource<>(filepath, locale, this.loader.load(filepath)));
        }
    }

    private static @NotNull Map<Path, Locale> collectPath(@NotNull Path directory,
                                                          @NotNull FileExtension fileExtension) throws IOException {
        if (Files.isDirectory(directory)) {
            var fileLocaleMap = new HashMap<Path, Locale>();
            try (var list = Files.list(directory)) {
                list.forEach(path -> {
                    if (Files.isRegularFile(path)) {
                        var locale = fileExtension.parse(path.getFileName().toString());

                        if (locale != null) {
                            fileLocaleMap.put(path, locale);
                        }
                    }
                });
            }
            return fileLocaleMap;
        } else {
            return new HashMap<>();
        }
    }
}
