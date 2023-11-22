package com.github.siroshun09.messages.minimessage.arg;

import com.github.siroshun09.messages.minimessage.base.MiniMessageBase;
import com.github.siroshun09.messages.minimessage.base.TagResolverBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

record Arg1Impl<A1>(@NotNull String key, @NotNull Function<? super A1, ? extends TagResolverBase> a1) implements Arg1<A1> {

    @Override
    @Contract("_ -> new")
    public @NotNull MiniMessageBase apply(A1 a1) {
        return MiniMessageBase.withTagResolverBase(this.key, this.a1.apply(a1));
    }

}
