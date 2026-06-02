/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shadowcat.wynnrot.config.WynnrotConfig;
import com.shadowcat.wynnrot.utils.ModUpdater;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wynnrot implements ModInitializer {
    public static final String MOD_ID = "wynnrot";
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static String version = "";

    @Override
    public void onInitialize() {
        WynnrotConfig.init();

        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);
        if (modContainer.isPresent()) {
            version = modContainer.get().getMetadata().getVersion().getFriendlyString();
        } else {
            error("Where is my Wynnrot :(");
            return;
        }

        ModUpdater.checkForUpdate();
    }

    public static String getVersion() {
        return version;
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object arg) {
        LOGGER.error(message, arg);
    }

    public static String toEscapedUnicode(String s) {
        StringBuilder out = new StringBuilder();
        for (char c : s.toCharArray()) {
            out.append(String.format("\\u%04X", (int) c));
        }
        return out.toString();
    }
}
