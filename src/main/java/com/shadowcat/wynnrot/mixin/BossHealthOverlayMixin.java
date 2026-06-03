/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.utils.McUtils;
import com.shadowcat.wynnrot.utils.MixinUtils;
import java.util.UUID;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {
    @Unique
    private float horseVolume = 1.0f;

    @Inject(method = "update(Lnet/minecraft/network/protocol/game/ClientboundBossEventPacket;)V", at = @At("HEAD"))
    private void updatePre(ClientboundBossEventPacket packet, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.horseDeath()) return;

        packet.dispatch(new ClientboundBossEventPacket.Handler() {
            @Override
            public void add(
                    UUID id,
                    Component name,
                    float progress,
                    BossEvent.BossBarColor color,
                    BossEvent.BossBarOverlay overlay,
                    boolean darkenScreen,
                    boolean playMusic,
                    boolean createWorldFog) {
                if (name.getString().equals("\uE000\uE002\uE000")) {
                    McUtils.mc()
                            .getSoundManager()
                            .play(SimpleSoundInstance.forLocalAmbience(SoundEvents.HORSE_DEATH, 0.5f, horseVolume));

                    horseVolume = Math.clamp(horseVolume + 0.1f, 0.1f, 100.0f);
                }
            }
        });
    }
}
