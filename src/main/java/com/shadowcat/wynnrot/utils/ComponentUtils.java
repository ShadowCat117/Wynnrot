/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import com.shadowcat.wynnrot.Wynnrot;
import com.shadowcat.wynnrot.data.Colours;
import com.shadowcat.wynnrot.data.Fonts;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public final class ComponentUtils {
    private static final Character A_BACKGROUND_BASE = '\uE030';
    private static final Character A_FOREGROUND_BASE = '\uE000';
    private static final Character BACKGROUND_LEFT_EDGE = '\uE060';
    private static final Character BACKGROUND_RIGHT_EDGE = '\uE062';
    private static final Character SPACE_ZERO_HIGH_SURROGATE = '\uDB00';
    private static final Character SPACE_ZERO_LOW_SURROGATE = '\uDC00';
    public static final FontDescription BANNER_FLAG_FONT =
            new FontDescription.Resource(Identifier.withDefaultNamespace("banner/flag"));
    private static final Integer EXPECTED_FLAG_WIDTH = 2;
    private static final String BANNER_SPACER = "\uDAFF\uDFFF";

    private static Component wynnrotHeader = null;

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

    public static Component setColour(Component original, int colour) {
        MutableComponent copy = Component.empty();

        original.visit(
                (style, text) -> {
                    copy.append(Component.literal(ChatFormatting.stripFormatting(text))
                            .withStyle(style.withColor(colour)));

                    return Optional.empty();
                },
                Style.EMPTY);

        return copy;
    }

    public static Component replaceFirstSibling(Component original, String newText) {
        List<Component> siblings = original.getSiblings();

        if (siblings.isEmpty()) return original;

        MutableComponent rebuilt = Component.empty().withStyle(original.getStyle());

        Component first = siblings.getFirst();
        rebuilt.append(Component.literal(newText).withStyle(first.getStyle()));

        for (int i = 1; i < siblings.size(); i++) {
            rebuilt.append(siblings.get(i));
        }

        return rebuilt;
    }

    public static void submitDancingQueen(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        float time = McUtils.tickCount() + deltaTracker.getGameTimeDeltaPartialTick(true);

        List<String> frames = Fonts.DANCING_QUEEN.characters();
        int frameIndex = ((int) (time / 5)) % frames.size();
        String current = frames.get(frameIndex);

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        float speed = 100f;
        float x = width - ((time * speed / 20f) % (width + 50));

        float minY = 20;
        float maxY = height - 20;
        float amplitude = (maxY - minY) / 2f;
        float centerY = minY + amplitude;

        float frequency = 2.5f;
        float y = centerY + (float) (Math.sin(time * frequency * 0.1f) * amplitude);

        Component component = Component.literal(current)
                .withStyle(Style.EMPTY
                        .withFont(new FontDescription.Resource(Fonts.DANCING_QUEEN.identifier()))
                        .withoutShadow());

        GuiGraphicsUtils.submitText(guiGraphics, component, x, y, Colours.WHITE);
    }

    public static Component addWynnrotHeader(Component component) {
        if (wynnrotHeader == null) {
            buildWynnrotHeader();
        }

        return Component.empty().append(wynnrotHeader).append(component);
    }

    private static void buildWynnrotHeader() {
        String message = Wynnrot.MOD_ID;

        MutableComponent component = Component.empty().withStyle(Style.EMPTY.withFont(BANNER_FLAG_FONT));

        StringBuilder background = new StringBuilder();

        background.append(BACKGROUND_LEFT_EDGE);
        background.append(BANNER_SPACER);

        for (char character : message.toCharArray()) {
            Character backgroundCharacter = toBackgroundCharacter(character);

            if (backgroundCharacter != null) {
                background.append(backgroundCharacter);
            }

            background.append(BANNER_SPACER);
        }

        background.append(BACKGROUND_RIGHT_EDGE);

        component.append(Component.literal(background.toString()).withColor(Colours.CRIMSON));

        component.append(Component.literal(calculateOffset(McUtils.mc().font.width(component), EXPECTED_FLAG_WIDTH)));

        StringBuilder foreground = new StringBuilder();

        for (char character : message.toCharArray()) {
            Character foregroundCharacter = toForegroundCharacter(character);

            if (foregroundCharacter != null) {
                foreground.append(foregroundCharacter);
            }
        }

        foreground.append("\uDB00\uDC02 ");

        component.append(Component.literal(foreground.toString()).withColor(Colours.CYAN));

        wynnrotHeader = component;
    }

    public static String calculateOffset(int currentWidth, int targetWidth) {
        int delta = targetWidth - currentWidth;
        if (delta == 0) return "";

        int zeroCodePoint = Character.toCodePoint(SPACE_ZERO_HIGH_SURROGATE, SPACE_ZERO_LOW_SURROGATE);

        int targetCodePoint = zeroCodePoint + delta;

        return new String(Character.toChars(targetCodePoint));
    }

    private static Character toForegroundCharacter(char c) {
        return (char) (A_FOREGROUND_BASE + (c - 'a'));
    }

    private static Character toBackgroundCharacter(char c) {
        return (char) (A_BACKGROUND_BASE + (c - 'a'));
    }
}
