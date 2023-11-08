package com.github.siroshun09.messages.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A class to parse a string to {@link Locale}.
 */
public final class LocaleParser {

    private static final Pattern SPLITTER = Pattern.compile("_");

    /**
     * Parses a string to {@link Locale} (format: {@code language-region}).
     *
     * @param string a string representation of the locale (format: {@code language-region})
     * @return a locale
     */
    public static @Nullable Locale parse(@NotNull String string) {
        if (string.isEmpty()) {
            return null;
        }

        var segments = SPLITTER.split(string, 3);
        int length = segments.length;
        var builder = new Locale.Builder();

        if (length == 1) {
            builder.setLanguage(string);
        } else if (length == 2) {
            builder.setLanguage(segments[0]).setRegion(segments[1]);
        } else {
            return null;
        }

        return builder.build();
    }

    private LocaleParser() {
        throw new UnsupportedOperationException();
    }
}
