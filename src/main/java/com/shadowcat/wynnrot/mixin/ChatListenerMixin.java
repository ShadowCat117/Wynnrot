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
import com.shadowcat.wynnrot.data.Fonts;
import com.shadowcat.wynnrot.utils.ActionBarUtils;
import com.shadowcat.wynnrot.utils.McUtils;
import com.shadowcat.wynnrot.utils.MixinUtils;
import com.shadowcat.wynnrot.utils.SoundUtils;
import java.util.Locale;
import java.util.Random;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
  @Unique private static final Random RANDOM = new Random();

  @WrapMethod(method = "handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V", order = 999)
  private void wrapHandleSystemMessage(
      Component message, boolean isOverlay, Operation<Void> original) {
    if (!MixinUtils.onWynncraft() || !isOverlay || !WynnrotConfig.editActionBar()) {
      original.call(message, isOverlay);
      return;
    }

    Component newComponent = message;

    if (WynnrotConfig.sixSevenQueen()) {
      newComponent =
          ActionBarUtils.replaceCharacterAnimated(
              newComponent,
              "\uE0D2",
              Fonts.FRUMA_QUEEN_67.characters(),
              WynnrotConfig.sixSevenQueenUpdateRate(),
              Identifier.withDefaultNamespace("hud/dialogue/portrait"),
              new FontDescription.Resource(Fonts.FRUMA_QUEEN_67.identifier()));
    }

    if (WynnrotConfig.eternalHungerSui()) {
      newComponent =
          ActionBarUtils.replaceCharactersAnimated(
              newComponent,
              Fonts.RIGHT_SUI.characters(),
              Fonts.RIGHT_SUI_EATING.characters(),
              WynnrotConfig.eternalHungerSuiUpdateRate(),
              Fonts.RIGHT_SUI.identifier(),
              new FontDescription.Resource(Fonts.RIGHT_SUI_EATING.identifier()));

      newComponent =
          ActionBarUtils.replaceCharactersAnimated(
              newComponent,
              Fonts.LEFT_SUI.characters(),
              Fonts.LEFT_SUI_EATING.characters(),
              WynnrotConfig.eternalHungerSuiUpdateRate(),
              Fonts.LEFT_SUI.identifier(),
              new FontDescription.Resource(Fonts.LEFT_SUI_EATING.identifier()));
    }

    original.call(newComponent, true);
  }

  @Inject(
      method = "handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V",
      at = @At("HEAD"),
      order = 999)
  private void handleSystemMessagePre(Component message, boolean isOverlay, CallbackInfo ci) {
    if (!MixinUtils.onWynncraft() || !WynnrotConfig.meow() || WynnrotConfig.meowChance() == 0) {
      return;
    }

    if (message.getString().toLowerCase(Locale.ROOT).contains("meow")
        && RANDOM.nextInt(100) < WynnrotConfig.meowChance()) {
      McUtils.mc()
          .getSoundManager()
          .play(SimpleSoundInstance.forLocalAmbience(SoundUtils.getRandomCatSound(), 1.0f, 1.0f));
    }
  }
}
