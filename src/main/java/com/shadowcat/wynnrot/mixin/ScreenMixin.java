/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.BouncingQueenOptions;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.AnimatedSprite;
import com.shadowcat.wynnrot.utils.ComponentUtils;
import com.shadowcat.wynnrot.utils.GuiGraphicsUtils;
import com.shadowcat.wynnrot.utils.McUtils;
import com.shadowcat.wynnrot.utils.MixinUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Unique
    private static final long adStartTime = System.currentTimeMillis();

    @Inject(
            method = "renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderDeferredElements()V"))
    private void renderPreDeferredElements(
            GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.adBanner()) return;

        Screen screen = (Screen) (Object) this;
        if (screen instanceof AbstractContainerScreen<?> abstractContainerScreen) {
            long elapsed = System.currentTimeMillis() - adStartTime;
            int frame = AnimatedSprite.AD_BANNER.getFrame(elapsed);

            int x = abstractContainerScreen.leftPos + (abstractContainerScreen.imageWidth - 234) / 2;
            int y = abstractContainerScreen.topPos + abstractContainerScreen.imageHeight + 8;

            GuiGraphicsUtils.submitAnimatedSprite(guiGraphics, AnimatedSprite.AD_BANNER, frame, x, y, 234, 30);
        }
    }

    @Inject(method = "renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("RETURN"))
    private void renderPost(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || WynnrotConfig.dancingQueen() == BouncingQueenOptions.NOWHERE) return;

        Screen screen = (Screen) (Object) this;
        boolean isContainer = screen instanceof AbstractContainerScreen<?>;
        boolean isInventory = screen instanceof InventoryScreen;

        if (!shouldRenderQueen(isContainer, isInventory, WynnrotConfig.dancingQueen())) return;

        ComponentUtils.submitDancingQueen(guiGraphics, McUtils.deltaTracker());
    }

    @Unique
    private boolean shouldRenderQueen(boolean isContainer, boolean isInventory, BouncingQueenOptions option) {
        if (option == BouncingQueenOptions.EVERYWHERE) {
            return true;
        } else if (option == BouncingQueenOptions.ALL_CONTAINERS && isContainer) {
            return true;
        } else if (option == BouncingQueenOptions.INVENTORY && isInventory) {
            return true;
        } else {
            return option == BouncingQueenOptions.ALL_SCREENS;
        }
    }
}
