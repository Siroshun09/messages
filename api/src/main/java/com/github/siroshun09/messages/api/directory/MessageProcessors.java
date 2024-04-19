package com.github.siroshun09.messages.api.directory;

import com.github.siroshun09.messages.api.source.MessageMap;
import com.github.siroshun09.messages.api.source.StringMessageMap;
import com.github.siroshun09.messages.api.util.Loader;
import com.github.siroshun09.messages.api.util.MessageAppender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

/**
 * A class that provides utilities of message processing.
 */
public final class MessageProcessors {

    /**
     * Creates a {@link Loader} to append missing messages to the given {@link MessageMap} and something such as files.
     *
     * @param defaultMessageLoader a {@link Loader} to get default messages
     * @param messageAppender      a {@link MessageAppender}
     * @param <M>                  the type of messages
     * @param <S>                  the type of {@link MessageMap}
     * @return a {@link Loader} to append missing messages to the given {@link MessageMap} and something such as files
     */
    public static <M, S extends MessageMap<M>> @NotNull Loader<LoadedMessageSource<S>, S> appendMissingMessages(@NotNull Loader<Locale, @Nullable Map<String, M>> defaultMessageLoader, @Nullable MessageAppender<Path, M> messageAppender) {
        return loaded -> {
            var defaultMessageMap = defaultMessageLoader.load(loaded.locale());

            if (defaultMessageMap != null) {
                var missingMessages = loaded.messageSource().merge(defaultMessageMap);

                if (messageAppender != null && !missingMessages.isEmpty()) {
                    messageAppender.append(loaded.filepath(), missingMessages);
                }
            }

            return loaded.messageSource();
        };
    }

    /**
     * Creates a {@link Loader} to append missing messages to the given {@link StringMessageMap} and something such as files.
     *
     * @param defaultMessageLoader a {@link Loader} to get default messages
     * @param messageAppender      a {@link MessageAppender}
     * @return a {@link Loader} to append missing messages to the given {@link StringMessageMap} and something such as files
     */
    public static @NotNull Loader<LoadedMessageSource<StringMessageMap>, StringMessageMap> appendMissingStringMessages(@NotNull Loader<Locale, @Nullable Map<String, String>> defaultMessageLoader, @Nullable MessageAppender<Path, String> messageAppender) {
        return appendMissingMessages(defaultMessageLoader, messageAppender);
    }
}
