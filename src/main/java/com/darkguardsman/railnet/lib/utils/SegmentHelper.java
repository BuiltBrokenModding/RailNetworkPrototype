package com.darkguardsman.railnet.lib.utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.lib.MathHelpers;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.lib.SnappedPos;
import com.darkguardsman.railnet.lib.SnappedPos.SNAP_VECTORS;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * Assists with the placement of segments, confirms the snap points, gets the
 * angles
 * 
 * @author joseph.bailey
 *
 */
public class SegmentHelper {

	

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
	public static RailSegment generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle, boolean forceStraight) {
		while (start.x() == end.x() && start.z() == end.z()) {
			end = new SnappedPos(end.addHVector(getAngleFromPoints(start, end).angle(), 1));
		}
		RailHeading endAngle = RailHeading.NORTH;
		// If we are forcing the end point to line be straight from the start point
		// (shift click in MC)
		if (forceStraight) {

			endAngle = RailHeading.fromAngle(startAngle.angle() + 180);

		} else {
			// Handle situation where N-S or E-W start and point is exactly 90 degrees
			// opposing (attempting to make 180)
			// Instead we will snap to a perfect 90 Degree segment instead.
			
			switch (startAngle) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				int diffAngle = MathHelpers.wrapTo360(startAngle.angle() - start.getAngle(end));
				
				if (diffAngle == 90 || diffAngle == 270) {
					if (start.z() == end.z()) {
						int lengthHint = (int) Math.ceil((start.x() - end.x()) / 4d) * 2;
						end = new SnappedPos(start.x() - lengthHint, start.y(),
								start.z() + ((startAngle == RailHeading.NORTH ? 1 : -1) * Math.abs(lengthHint)),
								SNAP_VECTORS.EW);
						return generateRail(start, end, startAngle, lengthHint > 0 ? RailHeading.EAST : RailHeading.WEST);
					} else if (start.x() == end.x()) {
						int lengthHint = (int) Math.floor((start.z() - end.z()) / 4d) * 2;
						end = new SnappedPos(start.x() + ((startAngle == RailHeading.EAST ? 1 : -1) * Math.abs(lengthHint)),
								start.y(), start.z() - lengthHint, SNAP_VECTORS.NS);
						return generateRail(start, end, startAngle, lengthHint > 0 ? RailHeading.NORTH : RailHeading.SOUTH);
					}
				}
				return generateRail(start, end, startAngle, getAngleFromPoints(end, start));
			}
		}
		return null;
	}

	public static RailSegment generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle) throws Exception {
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
		System.out.println((int)start.x() + "," + (int)start.z() +" to " +(int)end.x() + "," + (int)end.z() + " = " + getAngleFromPoints(start, end).angle());
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
	public static RailSegment generateRail(IPosM start, IPosM end, RailHeading startAngle, RailHeading endAngle) {
		if (!Arrays.asList(getValidAngles((int) start.x(), (int) start.z())).contains(startAngle)) {
			return null;
		}
		return new RailSegmentCurve(start, end, startAngle.angle(), endAngle.angle());
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
	private static RailHeading getAngleFromPoints(SnappedPos start, SnappedPos end) {
		double shortestDistance = start.hDistance(end) * 2;
		RailHeading out = RailHeading.NORTH;
		RailHeading[] validAngles = getValidAngles((int) start.x(), (int) start.z());
		System.out.println(validAngles[0]);
		for (int i = 0; i < validAngles.length; i++) {
			double testDistance = start.addHVector(validAngles[i].angle(), 0.25).hDistance(end);
			if (shortestDistance > testDistance) {
				shortestDistance = testDistance;
				out = validAngles[i];
			}
		}

		return out;
	}

	public static RailHeading[] getValidAngles(int x, int z) {
		int gridx = SnappedPos.gridPoint(x);
		int gridz = SnappedPos.gridPoint(z);
		if (gridx + gridz == 0) {
			return new RailHeading[] {};
		}
		if (gridx == 1 && SnappedPos.gridPoint(z) == 1) {
			return new RailHeading[] { RailHeading.NORTH_EAST, RailHeading.NORTH_WEST, RailHeading.SOUTH_EAST, RailHeading.SOUTH_WEST };
		} else if (gridx == 1) {
			return new RailHeading[] { RailHeading.NORTH, RailHeading.SOUTH };
		} else {
			return new RailHeading[] { RailHeading.EAST, RailHeading.WEST };
		}
	}
}
