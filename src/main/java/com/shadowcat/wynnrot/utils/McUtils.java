/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;

public final class McUtils {
    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer player() {
        return mc().player;
    }

    public static Font font() {
        return mc().font;
    }

    public static boolean hudHidden() {
        return mc().options.hideGui;
    }

    public static Screen screen() {
        return mc().screen;
    }

    public static int tickCount() {
        return player().tickCount;
    }

    public static DeltaTracker deltaTracker() {
        return mc().getDeltaTracker();
    }

    public static String serverBrand() {
        ClientPacketListener clientPacketListener = mc().getConnection();

        if (clientPacketListener == null) return "";

        return clientPacketListener.serverBrand();
    }

    public static void sendMessageToClient(Component message) {
        mc().getChatListener().handleSystemMessage(message, false);
    }

    public static void sendWynnrotMessage(Component message) {
        sendMessageToClient(ComponentUtils.addWynnrotHeader(message));
    }

    public static void sendCommand(String command) {
        if (mc().getConnection() == null) return;

        mc().getConnection().sendCommand(command);
    }

    public static void playSound(SoundEvent sound) {
        mc().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(sound, 1.0f, 1.0f));
    }
}
