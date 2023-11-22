package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg2Impl<A1, A2>(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1, @NotNull Function<? super A2, ? extends ReplacementBase> a2) implements Arg2<A1, A2> {

    @Override
    @Contract("_, _ -> new")
    public @NotNull LegacyFormatMessageBase apply(A1 a1, A2 a2) {
        return LegacyFormatMessageBase.withReplacementBase(this.key, this.a1.apply(a1), this.a2.apply(a2));
    }

}
