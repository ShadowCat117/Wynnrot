/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.data.Colours;
import com.shadowcat.wynnrot.utils.ComponentUtils;
import com.shadowcat.wynnrot.utils.McUtils;
import com.shadowcat.wynnrot.utils.MixinUtils;
import com.shadowcat.wynnrot.utils.ModUpdater;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

    @ModifyArg(
            method = "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/network/syncher/SynchedEntityData;assignValues(Ljava/util/List;)V"),
            index = 0,
            order = 999)
    private List<SynchedEntityData.DataValue<?>> handleSetEntityDataPre(
            List<SynchedEntityData.DataValue<?>> packedItems,
            @Local(argsOnly = true) ClientboundSetEntityDataPacket packet) {
        if (!MixinUtils.onWynncraft() || !WynnrotConfig.leBigFishe() || McUtils.mc().level == null) return packedItems;

        Entity entity = McUtils.mc().level.getEntity(packet.id());
        if (!(entity instanceof Display.TextDisplay)) return packedItems;

        for (SynchedEntityData.DataValue<?> packedItem : packedItems) {
            if (packedItem.id() == Display.TextDisplay.DATA_TEXT_ID.id()) {
                Component component = (Component) packedItem.value();

                if (component.getSiblings().isEmpty()) return packedItems;

                Component mobName = component.getSiblings().getFirst();

                if (mobName.getString().equals("The Piranha")) {
                    SynchedEntityData.DataValue<Component> replacedText = new SynchedEntityData.DataValue<>(
                            Display.TextDisplay.DATA_TEXT_ID.id(),
                            (EntityDataSerializer<Component>) packedItem.serializer(),
                            ComponentUtils.replaceFirstSibling(component, "Le Big Fishe"));
                    packedItems.remove(packedItem);
                    packedItems.add(replacedText);
                }
            }
        }

        return packedItems;
    }
}
