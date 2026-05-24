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
    private static final Identifier PORTRAIT_FONT = Identifier.withDefaultNamespace("hud/dialogue/portrait");

    public static final Fonts FRUMA_QUEEN_67 = new Fonts(
            Identifier.fromNamespaceAndPath("wynnrot", "hud/dialogue/portrait"),
            List.of("\uE000", "\uE001", "\uE002", "\uE003", "\uE004", "\uE005", "\uE006", "\uE007"));
    public static final Fonts RIGHT_SUI = new Fonts(
            PORTRAIT_FONT,
            List.of(
                    "\uE170", "\uE171", "\uE172", "\uE173", "\uE174", "\uE175", "\uE176", "\uE177", "\uE178", "\uE179",
                    "\uE17A", "\uE17B", "\uE17C", "\uE17D", "\uE17E", "\uE17F", "\uE290", "\uE291", "\uE292", "\uE293",
                    "\uE294", "\uE295", "\uE296", "\uE297", "\uE298", "\uE299", "\uE29A", "\uE29B", "\uE29C", "\uE29D",
                    "\uE29E", "\uE29F", "\uE2B0", "\uE2B1", "\uE2B2", "\uE2B3", "\uE2B4", "\uE2B5", "\uE2B6", "\uE2B7",
                    "\uE2B8", "\uE2B9"));
    public static final Fonts RIGHT_SUI_EATING = new Fonts(PORTRAIT_FONT, List.of("\uE17A", "\uE17B"));
    public static final Fonts LEFT_SUI = new Fonts(
            PORTRAIT_FONT,
            List.of(
                    "\uE180", "\uE181", "\uE182", "\uE183", "\uE184", "\uE185", "\uE186", "\uE187", "\uE188", "\uE189",
                    "\uE18A", "\uE18B", "\uE18C", "\uE18D", "\uE18E", "\uE18F", "\uE2A0", "\uE2A1", "\uE2A2", "\uE2A3",
                    "\uE2A4", "\uE2A5", "\uE2A6", "\uE2A7", "\uE2A8", "\uE2A9", "\uE2AA", "\uE2AB", "\uE2AC", "\uE2AD",
                    "\uE2AE", "\uE2AF", "\uE2C0", "\uE2C1", "\uE2C2", "\uE2C3", "\uE2C4", "\uE2C5", "\uE2C6", "\uE2C7",
                    "\uE2C8", "\uE2C9"));
    public static final Fonts LEFT_SUI_EATING = new Fonts(PORTRAIT_FONT, List.of("\uE18A", "\uE18B"));

    // When replacing a portrait, we need to make sure the "used" area of the sprite matches the
    // original.

    // For example. The first Sui portrait has 4 blank pixels before the sprite begins, and after that
    // it uses every
    // remaining pixel in the width of 48 for a total of 44 pixels used.
    // If we replace it with one that has 3 blank pixels at the start, then we need to add the
    // character representing
    // a +1 pixel offset before it with the space font; the portrait font references this already.
    public static Map<String, String> PREFIX_OFFSETS = Map.ofEntries(
            Map.entry("\uE170", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE171", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE172", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE173", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE174", "\uDAFF\uDFFD"), // Unverified
            Map.entry("\uE175", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE176", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE177", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE178", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE179", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE17C", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE17D", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE17E", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE17F", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE290", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE294", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE296", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE297", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE298", "\uDAFF\uDFFB"), // Unverified
            Map.entry("\uE299", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE29A", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE29B", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE29C", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE29D", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE29E", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE29F", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE2B0", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B1", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE2B2", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2B3", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE2B4", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2B5", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2B6", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE2B7", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2B8", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE2B9", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE180", "\uDB00\uDC01"), // Verified
            Map.entry("\uE184", "\uDB00\uDC02"), // Verified
            Map.entry("\uE186", "\uDB00\uDC01"), // Verified
            Map.entry("\uE187", "\uDB00\uDC01"), // Verified
            Map.entry("\uE189", "\uDAFF\uDFFE"), // Verified
            Map.entry("\uE18D", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE18E", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE18F", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2A0", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2A1", "\uDAFF\uDFFE"), // Unverified
            Map.entry("\uE2A2", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2A3", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2A5", "\uDAFF\uDFFE"), // Unverified
            Map.entry("\uE2A7", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2A8", "\uDAFF\uDFFD"), // Unverified
            Map.entry("\uE2A9", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AA", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AB", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2AC", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AD", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AE", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AF", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2C0", "\uDAFF\uDFFE"), // Unverified
            Map.entry("\uE2C1", "\uDAFF\uDFFF"), // Unverified
            Map.entry("\uE2C2", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C4", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C5", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C6", "\uDAFF\uDFFD"), // Unverified
            Map.entry("\uE2C7", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C9", "\uDAFF\uDFFE"));// Unverified

    // Then we need to fill in the end with the same principal. If the original sprite goes all the
    // way to the end of
    // the width, but the one we are replacing does not, we need to pad it to now fit.
    // However, this needs to take into account the prefix character added. So if we need to add a +2
    // gap, but we already
    // added 1 in the prefix, we only need to add 1 here instead of 2.
    public static Map<String, String> SUFFIX_OFFSETS = Map.ofEntries(
            Map.entry("\uE170", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE171", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE172", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE173", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE174", "\uDB00\uDC05"), // Unverified
            Map.entry("\uE175", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE176", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE177", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE178", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE179", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE17C", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE17D", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE17E", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE17F", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE290", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE294", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE296", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE297", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE298", "\uDB00\uDC00"), // Unverified
            Map.entry("\uE299", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE29A", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE29B", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE29C", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE29D", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE29E", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE29F", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B0", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2B1", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B2", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2B3", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B4", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2B5", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2B6", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B7", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2B8", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2B9", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE180", "\uDB00\uDC01"), // Verified
            Map.entry("\uE181", "\uDB00\uDC01"), // Verified
            Map.entry("\uE182", "\uDB00\uDC02"), // Verified
            Map.entry("\uE183", "\uDB00\uDC02"), // Verified
            Map.entry("\uE185", "\uDB00\uDC02"), // Verified
            Map.entry("\uE186", "\uDB00\uDC01"), // Verified
            Map.entry("\uE187", "\uDB00\uDC01"), // Verified
            Map.entry("\uE188", "\uDB00\uDC02"), // Verified
            Map.entry("\uE189", "\uDB00\uDC04"), // Verified
            Map.entry("\uE18C", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE18D", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE18E", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE18F", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2A0", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2A1", "\uDB00\uDC04"), // Unverified
            Map.entry("\uE2A2", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2A3", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2A4", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2A5", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2A6", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2A7", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2A8", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2A9", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AA", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AB", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2AC", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AD", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AE", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2AF", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2C0", "\uDB00\uDC04"), // Unverified
            Map.entry("\uE2C1", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2C2", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C3", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2C4", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C5", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C6", "\uDB00\uDC03"), // Unverified
            Map.entry("\uE2C7", "\uDB00\uDC01"), // Unverified
            Map.entry("\uE2C8", "\uDB00\uDC02"), // Unverified
            Map.entry("\uE2C9", "\uDB00\uDC03"));// Unverified
}
