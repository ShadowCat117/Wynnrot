/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.RainbowTextOptions;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.Colours;
import com.shadowcat.wynnrot.utils.MixinUtils;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BakedSheetGlyph.class)
public class BakedSheetGlyphMixin {

    @ModifyVariable(
            method =
                    "renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/vertex/VertexConsumer;IZ)V",
            at = @At("STORE"),
            name = "color")
    private int modifyMainColor(int color) {
        if (MixinUtils.onWynncraft() && WynnrotConfig.rainbowText() == RainbowTextOptions.ALL_TEXT) {
            return Colours.RAINBOW;
        }

        return color;
    }
}
