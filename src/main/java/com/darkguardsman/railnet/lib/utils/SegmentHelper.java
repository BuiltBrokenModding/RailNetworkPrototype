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
	 * Get the appropriate rail segment(s) Returns 2 segments if trying to create a semi circle
	 * 
	 * @param start
	 * @param end
	 * @param startAngle
	 * @param forceStraight
	 * @return
	 * @throws Exception
	 */
	public static RailSegment[] generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle,
			boolean forceStraight) {
		if (start.x() == end.x() && start.z() == end.z()) {
			return null;
		}
		startAngle = RailHeading.fromAngle(90);
		start.clearOrigin();
		RailHeading endAngle = RailHeading.NORTH;
		// If we are forcing the end point to line be straight from the start point
		// (shift click in MC)
		if (forceStraight) {
			endAngle = RailHeading.fromAngle(startAngle.angle + 180);
		} else {
			// Get the angle from the start point to the end point
			int angleToEnd = (int) Math.round(start.getAngle(end));
			System.out.println(String.format("Angle to End: %s", angleToEnd));
			// Get the angle between the heading and the angle to the end point
			int diffAngle = MathHelpers.wrapTo360(angleToEnd - startAngle.angle);
			System.out.println(String.format("Diff Angle: %s", diffAngle));
			// Is the end point on your left or your right?
			boolean left = (diffAngle > 180);
			System.out.println(String.format("Left: %s", left));
			// Is the end point 90 degrees or more either side of your heading?
			boolean behind = (diffAngle >= 90 && diffAngle <= 270);
			System.out.println(String.format("Behind: %s", behind));
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
				System.out.println(String.format("endDistance: %s", endDistance));
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
				System.out.println(String.format("startRightAngle: %s", startRightAngle));
				double startToEndAngle = start.getAngle(end.origin());
				
				double distanceToIntersectPoint;
				//If the right angle is inline then the distance is merely the distance to end
				double angleBetween = MathHelpers.wrapTo360(startRightAngle - startToEndAngle);
				if(angleBetween > 90) {
					angleBetween = 360 - angleBetween;
				}
				System.out.println(String.format("angleBetween: %s", angleBetween));
				if(angleBetween == 0) {
					distanceToIntersectPoint = start.distance(end);
				} else {
					// The angle we are looking for is the difference between the angle to the end
					
					// a/sin(a) = b/sin(b); a = b*sin(a)/sin(b))
					distanceToIntersectPoint = endDistance * Math.sin(Math.toRadians(90-angleBetween))
							/ (Math.sin(Math.toRadians(rightAngle)));
				}
				//Make the distance round up to a % 4 snap point to avoid issue with the quad circle snapping to the wrong place
				double snapAssist = 1;
				double snapFactor = 4;
				if(startAngle.angle == 45 || startAngle.angle == 135 || startAngle.angle == 225 || startAngle.angle == 315 ) {
					snapAssist = Math.sqrt(2);
				}
				distanceToIntersectPoint = Math.round(distanceToIntersectPoint/(snapFactor*snapAssist))*(snapFactor*snapAssist) +2*snapAssist;
				if(distanceToIntersectPoint<18) {
					distanceToIntersectPoint = 18;
				}
				System.out.println(String.format("distanceToIntersectPoint: %s", distanceToIntersectPoint));
				// Now we have the distance we just need to find the point 45 degrees in front
				// of us on the left/right that also intersects that line
				// so h = sqrt(2a^2);
				double distanceToQuarterCircleEnd = Math.sqrt(distanceToIntersectPoint/2 * distanceToIntersectPoint/2 * 2);
				System.out.println(String.format("distanceToQuarterCircleEnd: %s", distanceToQuarterCircleEnd));
				// Now we merely add this as a vector to our start point
				double startAngle45;
				if (left) {
					startAngle45 = startAngle.angle - 45;
				} else {
					startAngle45 = startAngle.angle + 45;
				}
				
				//======Create part 1=======			
				//The end angle is the opposite of our exit angle
				endAngle = RailHeading.fromAngle(startRightAngle-180);

				//Get the possible snap vectors to ensure we snap to the right place
				SNAP_VECTORS possibleVectors = SNAP_VECTORS.getFromAngle(endAngle.angle);
				
				//Find the end point by adding on 45 degrees with a length gotten from pythagoras;
				end = new SnappedPos(start.addHVector(startAngle45, distanceToQuarterCircleEnd),
						possibleVectors);	
				
				RailSegment part1 = generateRail(start, end, startAngle, endAngle);
				
				//======Create part 2=======
				//The start position of our second part will be the end point of our first
				start = end;
				//Get the new possible vectors
				possibleVectors = SNAP_VECTORS.getFromAngle(startAngle.angle);
				//Get the new adjustment vector
				if (left) {
					startAngle45 -= 90;
				} else {
					startAngle45 += 90;
				}
				end = new SnappedPos(part1.start().addHVector(startRightAngle, distanceToIntersectPoint),
						possibleVectors);	
				
				//Our end angle is now our original start angle
				endAngle = startAngle;
				//Our startAngle is now the original 90 degree from rail part 1
				startAngle = RailHeading.fromAngle(startRightAngle);
				
				RailSegment part2 = generateRail(start, end, startAngle , endAngle);
				//Return both segments
				return new RailSegment[] {part1,part2};

			} else {
				return new RailSegment[] {generateRail(start, end, startAngle, getAngleFromPoints(end, start))};
				
			}

		}
		return null;
	}

	public static RailSegment[] generateRail(SnappedPos start, SnappedPos end, RailHeading startAngle) throws Exception {
		start.clearOrigin();
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
	public static RailSegment[] generateRail(SnappedPos start, SnappedPos end, boolean forceStraight) throws Exception {
		start.clearOrigin();
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
	public static RailSegment[] generateRail(SnappedPos start, SnappedPos end) throws Exception {
		start.clearOrigin();
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
		//if (!Arrays.asList(start.possibleHeadings()).contains(startAngle)) {
		//	return null;
		//}
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
		
		boolean xPositive = end.origin().x() >= start.origin().x();
		boolean zPositive = end.origin().z() >= start.origin().z();
		

		
		RailHeading[] validAngles = start.possibleHeadings();
		
		for(int i = 0; i < validAngles.length;i++) {
			RailHeading validAngle = validAngles[i];
			//If an offset is not 0 then matters if the difference for that axis matches ours
			boolean offXMatters = validAngle.offsetX != 0;
			boolean offXPositive = validAngle.offsetX > 0;	
			boolean offZMatters = validAngle.offsetZ != 0;
			boolean offZPositive = validAngle.offsetZ > 0;			
			
			//If the x offset is important and does not match our offset side ignore this vector
			if(offXMatters && offXPositive != xPositive ) {
				continue;
			}
			//Same for z
			if(offZMatters && offZPositive != zPositive) {
				continue;
			}			

			return validAngle;
		}

		return null;
	}
}
