package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg5Impl<A1, A2, A3, A4, A5>(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1, @NotNull Function<? super A2, ? extends TagResolverBase> a2, @NotNull Function<? super A3, ? extends TagResolverBase> a3, @NotNull Function<? super A4, ? extends TagResolverBase> a4, @NotNull Function<? super A5, ? extends TagResolverBase> a5) implements Arg5<A1, A2, A3, A4, A5> {

    @Override
    @Contract("_, _, _, _, _ -> new")
    public @NotNull MiniMessageBase apply(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5) {
        return MiniMessageBase.withTagResolverBase(this.key, this.a1.apply(a1), this.a2.apply(a2), this.a3.apply(a3), this.a4.apply(a4), this.a5.apply(a5));
    }

}
