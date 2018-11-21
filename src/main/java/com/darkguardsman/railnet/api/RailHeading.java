package com.darkguardsman.railnet.api;

import com.darkguardsman.railnet.lib.MathHelpers;
import com.darkguardsman.railnet.lib.utils.SegmentHelper;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public enum RailHeading {
    NORTH(0, -1, 0),
    NORTH_EAST(1, -1, 45),
    EAST(1, 0, 90),
    SOUTH_EAST(1, 1, 135),
    SOUTH(0, 1, 180),
    SOUTH_WEST(-1, 1, 225),
    WEST(-1, 0, 270),
    NORTH_WEST(-1, -1, 315);

    public final int offsetX;
    public final int offsetZ;
    public final int angle;

    RailHeading(int offsetX, int offsetZ, int angle) {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.angle = angle;
    }

    /**
     * Takes in an angle and returns a heading that closely matches
     * <p>
     * Works by wrapping the angle to 0 - 360 and then limiting to 45
     * degree segments. Ensuring that the angle snaps to the closest
     * value.
     *
     * @param angleInput - angle in degrees
     * @return heading
     */
    public static RailHeading fromAngle(double angleInput) {
        final double angle = MathHelpers.wrapTo360(angleInput);
        final int limitedAngle = ((int) Math.round(angle / 45)) * 45; //TODO test that this snaps to closest

        for (RailHeading heading : values()) {
            if (heading.angle == limitedAngle) {
                return heading;
            }
        }
        System.out.println("Error: angle '" + angleInput + "' could not be matched to a heading.");
        return RailHeading.NORTH;
    }
}
