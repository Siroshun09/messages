package com.github.siroshun09.messages.legacyformat.base;

import com.github.siroshun09.messages.legacyformat.replacement.StringReplacement;
import com.github.siroshun09.messages.legacyformat.source.LegacyFormatSource;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

final class BaseImpls {

    record MessageKey(@NotNull String key) implements LegacyFormatMessageBase {
        @Override
        public @NotNull Component create(@NotNull LegacyFormatSource source) {
            return source.getMessage(this.key);
        }
    }

    record WithReplacementBase1(@NotNull String key,
                                @NotNull ReplacementBase replacementBase) implements LegacyFormatMessageBase {
        @Override
        public @NotNull Component create(@NotNull LegacyFormatSource source) {
            return source.getMessage(this.key, this.replacementBase.apply(source));
        }
    }

    record WithReplacementBaseN(@NotNull String key,
                                @NotNull ReplacementBase @NotNull [] replacementBases) implements LegacyFormatMessageBase {
        @Override
        public @NotNull Component create(@NotNull LegacyFormatSource source) {
            var replacements = new StringReplacement[replacementBases.length];

            for (int i = 0; i < this.replacementBases.length; i++) {
                replacements[i] = this.replacementBases[i].apply(source);
            }

            return source.getMessage(this.key, replacements);
        }
    }

    record WithStringReplacement1(@NotNull String key,
                                  @NotNull StringReplacement replacement) implements LegacyFormatMessageBase {
        @Override
        public @NotNull Component create(@NotNull LegacyFormatSource source) {
            return source.getMessage(this.key, this.replacement);
        }
    }

    record WithStringReplacementN(@NotNull String key,
                                  @NotNull StringReplacement @NotNull [] replacements) implements LegacyFormatMessageBase {
        @Override
        public @NotNull Component create(@NotNull LegacyFormatSource source) {
            return source.getMessage(this.key, this.replacements);
        }
    }

    record StaticReplacement(@NotNull StringReplacement replacement) implements ReplacementBase {
        @Override
        public StringReplacement apply(LegacyFormatSource legacyFormatSource) {
            return this.replacement;
        }
    }

    record StringPlaceholder<T>(@NotNull String key,
                                @NotNull Function<? super T, String> function) implements Placeholder<T> {
        @Override
        public @NotNull ReplacementBase apply(T t) {
            return ReplacementBase.replacement(this.key, this.function.apply(t));
        }
    }

    record StringWithSourcePlaceholder<T>(@NotNull String key,
                                          @NotNull BiFunction<? super T, ? super LegacyFormatSource, String> function) implements Placeholder<T> {
        @Override
        public @NotNull ReplacementBase apply(T t) {
            return source -> new StringReplacement(this.key, this.function.apply(t, source));
        }
    }

    private BaseImpls() {
        throw new UnsupportedOperationException();
    }
}
