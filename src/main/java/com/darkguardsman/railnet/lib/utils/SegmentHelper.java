package com.darkguardsman.railnet.lib.utils;

import java.awt.Point;
import java.awt.geom.Point2D;

import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.lib.SnappedPos;
import com.darkguardsman.railnet.lib.SnappedPos.SNAP_VECTORS;

/**
 * Assists with the placement of segments, confirms the snap points, gets the
 * angles
 * 
 * @author joseph.bailey
 *
 */
public class SegmentHelper {
	public enum ANGLE {
		NORTH(0), NORTHEAST(45), EAST(90), SOUTHEAST(135), SOUTH(180), SOUTHWEST(225), WEST(270), NORTHWEST(315);

		private final int angle;

		ANGLE(int angle) {
			this.angle = angle;
		}

		public static ANGLE getAngle(double angle) {
			int flooredAngle = (int) (Math.round(angle / 45) * 45);
			switch (to360(flooredAngle)) {
			case 0:
				return ANGLE.NORTH;
			case 45:
				return ANGLE.NORTHEAST;
			case 90:
				return ANGLE.EAST;
			case 135:
				return ANGLE.SOUTHEAST;
			case 180:
				return ANGLE.SOUTH;
			case 225:
				return ANGLE.SOUTHWEST;
			case 270:
				return ANGLE.WEST;
			case 315:
				return ANGLE.NORTHWEST;
			}
			return ANGLE.NORTH;
		}

		public static int to360(int in) {
			return (in + 3600) % 360;
		}

		public int value() {
			return angle;
		}
	}

	/**
	 * Get the appropriate rail segment
	 * 
	 * @param start
	 * @param end
	 * @param startAngle
	 * @param forceStraight
	 * @return
	 * @throws Exception
	 */
	public static RailSegment generateRail(SnappedPos start, SnappedPos end, ANGLE startAngle, boolean forceStraight) {
		while (start.x() == end.x() && start.z() == end.z()) {
			end = new SnappedPos(end.addHVector(getAngleFromPoints(start, end).value(), 1));
		}
		ANGLE endAngle = ANGLE.NORTH;
		// If we are forcing the end point to line be straight from the start point
		// (shift click in MC)
		if (forceStraight) {

			endAngle = ANGLE.getAngle(startAngle.value() + 180);

		} else {
			// Handle situation where N-S or E-W start and point is exactly 90 degrees
			// opposing (attempting to make 180)
			// Instead we will snap to a perfect 90 Degree segment instead.

			switch (startAngle) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				int diffAngle = ANGLE.to360(startAngle.value() - start.getAngle(end));
				System.out.println(diffAngle);
				if (diffAngle == 90 || diffAngle == 270) {
					if (start.z() == end.z()) {
						int lengthHint = (int) (start.x() - end.x()) / 2;
						end = new SnappedPos(start.x() - lengthHint, start.y(),
								start.z() + ((startAngle == ANGLE.NORTH ? 1 : -1) * Math.abs(lengthHint)),
								SNAP_VECTORS.EW);
						return generateRail(start, end, startAngle, lengthHint > 0 ? ANGLE.EAST : ANGLE.WEST);
					} else if (start.x() == end.x()) {
						int lengthHint = (int) (start.z() - end.z()) / 2;
						end = new SnappedPos(start.x()+ ((startAngle == ANGLE.EAST ? 1 : -1) * Math.abs(lengthHint)) , start.y(),
								start.z() - lengthHint,
								SNAP_VECTORS.NS);
						return generateRail(start, end, startAngle, lengthHint > 0 ? ANGLE.NORTH : ANGLE.SOUTH);
					}
				}
			}
		}
		return null;
	}

	public static RailSegment generateRail(SnappedPos start, SnappedPos end, ANGLE startAngle) throws Exception {
		return generateRail(start, end, startAngle, false);
	}

	/**
	 * Gets the best detected line between 2 points, can force the line to be
	 * straight Will be used by the rail planning tool to place the first segment of
	 * track so that the start angle is not forced.
	 * 
	 * @param start
	 * @param end
	 * @param forceStraight
	 * @return
	 * @throws Exception
	 */
	public static RailSegment generateRail(SnappedPos start, SnappedPos end, boolean forceStraight) throws Exception {
		return generateRail(start, end, getAngleFromPoints(start, end), forceStraight);
	}

	/**
	 * Gets the best detected segment between two points, no forced straight.
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static RailSegment generateRail(SnappedPos start, SnappedPos end) throws Exception {
		return generateRail(start, end, false);
	}

	/**
	 * Creates a rail from given points and angles
	 * 
	 * @param start
	 * @param end
	 * @param startAngle
	 * @param endAngle
	 * @return
	 */
	public static RailSegment generateRail(IPosM start, IPosM end, ANGLE startAngle, ANGLE endAngle) {
		return new RailSegmentCurve(start, end, startAngle.value(), endAngle.value());
	}

	/**
	 * Gets the ideal angle between 2 points for the start point, both positions
	 * should be already snapped to the Grid
	 * 
	 * @param start Pos snapped to grid
	 * @param end   Pos snapped to grid
	 * @return
	 * @throws Exception
	 */
	private static ANGLE getAngleFromPoints(SnappedPos start, SnappedPos end) {
		double shortestDistance = start.hDistance(end) * 2;
		ANGLE out = ANGLE.NORTH;
		ANGLE[] validAngles = getValidAngles((int) start.x(), (int) end.z());
		for (int i = 0; i < validAngles.length; i++) {
			double testDistance = start.addHVector(validAngles[i].value(), 1).hDistance(end);
			if (shortestDistance > testDistance) {
				shortestDistance = testDistance;
				out = validAngles[i];
			}
		}

		return out;
	}

	public static ANGLE[] getValidAngles(int x, int z) {
		int gridx = SnappedPos.gridPoint(x);
		int gridz = SnappedPos.gridPoint(z);
		if (gridx + gridz == 0) {
			return new ANGLE[] {};
		}
		if (gridx == 1 && SnappedPos.gridPoint(z) == 1) {
			return new ANGLE[] { ANGLE.NORTHEAST, ANGLE.NORTHWEST, ANGLE.SOUTHEAST, ANGLE.SOUTHWEST };
		} else if (gridx == gridz) {
			return new ANGLE[] { ANGLE.NORTH, ANGLE.SOUTH };
		} else {
			return new ANGLE[] { ANGLE.EAST, ANGLE.WEST };
		}
	}
}
