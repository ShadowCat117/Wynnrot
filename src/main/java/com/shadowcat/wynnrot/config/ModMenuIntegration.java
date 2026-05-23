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

            // region Portraits
            ConfigCategory portraits =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.portraits"));

            portraits.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.sixSevenQueen.name"), WynnrotConfig.sixSevenQueen())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.sixSevenQueen.description"))
                    .setSaveConsumer(WynnrotConfig::updateSixSevenQueen)
                    .build());
            portraits.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.sixSevenQueenUpdateRate.name"),
                            WynnrotConfig.sixSevenQueenUpdateRate(),
                            1,
                            100)
                    .setDefaultValue(2)
                    .setTooltip(Component.translatable("wynnrot.config.sixSevenQueenUpdateRate.description"))
                    .setSaveConsumer(WynnrotConfig::updateSixSevenQueenUpdateRate)
                    .build());

            portraits.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.eternalHungerSui.name"),
                            WynnrotConfig.eternalHungerSui())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.eternalHungerSui.description"))
                    .setSaveConsumer(WynnrotConfig::updateEternalHungerSui)
                    .build());
            portraits.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.eternalHungerSuiUpdateRate.name"),
                            WynnrotConfig.eternalHungerSuiUpdateRate(),
                            1,
                            100)
                    .setDefaultValue(4)
                    .setTooltip(Component.translatable("wynnrot.config.eternalHungerSuiUpdateRate.description"))
                    .setSaveConsumer(WynnrotConfig::updateEternalHungerSuiUpdateRate)
                    .build());
            // endregion

            // region Chat
            ConfigCategory chat =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.chat"));

            chat.addEntry(entryBuilder
                    .startBooleanToggle(Component.translatable("wynnrot.config.meow.name"), WynnrotConfig.meow())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.meow.description"))
                    .setSaveConsumer(WynnrotConfig::updateMeow)
                    .build());
            chat.addEntry(entryBuilder
                    .startIntSlider(
                            Component.translatable("wynnrot.config.meowChance.name"),
                            WynnrotConfig.meowChance(),
                            1,
                            100)
                    .setDefaultValue(100)
                    .setTooltip(Component.translatable("wynnrot.config.meowChance.description"))
                    .setSaveConsumer(WynnrotConfig::updateMeowChance)
                    .build());
            // endregion

            // region Titles
            ConfigCategory titles =
                    configBuilder.getOrCreateCategory(Component.translatable("wynnrot.config.category.titles"));

            titles.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("wynnrot.config.leBigFish.name"), WynnrotConfig.leBigFish())
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("wynnrot.config.leBigFish.description"))
                    .setSaveConsumer(WynnrotConfig::updateLeBigFish)
                    .build());
            // endregion

            return configBuilder.build();
        };
    }
}
