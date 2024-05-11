package com.github.siroshun09.messages.api.localize;

import com.github.siroshun09.messages.api.source.FallingBackMessageSource;
import com.github.siroshun09.messages.api.source.MessageSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An abstract implementation of {@link Localization}.
 *
 * @param <T>  the message type
 * @param <M>  the message source type
 * @param <FB> the fallback source type
 */
public abstract class AbstractLocalization<T, M extends MessageSource<T>, FB extends FallingBackMessageSource<T, M>> implements Localization<T, M, FB> {

    private final Map<Locale, M> sourceMap = new ConcurrentHashMap<>();
    private final M defaultSource;

    /**
     * A constructor of {@link AbstractLocalization}.
     *
     * @param defaultSource the default {@link MessageSource}
     */
    protected AbstractLocalization(@NotNull M defaultSource) {
        this.defaultSource = Objects.requireNonNull(defaultSource);
    }

    @Override
    public @NotNull M defaultSource() {
        return defaultSource;
    }

    @Override
    public @Nullable M getSource(@NotNull Locale locale) {
        var source = sourceMap.get(Objects.requireNonNull(locale));
        return source != null ? source : sourceMap.get(new Locale(locale.getLanguage()));
    }

    @Override
    public void addSource(@NotNull Locale locale, @NotNull M source) {
        sourceMap.put(Objects.requireNonNull(locale), Objects.requireNonNull(source));
    }

    @Override
    public void removeSource(@NotNull Locale locale) {
        sourceMap.remove(Objects.requireNonNull(locale));
    }

    @Override
    public void clearSources() {
        sourceMap.clear();
    }

    @Override
    public @NotNull @Unmodifiable Map<Locale, M> sourceMap() {
        return Map.copyOf(sourceMap);
    }
}
