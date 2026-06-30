/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.data;

import com.shadowcat.wynnrot.utils.McUtils;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public final class HeartProblemsData {
    private static final Random RANDOM = new Random();

    private static int ticks = -1;
    private static int messageNum = 1;

    public static void tick() {
        if (ticks == -1) {
            if (McUtils.tickCount() % 20 != 0) return;
            if (McUtils.screen() != null) return;
            if (McUtils.mc().getCameraEntity() == null
                    || !McUtils.mc().getCameraEntity().equals(McUtils.player())) return;

            int randomIndex = RANDOM.nextInt(30000000);

            if (randomIndex != 0) return;
        }

        switch (ticks) {
            case 0:
                triggerHeartAttack();
                break;
            case 18, 42, 60, 74, 80:
                messageNum++;
                sendMessage();
                break;
            case 20, 36, 48, 58, 66, 72, 76, 92:
                triggerHeartbeat();

                if (ticks == 72) {
                    McUtils.sendCommand("kill");
                } else if (ticks == 92) {
                    ticks = -1;
                    messageNum = 1;
                    return;
                }
                break;
        }

        ticks++;
    }

    public static int currentTick() {
        return ticks;
    }

    private static void triggerHeartAttack() {
        sendMessage();

        triggerHeartbeat();
    }

    public static void triggerHeartbeat() {
        McUtils.playSound(SoundEvents.WARDEN_HEARTBEAT);
    }

    private static void sendMessage() {
        McUtils.sendMessageToClient(Component.translatable("wynnrot.heartProblems.message" + messageNum)
                .withStyle(ChatFormatting.GRAY));
    }
}
