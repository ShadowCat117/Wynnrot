/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.utils.ComponentUtils;
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

    @Inject(
            method = "update(Lnet/minecraft/network/protocol/game/ClientboundBossEventPacket;)V",
            at = @At("HEAD"),
            order = 999)
    private void updatePre(ClientboundBossEventPacket packet, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft() || (!WynnrotConfig.horseDeath() && !WynnrotConfig.leBigFishe())) return;

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

        if (WynnrotConfig.leBigFishe()) {
            ClientboundBossEventPacket.Operation operation = packet.operation;

            if (operation instanceof ClientboundBossEventPacket.AddOperation addOperation) {
                Component name = addOperation.name;
                if (!name.getSiblings().isEmpty()) {
                    Component mobName = name.getSiblings().getFirst();

                    if (mobName.getString().equals("The Piranha")) {
                        addOperation.name = ComponentUtils.replaceFirstSibling(addOperation.name, "Le Big Fishe");
                    }
                }
                return;
            }

            if (operation instanceof ClientboundBossEventPacket.UpdateNameOperation updateOperation
                    && !updateOperation.name.getSiblings().isEmpty()) {
                Component name = updateOperation.name;
                if (!name.getSiblings().isEmpty()) {
                    Component mobName = name.getSiblings().getFirst();

                    if (mobName.getString().equals("The Piranha")) {
                        updateOperation.name = ComponentUtils.replaceFirstSibling(updateOperation.name, "Le Big Fishe");
                    }
                }
            }
        }
    }
}
