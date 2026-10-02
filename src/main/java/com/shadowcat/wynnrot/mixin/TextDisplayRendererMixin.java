/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.utils.McUtils;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.state.TextDisplayEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayRenderer.TextDisplayRenderer.class)
public class TextDisplayRendererMixin {
    @Unique
    private static final Pattern SHARE_PATTERN =
            Pattern.compile("Shares are .+\non the Trade Market!\nClick to browse");

    @Unique
    private static final Pattern BROWSE_PATTERN =
            Pattern.compile("Click to browse Store\nRanks, Crates,\nPets & more!");

    @Unique
    private static final Pattern OFFER_PATTERN =
            Pattern.compile(".+ Offer\n(?:\\d+h )?(?:\\d+m )?(?:\\d+s)?\n\n\\d+% OFF .+");

    @Unique
    private static final Pattern FREE_CRATE_PATTERN = Pattern.compile("Free Crate Available!\nClick to claim");

    @Unique
    private static final Pattern OPEN_CRATE_PATTERN = Pattern.compile("You have \\d+ Crates?\nClick to open them!");

    @Unique
    private static final Pattern GET_CRATES_PATTERN =
            Pattern.compile("Get Crates for hats,\npets, weapon skins\n& more cosmetics!");

    @Inject(
            method =
                    "submitInner(Lnet/minecraft/client/renderer/entity/state/TextDisplayEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IF)V",
            at = @At("HEAD"))
    private void onRender(
            TextDisplayEntityRenderState textDisplayEntityRenderState,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int lightCoords,
            float interpolationProgress,
            CallbackInfo ci) {
        if (!WynnrotConfig.obnoxiousPodium()) return;

        Component text = textDisplayEntityRenderState.textRenderState.text();
        String string = text.getString();

        float phase;

        if (SHARE_PATTERN.matcher(string).matches()) {
            phase = 0.0f;
        } else if (BROWSE_PATTERN.matcher(string).matches()
                || OFFER_PATTERN.matcher(string).matches()) {
            phase = 2.0f;
        } else if (FREE_CRATE_PATTERN.matcher(string).matches()
                || OPEN_CRATE_PATTERN.matcher(string).matches()
                || GET_CRATES_PATTERN.matcher(string).matches()) {
            phase = 4.0f;
        } else {
            return;
        }

        float ticks = McUtils.tickCount() + interpolationProgress;
        float animation = Mth.sin((ticks + phase) * 0.35f);
        float progress = (animation + 1.0f) * 0.5f;
        float scale = 1.0f + progress;

        float forward = progress * 3.0f;

        poseStack.translate(0.0f, 0.0f, forward);
        poseStack.scale(scale, scale, scale);
    }
}
