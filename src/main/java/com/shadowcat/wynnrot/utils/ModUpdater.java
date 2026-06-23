/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shadowcat.wynnrot.Wynnrot;
import com.shadowcat.wynnrot.data.Colours;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.util.version.VersionParser;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class ModUpdater {
    private static final Pattern WORLD_PATTERN = Pattern.compile("^§f {2}§lGlobal \\[(.*)]$");
    private static final UUID WORLD_UUID = UUID.fromString("16ff7452-714f-2752-b3cd-c3cb2068f6af");

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static boolean updateAvailable = false;
    private static String latestVersion = "";
    private static String latestId = "";

    private static boolean promptedUpdate = false;

    public static void checkForUpdate() {
        CompletableFuture.runAsync(() -> {
            try {
                String mcVersion = SharedConstants.getCurrentVersion().name();
                String encodedVersion = URLEncoder.encode("[\"" + mcVersion + "\"]", StandardCharsets.UTF_8);

                URI uri = URI.create(
                        "https://api.modrinth.com/v2/project/wynnrot/version?include_changelog=false&game_versions="
                                + encodedVersion);

                HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                JsonArray versions = JsonParser.parseString(response.body()).getAsJsonArray();

                if (versions.isEmpty()) {
                    Wynnrot.info("No updates found");
                    return;
                }

                JsonObject version = versions.get(0).getAsJsonObject();

                latestVersion = version.get("version_number").getAsString();
                latestId = version.get("id").getAsString();

                SemanticVersion current = VersionParser.parseSemantic(Wynnrot.getVersion());

                updateAvailable = current.compareTo(Version.parse(latestVersion)) < 0;
            } catch (IOException | InterruptedException | VersionParsingException e) {
                Wynnrot.error("Failed to check for updates", e);
            }
        });
    }

    public static boolean trySendUpdatePrompt(UUID uuid, Component displayName) {
        if (!updateAvailable) return true;
        if (promptedUpdate) return true;
        if (!WORLD_UUID.equals(uuid)) return false;
        if (displayName == null) return false;

        Matcher matcher = WORLD_PATTERN.matcher(displayName.getString());

        if (!matcher.matches()) return false;

        promptedUpdate = true;

        if (latestVersion.isEmpty()) return true;

        MutableComponent updateMessage = Component.empty().withColor(Colours.CYAN);
        updateMessage.append(Component.translatable("wynnrot.updateAvailable1", latestVersion));
        updateMessage.append(Component.literal(" "));
        updateMessage.append(Component.translatable("wynnrot.updateAvailable2")
                .withStyle(Style.EMPTY
                        .withColor(Colours.DARK_CYAN)
                        .withUnderlined(true)
                        .withClickEvent(new ClickEvent.OpenUrl(
                                URI.create("https://modrinth.com/mod/wynnrot/version/" + latestId)))));

        McUtils.sendWynnrotMessage(updateMessage);

        return true;
    }
}
