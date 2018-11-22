package com.darkguardsman.railnet.api.math;

import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;

/**
 * Version of {@link IPos} that contains math functions
 *
 * @see <a href=
 *      "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 *      for what you can and can't do with the code. Created by
 *      Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public interface IPosM<N extends IPosM, P extends IPos> extends IPos<N> {

	/**
	 * Creates a copy of the pos
	 *
	 * @return
	 */
	default N copy() {
		return newCopyAtPosition(x(), y(), z());
	}

	/**
     * Creates a new position of the same type
     * at the position given
	 *
	 * @param x - pos
	 * @param y - pos
	 * @param z - pos
	 * @return new position
	 */
	N newCopyAtPosition(float x, float y, float z);

	/**
     * Creates a new position of the pos type
     * at the position given
	 *
	 * @param x - pos
	 * @param y - pos
	 * @param z - pos
	 * @return new position
	 */
	P newPos(float x, float y, float z);

	/**
	 * Horizontal distance between 2 points
	 * @param end
	 * @return
	 */
	//TODO rename to distanceFlat
	default double hDistance(IPos end) {
		return Math.sqrt(Math.pow(end.x() - x(), 2) + Math.pow(end.z() - z(), 2));
	}

	/**
	 * Add a horizontal vector to a position based on an angle (degrees) and a
	 * distance.
	 * 
	 * @param angleDegree - angle of the vector in degrees
	 * @param distance - scale of the vector
	 * @return
	 */
	//TODO rename to addAngle
	default IPosM addHVector(double angleDegree, double distance) {
		final double angleRadian = Math.toRadians(angleDegree);
		return (IPosM) add(new Pos(Math.cos(angleRadian) * distance, 0, Math.sin(angleRadian) * distance));
	}

	/**
	 * Adds the position creating a new pos
	 * 
	 * @param pos
	 * @return new position
	 */
	default P add(IPos pos) {
		return newPos(x() + pos.x(), y() + pos.y(), z() + pos.z());
	}

	//TODO change to return double
	default double getAngle(IPos b) {
		double angle = Math.toDegrees(Math.atan2(b.z() - z(), b.x() - x())) - 90;

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}

	/**
	 * Distance to the point
	 * 
	 * @param end
	 * @return
	 */
	default double distance(IPos end) {
		return Math.sqrt(distanceSq(end));
	}

	/**
	 * Distance to the point squared D * D
	 * 
	 * @param end
	 * @return
	 */
	default double distanceSq(IPos end) {
		float x = x() - end.x(), y = y() - end.y(), z = z() - end.z();
		return x * x + y * y + z * z;
	}

	//TODO rename and document
	default boolean collidesWithH(IPos pos, double angle) {
		double xDiff = x() - pos.x();
		double zDiff = z() - pos.z();
		return Math.atan2(zDiff, xDiff) == angle;
	}

}
