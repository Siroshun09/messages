package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg1Impl<A1>(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1) implements Arg1<A1> {

    @Override
    @Contract("_ -> new")
    public @NotNull LegacyFormatMessageBase apply(A1 a1) {
        return LegacyFormatMessageBase.withReplacementBase(this.key, this.a1.apply(a1));
    }

}
