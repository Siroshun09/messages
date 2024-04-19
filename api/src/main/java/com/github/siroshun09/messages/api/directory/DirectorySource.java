package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.MessageSource;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.api.util.Loader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A class for loading {@link MessageSource}s from the directory.
 *
 * @param <S> the type of the {@link MessageSource}s.
 */
public final class DirectorySource<S extends MessageSource<?>> {

    /**
     * Creates a new {@link DirectorySource}.
     *
     * @param directory a directory to load sources
     * @param <S>       the type of the {@link MessageSource}s
     * @return a new {@link DirectorySource}
     */
    @Contract("_ -> new")
    public static <S extends MessageSource<?>> @NotNull DirectorySource<S> create(@NotNull Path directory) {
        return new DirectorySource<>(Objects.requireNonNull(directory), Collections.emptyList(), null, null);
    }

    /**
     * Creates a new {@link DirectorySource}.
     *
     * @param directory a directory to load sources
     * @return a new {@link DirectorySource}
     */
    @Contract("_ -> new")
    public static @NotNull DirectorySource<StringMessageMap> forStringMessageMap(@NotNull Path directory) {
        return new DirectorySource<>(Objects.requireNonNull(directory), Collections.emptyList(), null, null);
    }

    private final Path directory;
    private final @Unmodifiable Collection<Locale> defaultLocales;
    private final @Nullable FileExtension fileExtension;
    private final @Nullable Loader<LoadContext, LoadedMessageSource<S>> loader;

    private DirectorySource(@NotNull Path directory,
                            @NotNull Collection<Locale> defaultLocales,
                            @Nullable FileExtension fileExtension,
                            @Nullable Loader<LoadContext, LoadedMessageSource<S>> loader) {
        this.directory = directory;
        this.defaultLocales = defaultLocales;
        this.fileExtension = fileExtension;
        this.loader = loader;
    }

    /**
     * Sets {@link FileExtension}.
     *
     * @param fileExtension the {@link FileExtension}
     * @return a new {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> fileExtension(@NotNull FileExtension fileExtension) {
        return new DirectorySource<>(this.directory, this.defaultLocales, Objects.requireNonNull(fileExtension), this.loader);
    }

    /**
     * Adds a default {@link Locale}.
     *
     * @param locale a default {@link Locale}
     * @return a new {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Locale locale) {
        return this.defaultLocale0(List.of(locale));
    }

    /**
     * Adds default {@link Locale}s.
     *
     * @param locales default {@link Locale}s
     * @return a new {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Locale @NotNull ... locales) {
        return this.defaultLocale0(List.of(locales));
    }

    /**
     * Adds default {@link Locale}s.
     *
     * @param locales default {@link Locale}s
     * @return a new {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> defaultLocale(@NotNull Collection<Locale> locales) {
        return this.defaultLocale0(List.copyOf(locales));
    }

    private @NotNull DirectorySource<S> defaultLocale0(@NotNull @Unmodifiable List<Locale> locales) {
        List<Locale> newDefaultLocales;
        if (this.defaultLocales.isEmpty()) {
            newDefaultLocales = locales;
        } else {
            newDefaultLocales = new ArrayList<>(this.defaultLocales.size() + locales.size());
            newDefaultLocales.addAll(this.defaultLocales);
            newDefaultLocales.addAll(locales);
        }
        return new DirectorySource<>(this.directory, Collections.unmodifiableList(newDefaultLocales), this.fileExtension, this.loader);
    }

    /**
     * Sets the {@link Loader} to load {@link MessageSource} from the file.
     *
     * @param loader the {@link Loader} to load {@link MessageSource} from the file
     * @return a new {@link DirectorySource} instance
     */
    public @NotNull DirectorySource<S> messageLoader(@NotNull Loader<Path, ? extends S> loader) {
        Objects.requireNonNull(loader);

        if (this.loader != null) {
            throw new IllegalStateException("The message loader is already set.");
        }

        return new DirectorySource<>(
                this.directory,
                this.defaultLocales,
                this.fileExtension,
                context -> new LoadedMessageSource<>(context.filepath, context.locale, loader.apply(context.filepath))
        );
    }

    /**
     * Adds a {@link Loader} that processes loaded messages.
     *
     * @param processor a {@link Loader} that processes loaded messages.
     * @param <NS>      the type of new {@link MessageSource}
     * @return a new {@link DirectorySource} instance
     */
    public <NS extends MessageSource<?>> @NotNull DirectorySource<NS> messageProcessor(@NotNull Loader<LoadedMessageSource<S>, NS> processor) {
        if (this.loader == null) {
            throw new IllegalStateException("The message loader is not set.");
        }

        return new DirectorySource<>(
                this.directory,
                this.defaultLocales,
                this.fileExtension,
                context -> new LoadedMessageSource<>(context.filepath, context.locale, processor.apply(this.loader.apply(context)))
        );
    }

    /**
     * Performs loading.
     * <p>
     * {@link #fileExtension} and {@link #loader} must be set.
     *
     * @param consumer a {@link Consumer} to consume loaded {@link MessageSource}
     * @throws IOException if I/O error occurred
     */
    public void load(@NotNull Consumer<LoadedMessageSource<S>> consumer) throws IOException {
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
            consumer.accept(this.loader.load(new LoadContext(entry.getKey(), entry.getValue())));
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

    private record LoadContext(@NotNull Path filepath, @NotNull Locale locale) {
    }
}
