package com.github.siroshun09.messages.api.localize;

import com.github.siroshun09.messages.api.source.FallingBackMessageSource;
import com.github.siroshun09.messages.api.source.MessageSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Map;

/**
 * A class for providing the localization feature.
 *
 * @param <T>  the message type
 * @param <M>  the message source type
 * @param <FB> the fallback source type
 */
public interface Localization<T, M extends MessageSource<T>, FB extends FallingBackMessageSource<T, M>> {

    /**
     * Gets the default {@link MessageSource}.
     *
     * @return the default {@link MessageSource}
     */
    @NotNull M defaultSource();

    /**
     * Gets the {@link MessageSource} of the specified {@link Locale}
     *
     * @param locale the locale to get
     * @return the {@link MessageSource} of the specified {@link Locale} or {@code null} if not found
     */
    @Nullable M getSource(@NotNull Locale locale);

    /**
     * Adds the {@link MessageSource}.
     *
     * @param locale the {@link Locale} of the {@link MessageSource}
     * @param source the {@link MessageSource} to add
     */
    void addSource(@NotNull Locale locale, @NotNull M source);

    /**
     * Removes the {@link MessageSource} of the specified {@link Locale}.
     *
     * @param locale the {@link Locale} to remove
     */
    void removeSource(@NotNull Locale locale);

    /**
     * Removes the all {@link MessageSource}s.
     */
    void clearSources();

    /**
     * Gets the all {@link MessageSource}s.
     *
     * @return the all {@link MessageSource}s
     */
    @NotNull @Unmodifiable Map<Locale, M> sourceMap();

    /**
     * Gets the {@link MessageSource} of the specified {@link Locale} as {@link FallingBackMessageSource}.
     * <p>
     * The returned {@link FallingBackMessageSource} will fall back to {@link #defaultSource()}.
     *
     * @param locale the {@link Locale} to get
     * @return the {@link MessageSource} of the specified {@link Locale} as {@link FallingBackMessageSource}
     */
    @NotNull FB findSource(@NotNull Locale locale);

    /**
     * Gets the {@link MessageSource} from the specified object.
     *
     * @param obj the object to get
     * @return the {@link MessageSource} as {@link FallingBackMessageSource}
     */
    @NotNull FB findSource(@Nullable Object obj);
}
