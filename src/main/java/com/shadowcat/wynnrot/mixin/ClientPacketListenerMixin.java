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
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
  @WrapMethod(
      method = "setTitleText(Lnet/minecraft/network/protocol/game/ClientboundSetTitleTextPacket;)V",
      order = 999)
  private void wrapSetTitleText(ClientboundSetTitleTextPacket packet, Operation<Void> original) {
    if (!WynnrotConfig.leBigFish()) {
      original.call(packet);
      return;
    }

    Component component = packet.text();

    String plainText = ChatFormatting.stripFormatting(component.getString());
    if (plainText.equals("The Piranha") || plainText.equals("The Corrupted Piranha")) {
      ClientboundSetTitleTextPacket newPacket =
          new ClientboundSetTitleTextPacket(Component.literal("Le Big Fish").withColor(0x00f010));

      original.call(newPacket);
    } else {
      original.call(packet);
    }
  }
}
