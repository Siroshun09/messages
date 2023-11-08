package com.github.siroshun09.messages.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class LocaleParserTest {

    @Test
    void test() {
        Assertions.assertEquals(Locale.JAPANESE, LocaleParser.parse("ja"));
        Assertions.assertEquals(Locale.JAPAN, LocaleParser.parse("ja_JP"));
        Assertions.assertNull(LocaleParser.parse("ja_JP_JP"));
    }

}
