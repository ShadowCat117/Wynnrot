/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class McUtils {
  public static Minecraft mc() {
    return Minecraft.getInstance();
  }

  public static LocalPlayer player() {
    return mc().player;
  }
}
