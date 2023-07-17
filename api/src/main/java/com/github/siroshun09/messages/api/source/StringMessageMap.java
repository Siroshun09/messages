package com.github.siroshun09.messages.api.source;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation class of {@link MessageMap} and {@link StringMessageSource}.
 * <p>
 * This class holds the string messages.
 */
public final class StringMessageMap extends AbstractMessageMap<String> implements StringMessageSource {

    /**
     * Creates a new {@link StringMessageMap}.
     *
     * @return new {@link StringMessageMap}
     */
    public static @NotNull StringMessageMap create() {
        return new StringMessageMap(new ConcurrentHashMap<>());
    }

    /**
     * Creates a new {@link StringMessageMap} with the map.
     * <p>
     * The given map will be copied when creating {@link StringMessageMap}.
     *
     * @param map a map to copy messages
     * @return new {@link StringMessageMap}
     */
    public static @NotNull StringMessageMap create(@NotNull Map<String, String> map) {
        return new StringMessageMap(new ConcurrentHashMap<>(map));
    }

    private StringMessageMap(@NotNull Map<String, String> messageMap) {
        super(messageMap);
    }

    @Override
    protected @NotNull String empty() {
        return "";
    }
}
