/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.BouncingQueenOptions;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.utils.ComponentUtils;
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
    @Inject(method = "renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("RETURN"))
    private void renderPost(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || WynnrotConfig.dancingQueen() == BouncingQueenOptions.NOWHERE) return;

        Screen screen = (Screen) (Object) this;
        boolean isContainer = screen instanceof AbstractContainerScreen<?>;
        boolean isInventory = screen instanceof InventoryScreen;

        if (!shouldRender(isContainer, isInventory, WynnrotConfig.dancingQueen())) return;

        ComponentUtils.submitDancingQueen(guiGraphics, McUtils.deltaTracker());
    }

    @Unique
    private boolean shouldRender(boolean isContainer, boolean isInventory, BouncingQueenOptions option) {
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
