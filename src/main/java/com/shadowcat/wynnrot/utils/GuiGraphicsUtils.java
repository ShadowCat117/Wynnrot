/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import com.shadowcat.wynnrot.data.AnimatedSprite;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class GuiGraphicsUtils {
    public static void submitText(GuiGraphics graphics, Component component, float x, float y, int colour) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        graphics.drawString(McUtils.font(), component, 0, 0, colour);
        graphics.pose().popMatrix();
    }

    public static void submitTexture(
            GuiGraphics guiGraphics,
            Identifier identifier,
            int x,
            int y,
            int width,
            int height,
            int textureWidth,
            int textureHeight) {
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                identifier,
                x,
                y,
                0,
                0,
                width,
                height,
                textureWidth,
                textureHeight,
                textureWidth,
                textureHeight);
    }

    public static void submitAnimatedSprite(
            GuiGraphics guiGraphics, AnimatedSprite sprite, int frame, int x, int y, int width, int height) {
        submitTexture(guiGraphics, sprite.getFrame(frame), x, y, width, height, sprite.width(), sprite.height());
    }
}
