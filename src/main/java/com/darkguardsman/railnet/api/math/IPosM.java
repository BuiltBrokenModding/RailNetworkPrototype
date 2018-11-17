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

	default double horizontalDistance(IPos end) {
		return Math.sqrt(Math.pow(end.x() - x(), 2) + Math.pow(end.z() - z(), 2));
	}
	
	/**
	 * Distance to the point
	 * @param end
	 * @return
	 */
	default double distance(IPos end) {
		return Math.sqrt(distanceSq(end));
	}
	/**
	 * Distance to the point
	 * @param end
	 * @return
	 */
	default IPosM addHorizontalVector(float f, Double distance) {
		return (IPosM)add(new Pos(Math.cos(-f)*distance,0,Math.sin(-f)*distance));
		
	}

	/**
     * Distance to the point squared
     * D * D
	 * @param end
	 * @return
	 */
	default double distanceSq(IPos end) {
		float x = x() - end.x(), y = y() - end.y(), z = z() - end.z();
		return x * x + y * y + z * z;
	}

	/**
	 * Adds the position creating a new pos
	 * @param pos
	 * @return new position
	 */
	default P add(IPos pos) {
		return newPos(x() + pos.x(), y() + pos.y(), z() + pos.z());
	}

	/**
	 * Adds the position creating a new pos
	 * @param pos
	 * @return new position
	 */
	default P sub(IPos pos) {
		return newPos(x() - pos.x(), y() - pos.y(), z() - pos.z());
	}

	/**
	 * Adds the position creating a new pos
	 * @param pos
	 * @return new position
	 */
	default P multiply(IPos pos) {
		return newPos(x() * pos.x(), y() * pos.y(), z() * pos.z());
	}

	/**
	 * Adds the position creating a new pos
	 * @param pos
	 * @return new position
	 */
	default P divide(IPos pos) {
		return newPos(x() / pos.x(), y() / pos.y(), z() / pos.z());
	}
	default boolean collidesWithH(IPos pos, double angle) {
		 double xDiff = x() - pos.x();
	        double zDiff = z() - pos.z();
	        return Math.atan2(zDiff, xDiff) == angle;
	}
	
}
