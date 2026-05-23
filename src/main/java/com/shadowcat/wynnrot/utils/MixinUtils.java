/*
 * Copyright © 2026 ShadowCat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.shadowcat.wynnrot.utils;

public final class MixinUtils {
  private static final String WYNNCRAFT_SERVER_BRAND = "Wynn";

  public static boolean onWynncraft() {
    return McUtils.serverBrand().equals(WYNNCRAFT_SERVER_BRAND);
  }
}
