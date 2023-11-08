package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.MessageSource;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Locale;

/**
 * A record of the loaded {@link MessageSource}.
 *
 * @param filepath      a loaded filepath
 * @param locale        a {@link Locale} of the loaded {@link MessageSource}
 * @param messageSource a loaded {@link MessageSource}
 * @param <S>           the type of the {@link MessageSource}
 */
public record LoadedMessageSource<S extends MessageSource<?>>(@NotNull Path filepath,
                                                              @NotNull Locale locale,
                                                              @NotNull S messageSource) {
}
