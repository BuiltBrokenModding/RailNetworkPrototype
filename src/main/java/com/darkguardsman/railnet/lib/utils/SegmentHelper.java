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
	public static RailSegment generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle,
			boolean forceStraight) {
		if (start.x() == end.x() && start.z() == end.z()) {
			return null;
		}
		RailHeading endAngle = RailHeading.NORTH;
		// If we are forcing the end point to line be straight from the start point
		// (shift click in MC)
		if (forceStraight) {
			endAngle = RailHeading.fromAngle(startAngle.angle + 180);
		} else {
			// Get the angle from the start point to the end point
			int angleToEnd = (int) Math.round(start.getAngle(end));
			// Get the angle between the heading and the angle to the end point
			int diffAngle = MathHelpers.wrapTo360(angleToEnd - startAngle.angle);
			// Is the end point on your left or your right?
			boolean left = (diffAngle > 180);
			// Is the end point 90 degrees or more either side of your heading?
			boolean behind = (diffAngle >= 90 && diffAngle <= 270);
			// If behind then imagine we are aiming to build a quarter circle (90 degree
			// turn) where the end is in-line with a point running parallel to the original
			// end point (we will change the end point to suit)
			if (behind) {
				// If 180 behind then cannot possibly create a segment as no diameter
				if (diffAngle == 180) {
					return null;
				}
				// Use trigonometry to get the distance
				// We know the line parallel to us intersecting a line 90 degrees to the given
				// side will at 90 degrees so angle a = 90 (PI/2)
				int rightAngle = 90;
				// We can find the distance to the end point
				double endDistance = start.hDistance(end);
				// We can get the angle between the 90 degree line and the line to the end
				// point;
				int startRightAngle;
				// If the point is to our left then take 90 degrees off the currentAngle,
				// otherwise add it
				if (left) {
					startRightAngle = startAngle.angle - 90;
				} else {
					startRightAngle = startAngle.angle + 90;
				}
				double distanceToIntersectPoint;
				//If the right angle is inline then the distance is merely the distance to end
				double angleBetween = MathHelpers.wrapTo360(startRightAngle - start.getAngle(end));
				if(angleBetween == 0) {
					distanceToIntersectPoint = start.distance(end);
				} else {
					// The angle we are looking for is the difference between the angle to the end
					
					// a/sin(a) = b/sin(b); a = b/(sin(a)*sin(b))
					distanceToIntersectPoint = endDistance
							/ (Math.sin(Math.toRadians(angleBetween)) * Math.sin(rightAngle));
				}				
				// Now we have the distance we just need to find the point 45 degrees in front
				// of us on the left/right that also intersects that line
				// so h = sqrt(2a^2);
				double distanceToQuarterCircleEnd = Math.sqrt(distanceToIntersectPoint * distanceToIntersectPoint * 2);
				// Now we merely add this as a vector to our start point
				double startAngle45;
				if (left) {
					startAngle45 = startAngle.angle - 45;
				} else {
					startAngle45 = startAngle.angle + 45;
				}
				System.out.println(String.format("Start: %d,%d,%d", (int)start.x(),(int)start.y(),(int)start.z()));
				System.out.println(String.format("End: %d,%d,%d", (int)end.x(),(int)end.y(),(int)end.z()));
				System.out.println(String.format("Start Angle: %s", startAngle.angle));
				System.out.println(String.format("Angle to End: %s", angleToEnd));
				System.out.println(String.format("Diff Angle: %s", diffAngle));
				end = new SnappedPos(start.addHVector(startAngle45, distanceToQuarterCircleEnd),
						SNAP_VECTORS.getFromAngle(startRightAngle));
				return generateRail(start, end, startAngle, RailHeading.fromAngle(startRightAngle));

			} else {
				//return generateRail(start, end, startAngle, getAngleFromPoints(end, start));
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
	public static RailSegment generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle,
			RailHeading endAngle) {
		if (!Arrays.asList(start.possibleHeadings()).contains(startAngle)) {
			return null;
		}
		return new RailSegmentCurve(start, end, startAngle.angle, endAngle.angle);
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
		RailHeading[] validAngles = start.possibleHeadings();
		for (int i = 0; i < validAngles.length; i++) {
			double testDistance = start.addHVector(validAngles[i].angle, 0.25).hDistance(end);
			if (shortestDistance > testDistance) {
				shortestDistance = testDistance;
				out = validAngles[i];
			}
		}

		return out;
	}
}
