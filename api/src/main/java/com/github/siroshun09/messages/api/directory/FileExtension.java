package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.util.LocaleParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * An interface to represent a file extension.
 */
public interface FileExtension {

    /**
     * Creates a {@link FileExtension}.
     *
     * @param extension a file extension, includes "{@code .}"
     * @return a {@link FileExtension}
     */
    static @NotNull FileExtension create(@NotNull String extension) {
        return new DefaultFileExtension(Objects.requireNonNull(extension));
    }

    /**
     * Returns a file extension.
     *
     * @return a file extension
     */
    @NotNull String extension();

    /**
     * Creates a filename from {@link Locale}.
     *
     * @param locale a {@link Locale} to create a filename
     * @return a filename
     */
    default @NotNull String toFilename(@NotNull Locale locale)  {
        return Objects.requireNonNull(locale) + extension();
    }

    /**
     * Parses a filename to {@link Locale}.
     *
     * @param filename a filename to parse
     * @return a {@link Locale} or {@code null}
     */
    default @Nullable Locale parse(@NotNull String filename)  {
        return filename.endsWith(extension()) ?
                LocaleParser.parse(filename.substring(0, filename.length() - extension().length())) :
                null;
    }
}
