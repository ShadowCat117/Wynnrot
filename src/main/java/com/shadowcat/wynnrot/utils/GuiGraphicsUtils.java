/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class GuiGraphicsUtils {
    public static void submitText(GuiGraphics graphics, Component component, float x, float y, int colour) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        graphics.drawString(McUtils.font(), component, 0, 0, colour);
        graphics.pose().popMatrix();
    }
}
