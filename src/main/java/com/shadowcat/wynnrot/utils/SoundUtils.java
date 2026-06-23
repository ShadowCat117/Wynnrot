/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import com.shadowcat.wynnrot.Wynnrot;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;

public final class SoundUtils {
    private static List<SoundEvent> catSounds = List.of();

    public static SoundEvent getRandomCatSound() {
        if (catSounds.isEmpty()) {
            try {
                CatSoundVariant.CatSoundSet soundSet = McUtils.player()
                        .registryAccess()
                        .lookupOrThrow(Registries.CAT_SOUND_VARIANT)
                        .get(CatSoundVariants.CLASSIC)
                        .orElseThrow()
                        .value()
                        .adultSounds();

                catSounds = List.of(
                        soundSet.ambientSound().value(),
                        soundSet.strayAmbientSound().value(),
                        soundSet.hissSound().value(),
                        soundSet.begForFoodSound().value(),
                        soundSet.purrSound().value(),
                        soundSet.purreowSound().value());
            } catch (IllegalStateException e) {
                Wynnrot.error("Failed to access cat sounds :(", e);
                return null;
            } catch (NoSuchElementException e) {
                Wynnrot.error("Failed to find cat sounds :(", e);
                return null;
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(catSounds.size());
        return catSounds.get(randomIndex);
    }
}
