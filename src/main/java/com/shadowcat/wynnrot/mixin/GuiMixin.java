/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.BouncingQueenOptions;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.HeartProblemsData;
import com.shadowcat.wynnrot.utils.ComponentUtils;
import com.shadowcat.wynnrot.utils.McUtils;
import com.shadowcat.wynnrot.utils.MixinUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V",
            at = @At("RETURN"))
    private void renderPost(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || McUtils.hudHidden() || McUtils.screen() != null) return;

        if (WynnrotConfig.dancingQueen() == BouncingQueenOptions.EVERYWHERE) {
            ComponentUtils.submitDancingQueen(guiGraphics, deltaTracker);
        }

        if (HeartProblemsData.currentTick() >= 62) {
            float progress = (HeartProblemsData.currentTick() - 62) / 30.0F;
            progress = Math.min(progress, 1.0F);

            int alpha = (int) (progress * 255);
            int colour = (alpha << 24);

            guiGraphics.fill(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), colour);
        }
    }
}
