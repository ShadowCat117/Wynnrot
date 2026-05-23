/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import java.util.List;
import java.util.Random;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public final class SoundUtils {
    private static final List<SoundEvent> CAT_SOUNDS = List.of(
            SoundEvents.CAT_AMBIENT,
            SoundEvents.CAT_STRAY_AMBIENT,
            SoundEvents.CAT_HISS,
            SoundEvents.CAT_BEG_FOR_FOOD,
            SoundEvents.CAT_PURR,
            SoundEvents.CAT_PURREOW);

    public static SoundEvent getRandomCatSound() {
        Random random = new Random();
        int randomIndex = random.nextInt(CAT_SOUNDS.size());
        return CAT_SOUNDS.get(randomIndex);
    }
}
