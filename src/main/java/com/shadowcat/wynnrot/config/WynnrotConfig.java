/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.config;

import com.shadowcat.wynnrot.Wynnrot;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.loader.api.FabricLoader;

public class WynnrotConfig {
    private static final File CONFIG_FILE =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "Wynnrot.json");

    private static Config config;

    private static class Config {
        boolean sixSevenQueen = true;
        int sixSevenQueenUpdateRate = 2;
        boolean eternalHungerSui = true;
        int eternalHungerSuiUpdateRate = 4;
        boolean meow = true;
        int meowChance = 100;
        boolean leBigFishe = true;
        RainbowTextOptions rainbowText = RainbowTextOptions.HUD_ONLY;
        boolean horseDeath = true;
    }

    public static void init() {
        loadConfig();
    }

    public static boolean editActionBar() {
        return config.eternalHungerSui || config.sixSevenQueen;
    }

    public static boolean sixSevenQueen() {
        return config.sixSevenQueen;
    }

    public static void updateSixSevenQueen(boolean value) {
        config.sixSevenQueen = value;
        saveConfig();
    }

    public static int sixSevenQueenUpdateRate() {
        return config.sixSevenQueenUpdateRate;
    }

    public static void updateSixSevenQueenUpdateRate(int value) {
        config.sixSevenQueenUpdateRate = value;
        saveConfig();
    }

    public static boolean eternalHungerSui() {
        return config.eternalHungerSui;
    }

    public static void updateEternalHungerSui(boolean value) {
        config.eternalHungerSui = value;
        saveConfig();
    }

    public static int eternalHungerSuiUpdateRate() {
        return config.eternalHungerSuiUpdateRate;
    }

    public static void updateEternalHungerSuiUpdateRate(int value) {
        config.eternalHungerSuiUpdateRate = value;
        saveConfig();
    }

    public static boolean meow() {
        return config.meow;
    }

    public static void updateMeow(boolean value) {
        config.meow = value;
        saveConfig();
    }

    public static int meowChance() {
        return config.meowChance;
    }

    public static void updateMeowChance(int value) {
        config.meowChance = value;
        saveConfig();
    }

    public static boolean leBigFishe() {
        return config.leBigFishe;
    }

    public static void updateLeBigFishe(boolean value) {
        config.leBigFishe = value;
        saveConfig();
    }

    public static RainbowTextOptions rainbowText() {
        return config.rainbowText;
    }

    public static void updateRainbowText(RainbowTextOptions value) {
        config.rainbowText = value;
        saveConfig();
    }

    public static boolean horseDeath() {
        return config.horseDeath;
    }

    public static void updateHorseDeath(boolean value) {
        config.horseDeath = value;
        saveConfig();
    }

    private static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader fileReader = new FileReader(CONFIG_FILE)) {
                config = Wynnrot.GSON.fromJson(fileReader, Config.class);
            } catch (IOException e) {
                Wynnrot.error("Failed to load config", e);
            }
        }
        if (config == null) {
            config = new Config();
            saveConfig();
        }
    }

    private static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            Wynnrot.GSON.toJson(config, writer);
        } catch (IOException e) {
            Wynnrot.error("Failed to save config", e);
        }
    }
}
