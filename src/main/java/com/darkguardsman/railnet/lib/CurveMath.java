package com.darkguardsman.railnet.lib;

import com.darkguardsman.railnet.api.math.*;
import com.darkguardsman.railnet.data.rail.path.*;

import java.util.ArrayList;
import java.util.List;

public class CurveMath {
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
    public static List<IPos> getCurvePoints(IPosM start, double startAngle, IPosM end, double endAngle, int segmentCount) {
    	segmentCount = 10;
        //Create the array ready to hold all the points
        List<IPos> points = new ArrayList();

        if(segmentCount > 1) {
            //Calculate the curve influencing positions
            //Get the distance between the points and divide by 3 to give a smooth curve.
        	float divider = (float) (3f - (getAngleBetween2Angles(startAngle,endAngle)/Math.PI)*1.5);
        	float distance = (float)(start.horizontalDistance(end)/divider);

            //Get the influencing points, from simple tests 1/3rd the ditance to the next point at the incoming angle seems to work fine.
            Pos p1t = calculateInfluencingPoint(start, distance, startAngle);
            points.add(p1t); //TODO change how we pass this back to the rail
            Pos p2t = calculateInfluencingPoint(end, distance, endAngle);
            points.add(p2t); //TODO change how we pass this back to the rail
            //Once those points are removed, remember to change the rail as well or the data will be wrong
            
            //Add the sub points that will create the bend
            for (int i = 1; i <= segmentCount; i++) {
            	float t = i / (segmentCount + 1f);
            	float x = getCurveValue(start.x(), p1t.x(), end.x(), p2t.x(), t);
                float z = getCurveValue(start.z(), p1t.z(), end.z(), p2t.z(), t);
                float y = start.y()+((end.y()- start.y())*t); 
                points.add(new Pos(x, y, z));
                //TODO we could use a lambda expression to create an add directly to the host allowing more reusablity
            }

        }
        return points;
    }
    private static double getAngleBetween2Angles(double angle1, double angle2) {
    	double a = angle1 - angle2;
    	return ((a + 5*Math.PI) % (Math.PI*2));

    }
    public static Pos calculateInfluencingPoint(IPosM position, double distance, double angle) {
        Pos outPosition = new Pos(position);
        Pos vector = new Pos(distance * Math.sin(angle), 0, distance * Math.cos(angle));
        return outPosition.add(vector);
    }

    private static float getCurveValue(double p1, double pt1, double p2, double pt2, double t) {
        return (float) (Math.pow(1 - t, 3) * p1 + 3 * Math.pow(1 - t, 2) * t * pt1 + 3 * (1 - t) * Math.pow(t, 2) * pt2 + Math.pow(t, 3) * p2);
    }
}