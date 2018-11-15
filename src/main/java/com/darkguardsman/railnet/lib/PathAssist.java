package com.darkguardsman.railnet.lib;

import com.darkguardsman.railnet.api.math.*;
import com.darkguardsman.railnet.data.rail.path.*;

public class PathAssist {
    /**
     * Creates curves between 2 points with smoothing based on the input location and direction
     *
     * @param start        Starting point
     * @param startAngle   Starting points entering direction (radians)
     * @param end          Ending point
     * @param endAngle     Ending points leaving direction (radians)
     * @param segmentCount The number of segments to divide this rail into, minimum is 1, 2 would mean 3 points (beginning, middle, end) (more is smoother for curves)
     * @return
     * @throws Exception
     */
    public RailPathPoint[] GetCurvePoints(IPosM start, double startAngle, IPosM end, double endAngle, int segmentCount) throws Exception {
        //Ensure at least 1 segment is made
        if (segmentCount < 1) {
            throw new Exception("Must contain at least 1 segment");
        }
        //Create the array ready to hold all the points
        RailPathPoint[] points = new RailPathPoint[segmentCount];
        //Set the starting and end points relevantly
        points[0] = new RailPathPoint(start, 0);
        points[segmentCount] = new RailPathPoint(end, segmentCount);
        //If the segment count is 1 then we only need to return the beginning and the end points
        if (segmentCount == 1) {
            return points;
        }
        //Calculate the curve influencing positions
        //Get the distance between the points and divide by 3 to give a smooth curve.
        double distance = start.distance(end) / 3;

        //Get the influencing points, from simple tests 1/3rd the ditance to the next point at the incoming angle seems to work fine.
        Pos p1t = calculateInfluencingPoint(start, distance, startAngle);
        Pos p2t = calculateInfluencingPoint(start, distance, startAngle);
        //Add the sub points that will create the bend
        for (int i = 1; i <= segmentCount; i++) {
            float t = i / (segmentCount + 1);
            float x = GetCurveValue(start.x(), p1t.x(), end.x(), p2t.x(), t);
            float z = GetCurveValue(start.z(), p1t.z(), end.z(), p2t.z(), t);
            points[i] = new RailPathPoint(x, 0, z, i);
        }
        return points;
    }

    public Pos calculateInfluencingPoint(IPosM position, double distance, double angle) {
        Pos outPosition = new Pos(position);
        Pos vector = new Pos(distance * Math.sin(angle), 0, distance * Math.cos(angle));
        return outPosition.add(vector);
    }

    private float GetCurveValue(double p1, double pt1, double p2, double pt2, double t) {
        return (float) (Math.pow(1 - t, 3) * p1 + 3 * Math.pow(1 - t, 2) * t * pt1 + 3 * (1 - t) * Math.pow(t, 2) * pt2 + Math.pow(t, 3) * p2);
    }
}