package com.darkguardsman.railnet.api;

import com.darkguardsman.railnet.lib.MathHelpers;
import com.darkguardsman.railnet.lib.SnappedPos;
import com.darkguardsman.railnet.lib.utils.SegmentHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum of support headings for rails
 *
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

    /**
     * Offset for the X axis
     */
    public final int offsetX;
    /**
     * Offset for the Z axis
     */
    public final int offsetZ;
    /**
     * Angle of the heading
     */
    public final int angle;

    //Possible headings for the value to proceed
    private List<RailHeading> possibleHeadings;

    static {
        for (RailHeading heading : values()) {
            heading.possibleHeadings = Collections.unmodifiableList(Arrays.asList(
                    heading.left().left(),
                    heading.left(),
                    heading,
                    heading.right(),
                    heading.right().right()
            ));
        }
    }

    RailHeading(int offsetX, int offsetZ, int angle) {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.angle = angle;
    }

    /**
     * List of possible headings that the current
     * heading can proceed in based on the supported
     * headings. Use this to define possible paths
     * and curves for the rails.
     * <p>
     * This list is immutable and can not be changed. If
     * you need to support more angles or directions
     * create logic in the snap system for more values.
     *
     * @return immutable list of headings
     */
    public List<RailHeading> getPossibleHeadings() {
        return possibleHeadings;
    }

    /**
     * Gets the next heading to the left. This
     * will increment by 45 on a compass rose.
     *
     * @return heading to the left
     */
    public RailHeading left() {
        return byIndexWrapped(ordinal() - 1);
    }

    /**
     * Gets the next heading to the right. This
     * will increment by 45 on a compass rose.
     *
     * @return heading to the right
     */
    public RailHeading right() {
        return byIndexWrapped(ordinal() + 1);
    }

    /**
     * Gets the heading at the index. Wraps the value
     * if it is below or above the range of the enum.
     *
     * @param index - index of the heading, or index to wrap
     * @return heading
     */
    public static RailHeading byIndexWrapped(int index) {
        return byIndex(MathHelpers.wrap(index, values().length - 1));
    }

    /**
     * Gets the heading by index.
     * <p>
     * Values that fall outside the range will default
     * to {@link #NORTH} and print an error to console
     * with trace.
     *
     * @param index - index of the heading
     * @return heading
     */
    public static RailHeading byIndex(int index) {
        if (index >= 0 && index < values().length) {
            return values()[index];
        }

        //TODO replace with Log4j2
        System.out.println("Error: index '" + index + "' could not be matched to a heading.");
        new RuntimeException("trace").printStackTrace();

        return NORTH;
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
        //TODO replace with Log4j2
        System.out.println("Error: angle '" + angleInput + "' could not be matched to a heading.");
        new RuntimeException("trace").printStackTrace();

        return RailHeading.NORTH;
    }
    public int angle() {
    	return angle;
    }

	public static RailHeading[] getPossibleHeadings(int x, int z) {
		int xs = SnappedPos.gridPoint(x);
		int zs = SnappedPos.gridPoint(z);
		if(xs == 1 && zs == 1) {
			return new RailHeading[] {NORTH_EAST,NORTH_WEST,SOUTH_EAST,SOUTH_WEST};
		} else if(xs== 1) {
			return new RailHeading[] {EAST,WEST};
		} else {
			return new RailHeading[] {NORTH,SOUTH};
		}
	}
}
