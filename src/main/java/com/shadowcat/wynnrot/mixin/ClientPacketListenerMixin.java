/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.Colours;
import com.shadowcat.wynnrot.utils.MixinUtils;
import com.shadowcat.wynnrot.utils.ModUpdater;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @WrapMethod(method = "setTitleText(Lnet/minecraft/network/protocol/game/ClientboundSetTitleTextPacket;)V")
    private void wrapSetTitleText(ClientboundSetTitleTextPacket packet, Operation<Void> original) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.leBigFishe()) {
            original.call(packet);
            return;
        }

        Component component = packet.text();

        String plainText = ChatFormatting.stripFormatting(component.getString());
        if (plainText.equals("The Piranha") || plainText.equals("The Corrupted Piranha")) {
            String bigFisheText = plainText.contains("Corrupted") ? "Le Corrupted Big Fishe" : "Le Big Fishe";
            ClientboundSetTitleTextPacket newPacket = new ClientboundSetTitleTextPacket(
                    Component.literal(bigFisheText).withColor(Colours.CRIMSON));

            original.call(newPacket);
        } else {
            original.call(packet);
        }
    }

    @WrapMethod(method = "setSubtitleText(Lnet/minecraft/network/protocol/game/ClientboundSetSubtitleTextPacket;)V")
    private void wrapSetTitleText(ClientboundSetSubtitleTextPacket packet, Operation<Void> original) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.leBigFishe()) {
            original.call(packet);
            return;
        }

        Component component = packet.text();

        String plainText = ChatFormatting.stripFormatting(component.getString());
        if (plainText.equals("The Piranha") || plainText.equals("The Corrupted Piranha")) {
            String bigFisheText = plainText.contains("Corrupted") ? "Le Corrupted Big Fishe" : "Le Big Fishe";
            ClientboundSetSubtitleTextPacket newPacket = new ClientboundSetSubtitleTextPacket(
                    Component.literal(bigFisheText).withColor(Colours.CRIMSON));

            original.call(newPacket);
        } else {
            original.call(packet);
        }
    }

    @Inject(
            method = "handlePlayerInfoUpdate(Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket;)V",
            at = @At("RETURN"))
    private void handlePlayerInfoUpdatePost(ClientboundPlayerInfoUpdatePacket packet, CallbackInfo ci) {
        if (!MixinUtils.onWynncraft()) return;

        for (ClientboundPlayerInfoUpdatePacket.Entry entry : packet.entries()) {
            for (ClientboundPlayerInfoUpdatePacket.Action action : packet.actions()) {
                if (action == ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME) {
                    if (ModUpdater.trySendUpdatePrompt(entry.profileId(), entry.displayName())) return;
                }
            }
        }
    }
}
