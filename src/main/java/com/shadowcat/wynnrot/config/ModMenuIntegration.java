/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder configBuilder =
                    ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("wynnrot.config"));
            ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

            // region HUD
            ConfigCategory hud =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.hud"));

            hud.addEntry(entryBuilder
                    .startEnumSelector(
                            Component.translatable("wynnrot.config.dancingQueen.name"),
                            BouncingQueenOptions.class,
                            WynnrotConfig.dancingQueen())
                    .setDefaultValue(BouncingQueenOptions.EVERYWHERE)
                    .setTooltip(Component.translatable("wynnrot.config.dancingQueen.description"))
                    .setSaveConsumer(WynnrotConfig::updateDancingQueen)
                    .build());

            hud.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.eternalHungerSui.name"),
                            WynnrotConfig.eternalHungerSui())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.eternalHungerSui.description"))
                    .setSaveConsumer(WynnrotConfig::updateEternalHungerSui)
                    .build());
            hud.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.eternalHungerSuiUpdateRate.name"),
                            WynnrotConfig.eternalHungerSuiUpdateRate(),
                            1,
                            100)
                    .setDefaultValue(4)
                    .setTooltip(Component.translatable("wynnrot.config.eternalHungerSuiUpdateRate.description"))
                    .setSaveConsumer(WynnrotConfig::updateEternalHungerSuiUpdateRate)
                    .build());

            hud.addEntry(entryBuilder
                    .startEnumSelector(
                            Component.translatable("wynnrot.config.rainbowText.name"),
                            RainbowTextOptions.class,
                            WynnrotConfig.rainbowText())
                    .setDefaultValue(RainbowTextOptions.HUD_ONLY)
                    .setTooltip(Component.translatable("wynnrot.config.rainbowText.description"))
                    .setSaveConsumer(WynnrotConfig::updateRainbowText)
                    .build());

            hud.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.sixSevenQueen.name"), WynnrotConfig.sixSevenQueen())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.sixSevenQueen.description"))
                    .setSaveConsumer(WynnrotConfig::updateSixSevenQueen)
                    .build());
            hud.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.sixSevenQueenUpdateRate.name"),
                            WynnrotConfig.sixSevenQueenUpdateRate(),
                            1,
                            100)
                    .setDefaultValue(2)
                    .setTooltip(Component.translatable("wynnrot.config.sixSevenQueenUpdateRate.description"))
                    .setSaveConsumer(WynnrotConfig::updateSixSevenQueenUpdateRate)
                    .build());
            // endregion

            // region Sounds
            ConfigCategory sounds =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.sounds"));

            sounds.addEntry(entryBuilder
                    .startBooleanToggle(Component.translatable("wynnrot.config.meow.name"), WynnrotConfig.meow())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.meow.description"))
                    .setSaveConsumer(WynnrotConfig::updateMeow)
                    .build());
            sounds.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.meowChance.name"),
                            WynnrotConfig.meowChance(),
                            1,
                            100)
                    .setDefaultValue(100)
                    .setTooltip(Component.translatable("wynnrot.config.meowChance.description"))
                    .setSaveConsumer(WynnrotConfig::updateMeowChance)
                    .build());

            sounds.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.horseDeath.name"), WynnrotConfig.horseDeath())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.horseDeath.description"))
                    .setSaveConsumer(WynnrotConfig::updateHorseDeath)
                    .build());
            // endregion

            // region Gameplay
            ConfigCategory gameplay =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.gameplay"));

            gameplay.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.heartProblems.name"), WynnrotConfig.heartProblems())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.heartProblems.description"))
                    .setSaveConsumer(WynnrotConfig::updateHeartProblems)
                    .build());
            // endregion

            // region Mobs
            ConfigCategory mobs =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.mobs"));

            mobs.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.leBigFishe.name"), WynnrotConfig.leBigFishe())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.leBigFishe.description"))
                    .setSaveConsumer(WynnrotConfig::updateLeBigFishe)
                    .build());
            // endregion

            return configBuilder.build();
        };
    }
}
