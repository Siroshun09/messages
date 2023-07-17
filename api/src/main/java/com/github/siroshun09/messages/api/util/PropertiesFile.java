package com.github.siroshun09.messages.api.util;

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
     * Loads the string map from the {@link Reader}.
     * <p>
     * This method uses {@link Properties} to load, but the loaded {@code key-value}s will be stored to {@link LinkedHashMap}.
     * <p>
     * The give {@link Reader} will <b>NOT</b> be closed by this method.
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
            writer.write(entry.getKey());
            writer.write('=');
            writer.write(entry.getValue());
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
