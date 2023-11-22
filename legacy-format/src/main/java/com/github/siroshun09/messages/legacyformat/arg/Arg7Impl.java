package com.github.siroshun09.messages.legacyformat.arg;

import com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase;
import com.github.siroshun09.messages.legacyformat.base.ReplacementBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg7Impl<A1, A2, A3, A4, A5, A6, A7>(@NotNull String key, @NotNull Function<? super A1, ? extends ReplacementBase> a1, @NotNull Function<? super A2, ? extends ReplacementBase> a2, @NotNull Function<? super A3, ? extends ReplacementBase> a3, @NotNull Function<? super A4, ? extends ReplacementBase> a4, @NotNull Function<? super A5, ? extends ReplacementBase> a5, @NotNull Function<? super A6, ? extends ReplacementBase> a6, @NotNull Function<? super A7, ? extends ReplacementBase> a7) implements Arg7<A1, A2, A3, A4, A5, A6, A7> {

    @Override
    @Contract("_, _, _, _, _, _, _ -> new")
    public @NotNull LegacyFormatMessageBase apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) {
        return LegacyFormatMessageBase.withReplacementBase(this.key, this.a1.apply(a1), this.a2.apply(a2), this.a3.apply(a3), this.a4.apply(a4), this.a5.apply(a5), this.a6.apply(a6), this.a7.apply(a7));
    }

}
