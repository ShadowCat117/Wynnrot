/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import com.shadowcat.wynnrot.Wynnrot;
import com.shadowcat.wynnrot.data.Fonts;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public final class ActionBarUtils {
    public static Component replaceCharacters(
            Component original,
            List<String> originalCharacters,
            String replacementCharacter,
            Identifier originalFont,
            FontDescription replacementFont) {
        MutableComponent copy = Component.empty();

        original.visit(
                (style, text) -> {
                    if (originalCharacters.contains(text)
                            && style.getFont() instanceof FontDescription.Resource(Identifier id)
                            && id.equals(originalFont)) {
                        String prefix = Fonts.PREFIX_OFFSETS.getOrDefault(text, "");
                        if (!prefix.isEmpty()) {
                            copy.append(Component.literal(prefix).withStyle(style));
                        }

                        copy.append(Component.literal(replacementCharacter).withStyle(style.withFont(replacementFont)));

                        Wynnrot.info("Replacing " + Wynnrot.toEscapedUnicode(text));

                        String suffix = Fonts.SUFFIX_OFFSETS.getOrDefault(text, "");
                        if (!suffix.isEmpty()) {
                            copy.append(Component.literal(suffix).withStyle(style));
                        }
                    } else {
                        copy.append(Component.literal(text).withStyle(style));
                    }

                    return Optional.empty();
                },
                Style.EMPTY);

        return copy;
    }

    public static Component replaceCharacterAnimated(
            Component original,
            String originalCharacter,
            List<String> replacementCharacters,
            int updateRate,
            Identifier originalFont,
            FontDescription replacementFont) {
        return replaceCharactersAnimated(
                original, List.of(originalCharacter), replacementCharacters, updateRate, originalFont, replacementFont);
    }

    public static Component replaceCharactersAnimated(
            Component original,
            List<String> originalCharacters,
            List<String> replacementCharacters,
            int updateRate,
            Identifier originalFont,
            FontDescription replacementFont) {
        int tickCount = McUtils.player().tickCount;
        int index = (tickCount / updateRate) % replacementCharacters.size();
        String replacementCharacter = replacementCharacters.get(index);

        return replaceCharacters(original, originalCharacters, replacementCharacter, originalFont, replacementFont);
    }
}
