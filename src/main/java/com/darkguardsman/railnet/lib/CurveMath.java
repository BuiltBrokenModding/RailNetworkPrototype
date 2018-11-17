package com.darkguardsman.railnet.lib;

import com.darkguardsman.railnet.api.math.*;
import com.darkguardsman.railnet.data.rail.path.*;

import java.util.ArrayList;
import java.util.List;

public class CurveMath {
	
	private IPosM start;
	private IPosM end;
	private double startAngle;
	private double endAngle;
	
	public IPos startInfluencePoint = null;
	public IPos endInfluencePoint = null;
	
	private int segmentCount;
	
	private double distance;
	private double influenceDistance;
	
	
	public CurveMath (IPosM start, double startAngle, IPosM end, double endAngle, double approxSegmentSpacing) {		
		this.start = start;
		this.startAngle = startAngle;
		this.end = end;
		this.endAngle = endAngle;
		
		distance = start.horizontalDistance(end);
		
		double divider = (3d - ((getAngleBetween2Angles(startAngle,endAngle))/Math.PI)*1.5);
		
		if(start.collidesWithH(end,startAngle) && end.collidesWithH(start,endAngle)) {
			segmentCount = 1;
		} else {
			segmentCount = (int) Math.ceil((distance / approxSegmentSpacing) * (4d - divider));
		}
		distance = start.horizontalDistance(end);
		
    	
    	influenceDistance = start.horizontalDistance(end)/divider;
	}
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
    public List<IPos> getCurvePoints() {
        //Create the array ready to hold all the points
        List<IPos> points = new ArrayList();
        if(segmentCount > 1) {
            //Get the influencing points, from simple tests 1/3rd the ditance to the next point at the incoming angle seems to work fine.
            startInfluencePoint = calculateInfluencingPoint(start, influenceDistance, startAngle);            
            endInfluencePoint = calculateInfluencingPoint(end, influenceDistance, endAngle);
                       //Once those points are removed, remember to change the rail as well or the data will be wrong
            
            //Add the sub points that will create the bend
            for (int i = 1; i <= segmentCount; i++) {
            	float t = i / (segmentCount + 1f);
            	float x = getCurveValue(start.x(), startInfluencePoint.x(), end.x(), endInfluencePoint.x(), t);
                float z = getCurveValue(start.z(), startInfluencePoint.z(), end.z(), endInfluencePoint.z(), t);
                float y = start.y()+((end.y()- start.y())*t); 
                points.add(new Pos(x, y, z));
                //TODO we could use a lambda expression to create an add directly to the host allowing more reusablity
            }           

        }
        return points;
    }
    private double getAngleBetween2Angles(double angle1, double angle2) {
    	angle1 = positiveAngle(angle1);
    	angle2 = positiveAngle(angle2);
    	double a = angle1>angle2?angle1-angle2:angle2-angle1;
    	return a >= Math.PI? a-Math.PI:a;
    }
    private double positiveAngle(double angle) {
    	return ((angle + Math.PI*2) % (Math.PI*2));
    }
    public Pos calculateInfluencingPoint(IPosM position, double distance, double angle) {
        Pos outPosition = new Pos(position);
        Pos vector = new Pos(distance * Math.sin(angle), 0, distance * Math.cos(angle));
        return outPosition.add(vector);
        }

    private float getCurveValue(double p1, double pt1, double p2, double pt2, double t) {
        return (float) (Math.pow(1 - t, 3) * p1 + 3 * Math.pow(1 - t, 2) * t * pt1 + 3 * (1 - t) * Math.pow(t, 2) * pt2 + Math.pow(t, 3) * p2);
    }
}