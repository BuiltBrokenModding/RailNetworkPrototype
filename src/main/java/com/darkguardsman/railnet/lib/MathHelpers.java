package com.darkguardsman.railnet.lib;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/18.
 */
public class MathHelpers {
    public static int getX(int radius, int angle) {
        return (int) (radius * Math.cos(Math.toRadians(angle)));
    }

    public static int getY(int radius, int angle) {
        return (int) -(radius * Math.sin(Math.toRadians(angle)));
    }
}
