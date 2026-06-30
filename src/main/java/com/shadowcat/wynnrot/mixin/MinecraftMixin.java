/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.HeartProblemsData;
import com.shadowcat.wynnrot.utils.MixinUtils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "tick()V", at = @At("RETURN"))
    private void tickPost(CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.heartProblems()) return;

        HeartProblemsData.tick();
    }
}
