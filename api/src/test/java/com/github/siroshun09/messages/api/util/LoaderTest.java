package com.github.siroshun09.messages.api.util;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Function;

class LoaderTest {

    @Test
    void testLoad() {
        Assertions.assertThrows(IOException.class, () -> throwingLoader().load(new Object()));
    }

    @Test
    void testApply() {
        Assertions.assertThrows(IOException.class, () -> throwingLoader().apply(new Object()));
    }

    @Test
    void testAndThen() {
        Assertions.assertThrows(IOException.class, () -> throwingLoader().andThen(Function.identity()).apply(new Object()));
    }

    @Test
    void testCompose() {
        Assertions.assertThrows(IOException.class, () -> throwingLoader().compose(Function.identity()).apply(new Object()));
    }

    private <T, R> @NotNull Loader<T, R> throwingLoader() {
        return t -> {
            throw new IOException();
        };
    }
}
