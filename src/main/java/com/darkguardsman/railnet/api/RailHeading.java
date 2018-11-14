package com.darkguardsman.railnet.api;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public enum RailHeading {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTH_EAST(1, -1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(-1, 1);

    public final int offsetX;
    public final int offsetZ;

    RailHeading(int offsetX, int offsetZ) {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
    }
}
