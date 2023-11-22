package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg2Impl<A1, A2>(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1, @NotNull Function<? super A2, ? extends TagResolverBase> a2) implements Arg2<A1, A2> {

    @Override
    @Contract("_, _ -> new")
    public @NotNull MiniMessageBase apply(A1 a1, A2 a2) {
        return MiniMessageBase.withTagResolverBase(this.key, this.a1.apply(a1), this.a2.apply(a2));
    }

}
