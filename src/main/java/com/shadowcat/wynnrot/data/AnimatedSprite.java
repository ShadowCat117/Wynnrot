/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.data;

import com.shadowcat.wynnrot.Wynnrot;
import net.minecraft.resources.Identifier;

public record AnimatedSprite(String name, int frameCount, int width, int height, int fps) {
    public static final AnimatedSprite AD_BANNER = new AnimatedSprite("ad_banner", 199, 468, 60, 30);

    public Identifier getFrame(int frame) {
        int digits = String.valueOf(frameCount - 1).length();
        String filename = String.format("%s_%0" + digits + "d.png", name, frame);

        return Identifier.fromNamespaceAndPath(Wynnrot.MOD_ID, "textures/ad_banner/" + filename);
    }

    public int getFrame(long elapsedMillis) {
        return (int) ((elapsedMillis * fps / 1000.0) % frameCount);
    }
}
