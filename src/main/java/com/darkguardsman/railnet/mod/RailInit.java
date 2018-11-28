package com.darkguardsman.railnet.mod;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.RailNetAPI;
import com.darkguardsman.railnet.api.material.IRailMaterialType;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public class RailInit { //TODO merge into mod main class

    public static void init() {
        RailNetAPI.MATERIAL_REGISTRY = new RailMaterialRegistry();
        RailConfig.railMetalMaterial = RailNetAPI.MATERIAL_REGISTRY.createOrGetMaterialType("rail.metal");
        RailConfig.railWoodMaterial = RailNetAPI.MATERIAL_REGISTRY.createOrGetMaterialType("rail.wood");
    }
}
