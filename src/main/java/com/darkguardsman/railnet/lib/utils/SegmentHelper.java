package com.darkguardsman.railnet.lib.utils;

import java.awt.Point;
import java.awt.geom.Point2D;

import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.lib.Pos;

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

		public static ANGLE getAngle(int angle) throws Exception {
			switch (to360(angle)) {
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
			default:
				throw new Exception("Angle not found");
			}
		}

		public static int to360(int in) {
			return (in + 3600) % 360;
		}

		public int value() {
			return angle;
		}
	}

	/**
	 * 
	 */
	private static Point[] ALL_SNAP_VECTORS = new Point[] { new Point(1, 0), new Point(0, 1), new Point(-1, 0),
			new Point(0, -1), new Point(1, -1), new Point(1, 1), new Point(-1, 1), new Point(-1, -1) };
	private static Point[] HORIZONTAL_SNAP_VECTORS = new Point[] { new Point(1, 0), new Point(-1, 0) };
	private static Point[] VERTICAL_SNAP_VECTORS = new Point[] { new Point(0, 1), new Point(0, -1) };
	private static Point[] NE_SW_SNAP_VECTORS = new Point[] { new Point(1, 1), new Point(-1, -1) };
	private static Point[] NW_SW_SNAP_VECTORS = new Point[] { new Point(1, -1), new Point(-1, 1) };

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
	public static RailSegment generateRail(IPosM start, IPosM end, ANGLE startAngle, boolean forceStraight)
			throws Exception {
		Pos snappedStart = getClosestSnapPoint(start, ALL_SNAP_VECTORS);
		Pos snappedEnd = getClosestSnapPoint(end, ALL_SNAP_VECTORS);
		while (snappedStart.x() == snappedEnd.x() && snappedStart.z() == snappedEnd.z()) {
			end = end.addHVector(getAngleFromPoints(snappedStart,end).value(), 1);
			snappedEnd = getClosestSnapPoint(end, ALL_SNAP_VECTORS);
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
				if (snappedStart.z() == snappedEnd.z()) {
					int lengthHint = (int) (snappedEnd.x() - snappedStart.x()) / 2;

					end = new Pos(snappedStart.x() +lengthHint, snappedEnd.y(),
							snappedStart.z() +((startAngle == ANGLE.NORTH? 1 : -1) * Math.abs(lengthHint)));
					return generateRail(snappedStart, end, startAngle, lengthHint > 0 ? ANGLE.WEST : ANGLE.EAST);
				}
			}
		}
		return null;
	}
	
	public static RailSegment generateRail(IPosM start, IPosM end, ANGLE startAngle) throws Exception {
		return generateRail(start,end,startAngle,false);
	}
	/*
	 public static RailSegment generateRail(IPosM start, IPosM end, boolean forceStraight) {
	 

	}*/

	/**
	 * Gets the best detected segment between two points with no angle logging
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	/*public static RailSegment generateRail(IPosM start, IPosM end) {
		return generateRail(start, end, false);
	}*/

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
	 * @param start
	 *            Pos snapped to grid
	 * @param end
	 *            Pos snapped to grid
	 * @return
	 * @throws Exception
	 */
	private static ANGLE getAngleFromPoints(IPosM start, IPosM end) throws Exception {

		Pos startSnapped = getClosestSnapPoint(start, ALL_SNAP_VECTORS);
		Pos endSnapped = getClosestSnapPoint(end, ALL_SNAP_VECTORS);

		double shortestDistance = start.hDistance(end) * 2;
		ANGLE out = ANGLE.NORTH;

		ANGLE[] validAngles = getValidAngles((int) startSnapped.x(), (int) endSnapped.z());

		for (int i = 0; i < validAngles.length; i++) {
			double testDistance = startSnapped.addHVector(validAngles[i].value(), 1).hDistance(end);
			if (shortestDistance > start.addHVector(validAngles[i].value(), 1).hDistance(end)) {
				shortestDistance = testDistance;
				out = validAngles[i];
			}
		}

		return out;
	}

	/**
	 * Snaps a given position to the nearest valid position
	 * 
	 * @param pos
	 * @return
	 */
	public static Pos getClosestSnapPoint(IPosM pos, Point[] allowedVectors) {
		int x = Math.round(pos.x());
		int y = Math.round(pos.y());
		int z = Math.round(pos.z());
		// If snapped to the middle find the other next nearest snap point
		if (gridPoint(x) == 0 && gridPoint(z) == 0) {
			Point closestSnapPoint = null;
			double shortestDistance = 2d;
			for (int i = 0; i < allowedVectors.length; i++) {
				Point snapPoint = new Point(x + allowedVectors[i].x, z + allowedVectors[i].y);
				double testDistance = snapPoint.distance(pos.x(), pos.z());
				if (testDistance < shortestDistance) {
					shortestDistance = testDistance;
					closestSnapPoint = snapPoint;
				}
			}
			x = closestSnapPoint.x;
			z = closestSnapPoint.y;
		}
		return new Pos(x, y, z);
	}

	/**
	 * Gets the position of the dimension relative to the snapping grid
	 * 
	 * @param i
	 * @return
	 */
	private static int gridPoint(int i) {
		return Math.abs((i + 1) % 2);
	}

	public static ANGLE[] getValidAngles(int x, int z) throws Exception {
		int gridx = gridPoint(x);
		int gridz = gridPoint(z);
		if (gridx + gridz == 0) {
			throw new Exception("Center of rail cannot provide an angle, get the snap point first");
		}
		if (gridPoint(x) == 1 && gridPoint(z) == 1) {
			return new ANGLE[] { ANGLE.NORTHEAST, ANGLE.NORTHWEST, ANGLE.SOUTHEAST, ANGLE.SOUTHWEST };
		} else if (gridPoint(x) == 0) {
			return new ANGLE[] { ANGLE.NORTH, ANGLE.SOUTH };
		} else {
			return new ANGLE[] { ANGLE.EAST, ANGLE.WEST };
		}
	}
}
