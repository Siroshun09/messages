package com.github.siroshun09.messages.api.util;

import com.github.siroshun09.messages.api.directory.FileExtension;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A utility class for reading/writing properties file or format.
 */
public final class PropertiesFile {

    /**
     * A {@link FileExtension} implementation for the {@code .properties} files.
     */
    public static final FileExtension FILE_EXTENSION = FileExtension.create(".properties");

    /**
     * A default {@link Loader} that uses {@link #load(Reader)}.
     * <p>
     * If the file does not exist, this {@link Loader} returns {@link StringMessageMap#create()}
     */
    public static final Loader<Path, StringMessageMap> DEFAULT_LOADER = filepath -> {
        if (Files.isRegularFile(filepath)) {
            try (var reader = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
                return StringMessageMap.create(load(reader));
            }
        } else {
            return StringMessageMap.create();
        }
    };

    /**
     * Loads the string map from the {@link Reader}.
     * <p>
     * This method uses {@link Properties} to load, but the loaded {@code key-value}s will be stored to {@link LinkedHashMap}.
     * <p>
     * The given {@link Reader} will <b>NOT</b> be closed by this method.
     *
     * @param reader the {@link Reader} to load the map
     * @return the result of loading
     * @throws IOException if the I/O error occurred
     */
    public static @NotNull Map<String, String> load(@NotNull Reader reader) throws IOException {
        var map = new LinkedHashMap<String, String>();
        new PropertiesReader(map).load(reader);
        return map;
    }

    /**
     * Loads the string map from the {@link InputStream}.
     * <p>
     * The given {@link InputStream} will be closed by internally created {@link InputStreamReader}.
     *
     * @param inputStream the {@link InputStream} to load the map
     * @return the result of loading
     * @throws IOException if the I/O error occurred
     * @see #load(Reader)
     */
    public static @NotNull Map<String, String> load(@NotNull InputStream inputStream) throws IOException {
        try (var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return load(reader);
        }
    }

    /**
     * Loads the string map from the file.
     * <p>
     * If the given file does not exist, this method will return an empty {@link LinkedHashMap}.
     *
     * @param path the filepath to load the map.
     * @return the result of loading
     * @throws IOException if the I/O error occurred
     * @see #load(Reader)
     */
    public static @NotNull Map<String, String> load(@NotNull Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            return new LinkedHashMap<>();
        }

        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return load(reader);
        }
    }

    /**
     * Appends the map to the {@link Writer}.
     * <p>
     * Entries in the map will be written as {@code KEY=VALUE}.
     * <p>
     * The given {@link Writer} will <b>NOT</b> be closed by this method.
     *
     * @param writer the {@link Writer} to append entries
     * @param map    the map to append
     * @throws IOException if I/O error occurred
     */
    public static void append(@NotNull Writer writer, @NotNull Map<String, String> map) throws IOException {
        for (var entry : map.entrySet()) {
            appendEscapedString(entry.getKey(), true, writer);
            writer.write('=');
            appendEscapedString(entry.getValue(), false, writer);
            writer.write(System.lineSeparator());
        }
    }

    /**
     * Appends the map to the {@link OutputStream}.
     * <p>
     * The given {@link OutputStream} will be closed by internally created {@link OutputStreamWriter}.
     *
     * @param outputStream the {@link OutputStream} to append entries
     * @param map          the map to append
     * @throws IOException if I/O error occurred
     * @see #append(Writer, Map)
     */
    public static void append(@NotNull OutputStream outputStream, @NotNull Map<String, String> map) throws IOException {
        try (var writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            append(writer, map);
        }
    }

    /**
     * Appends the map to the file.
     *
     * @param file the filepath to append entries
     * @param map  the map to append
     * @throws IOException if I/O error occurred
     * @see #append(Writer, Map)
     */
    public static void append(@NotNull Path file, @NotNull Map<String, String> map) throws IOException {
        try (var writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            append(writer, map);
        }
    }

    private static void appendEscapedString(@NotNull String theString, boolean escapeSpace, @NotNull Writer writer) throws IOException {
        char[] chars = theString.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (61 < c && c < 127) {
                if (c == '\\') writer.append('\\').append('\\');
                else writer.append(c);
                continue;
            }

            switch (c) {
                case ' ' -> {
                    if (i == 0 || escapeSpace) writer.append('\\');
                    writer.append(' ');
                }
                case '\t' -> writer.append('\\').append('t');
                case '\n' -> writer.append('\\').append('n');
                case '\r' -> writer.append('\\').append('r');
                case '\f' -> writer.append('\\').append('f');
                case '=', ':', '#', '!' -> writer.append('\\').append(c);
                default -> writer.append(c);
            }
        }
    }

    private PropertiesFile() {
        throw new UnsupportedOperationException();
    }

    private static final class PropertiesReader extends Properties {

        private final Map<String, String> map;

        private PropertiesReader(@NotNull Map<String, String> map) {
            this.map = map;
        }

        @Override
        public synchronized Object put(Object key, Object value) {
            return map.put(String.valueOf(key), String.valueOf(value));
        }
    }
}
