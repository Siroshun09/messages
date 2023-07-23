package com.github.siroshun09.messages.api.source;

import com.github.siroshun09.messages.api.builder.LegacyFormatMessageBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * An interface that converting the string to {@link Component} using {@link LegacyComponentSerializer}.
 */
public interface LegacyFormatSource extends ComponentSource {

    /**
     * Creates a {@link LegacyFormatSource} with {@link StringMessageSource}.
     * <p>
     * The created {@link LegacyFormatSource} uses {@link LegacyComponentSerializer#legacyAmpersand()}.
     *
     * @param source the source of the string messages
     * @return new {@link LegacyFormatSource}
     */
    @Contract("_ -> new")
    static @NotNull LegacyFormatSource create(@NotNull StringMessageSource source) {
        return create(source, LegacyComponentSerializer.legacyAmpersand());
    }

    /**
     * Creates a {@link LegacyFormatSource} with {@link StringMessageSource} and {@link LegacyComponentSerializer}.
     *
     * @param source     the source of the string messages
     * @param serializer the {@link LegacyComponentSerializer}
     * @return new {@link LegacyFormatSource}
     */
    @Contract("_, _ -> new")
    static @NotNull LegacyFormatSource create(@NotNull StringMessageSource source, @NotNull LegacyComponentSerializer serializer) {
        return new LegacyFormatSourceImpl(source, serializer);
    }

    /**
     * Gets the message that replaced specified characters.
     * <p>
     * The placeholders will be replaced using {@link String#replace(CharSequence, CharSequence)}.
     *
     * @param key          the key to get the message
     * @param replacements the replacements
     * @return the message that replaced specified characters
     */
    @NotNull Component getMessage(@NotNull String key, @NotNull Map<String, String> replacements);

    /**
     * Creates a {@link LegacyFormatMessageBuilder} using this {@link LegacyFormatSource}.
     *
     * @return a {@link LegacyFormatMessageBuilder} using this {@link LegacyFormatSource}
     */
    default @NotNull LegacyFormatMessageBuilder builder() {
        return LegacyFormatMessageBuilder.create(this);
    }
}
