/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.data;

import java.util.List;
import java.util.Map;
import net.minecraft.resources.Identifier;

public record Fonts(Identifier identifier, List<String> characters) {
    public static final Identifier PORTRAIT_FONT = Identifier.withDefaultNamespace("hud/dialogue/portrait");

    public static final Fonts FRUMA_QUEEN_67 = new Fonts(
            Identifier.fromNamespaceAndPath("wynnrot", "hud/dialogue/portrait"),
            List.of("\uE000", "\uE001", "\uE002", "\uE003", "\uE004", "\uE005", "\uE006", "\uE007"));
    public static final Fonts SUI = new Fonts(
            PORTRAIT_FONT,
            List.of(
                    "\uE170", "\uE171", "\uE172", "\uE173", "\uE174", "\uE175", "\uE176", "\uE177", "\uE178", "\uE179",
                    "\uE17A", "\uE17B", "\uE180", "\uE181", "\uE182", "\uE183", "\uE184", "\uE185", "\uE186", "\uE187",
                    "\uE188", "\uE189", "\uE18A", "\uE18B", "\uE18C", "\uE18D", "\uE18E", "\uE190", "\uE191", "\uE192",
                    "\uE193", "\uE194", "\uE195", "\uE196", "\uE197", "\uE198", "\uE199", "\uE19A", "\uE19B", "\uE19C",
                    "\uE19D", "\uE19E"));
    public static final Fonts SUI_EATING = new Fonts(PORTRAIT_FONT, List.of("\uE17A", "\uE17B"));

    public static final Fonts DANCING_QUEEN = new Fonts(PORTRAIT_FONT, List.of("\uE0D5", "\uE0DA", "\uE0DB", "\uE0DC"));

    // TODO: Clean this up so it's not a massive hard coded map
    // When replacing a portrait, we need to make sure the "used" area of the sprite matches the
    // original.

    // For example. The first Sui portrait has 4 blank pixels before the sprite begins, and after that
    // it uses every
    // remaining pixel in the width of 48 for a total of 44 pixels used.
    // If we replace it with one that has 3 blank pixels at the start, then we need to add the
    // character representing
    // a +1 pixel offset before it with the space font; the portrait font references this already.
    public static Map<String, String> PREFIX_OFFSETS = Map.ofEntries(
            Map.entry("\uE170", "\uDB00\uDC01"),
            Map.entry("\uE174", "\uDB00\uDC02"),
            Map.entry("\uE176", "\uDB00\uDC01"),
            Map.entry("\uE177", "\uDB00\uDC01"),
            Map.entry("\uE179", "\uDAFF\uDFFE"),
            Map.entry("\uE17D", "\uDB00\uDC01"),
            Map.entry("\uE17E", "\uDB00\uDC01"),
            Map.entry("\uE17F", "\uDB00\uDC01"),
            Map.entry("\uE290", "\uDAFF\uDFFF"),
            Map.entry("\uE291", "\uDAFF\uDFFE"),
            Map.entry("\uE292", "\uDAFF\uDFFF"),
            Map.entry("\uE293", "\uDB00\uDC01"),
            Map.entry("\uE295", "\uDAFF\uDFFE"),
            Map.entry("\uE297", "\uDAFF\uDFFF"),
            Map.entry("\uE298", "\uDAFF\uDFFD"),
            Map.entry("\uE299", "\uDB00\uDC01"),
            Map.entry("\uE29A", "\uDB00\uDC01"),
            Map.entry("\uE29B", "\uDAFF\uDFFF"),
            Map.entry("\uE29C", "\uDB00\uDC01"),
            Map.entry("\uE29D", "\uDB00\uDC01"),
            Map.entry("\uE29E", "\uDB00\uDC01"),
            Map.entry("\uE29F", "\uDAFF\uDFFF"),
            Map.entry("\uE2B0", "\uDAFF\uDFFE"),
            Map.entry("\uE2B1", "\uDAFF\uDFFF"),
            Map.entry("\uE2B2", "\uDB00\uDC01"),
            Map.entry("\uE2B4", "\uDB00\uDC01"),
            Map.entry("\uE2B5", "\uDB00\uDC01"),
            Map.entry("\uE2B6", "\uDAFF\uDFFD"),
            Map.entry("\uE2B7", "\uDB00\uDC01"),
            Map.entry("\uE2B9", "\uDAFF\uDFFE"));

    // Then we need to fill in the end with the same principal. If the original sprite goes all the
    // way to the end of
    // the width, but the one we are replacing does not, we need to pad it to now fit.
    // However, this needs to take into account the prefix character added. So if we need to add a +2
    // gap, but we already
    // added 1 in the prefix, we only need to add 1 here instead of 2.
    public static Map<String, String> SUFFIX_OFFSETS = Map.ofEntries(
            Map.entry("\uE170", "\uDB00\uDC01"),
            Map.entry("\uE171", "\uDB00\uDC01"),
            Map.entry("\uE172", "\uDB00\uDC02"),
            Map.entry("\uE173", "\uDB00\uDC02"),
            Map.entry("\uE175", "\uDB00\uDC02"),
            Map.entry("\uE176", "\uDB00\uDC01"),
            Map.entry("\uE177", "\uDB00\uDC01"),
            Map.entry("\uE178", "\uDB00\uDC02"),
            Map.entry("\uE179", "\uDB00\uDC04"),
            Map.entry("\uE17C", "\uDB00\uDC02"),
            Map.entry("\uE17D", "\uDB00\uDC01"),
            Map.entry("\uE17E", "\uDB00\uDC01"),
            Map.entry("\uE17F", "\uDB00\uDC01"),
            Map.entry("\uE290", "\uDB00\uDC03"),
            Map.entry("\uE291", "\uDB00\uDC04"),
            Map.entry("\uE292", "\uDB00\uDC02"),
            Map.entry("\uE293", "\uDB00\uDC01"),
            Map.entry("\uE294", "\uDB00\uDC01"),
            Map.entry("\uE295", "\uDB00\uDC02"),
            Map.entry("\uE296", "\uDB00\uDC02"),
            Map.entry("\uE297", "\uDB00\uDC02"),
            Map.entry("\uE299", "\uDB00\uDC01"),
            Map.entry("\uE29A", "\uDB00\uDC01"),
            Map.entry("\uE29B", "\uDB00\uDC02"),
            Map.entry("\uE29C", "\uDB00\uDC01"),
            Map.entry("\uE29D", "\uDB00\uDC01"),
            Map.entry("\uE29E", "\uDB00\uDC01"),
            Map.entry("\uE29F", "\uDB00\uDC03"),
            Map.entry("\uE2B0", "\uDB00\uDC04"),
            Map.entry("\uE2B1", "\uDB00\uDC02"),
            Map.entry("\uE2B2", "\uDB00\uDC01"),
            Map.entry("\uE2B3", "\uDB00\uDC02"),
            Map.entry("\uE2B4", "\uDB00\uDC01"),
            Map.entry("\uE2B5", "\uDB00\uDC01"),
            Map.entry("\uE2B6", "\uDB00\uDC03"),
            Map.entry("\uE2B7", "\uDB00\uDC01"),
            Map.entry("\uE2B8", "\uDB00\uDC02"),
            Map.entry("\uE2B9", "\uDB00\uDC03"));
}
