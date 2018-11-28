package com.darkguardsman.railnet;

import com.darkguardsman.railnet.api.material.IRailMaterialType;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailConfig {
    public static int railPathPointDistanceDivide = 4;

    public static IRailMaterialType railMetalMaterial; //TODO rename
    public static IRailMaterialType railWoodMaterial; //TODO rename

    public static double railMetalMaterialAmountPerMeter = 1;
    public static double railWoodMaterialAmountPerMeter = 1;
}
