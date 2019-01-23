package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.IRailJoint;
import com.darkguardsman.railnet.api.rail.IRailPath;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.RailJoint;
import com.darkguardsman.railnet.data.rail.path.RailPath;
import com.darkguardsman.railnet.lib.CurveMath;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.sun.xml.internal.ws.policy.PolicyIntersector;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @see <a href=
 * "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 * for what you can and can't do with the code. Created by
 * Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailSegmentCurve extends RailSegment {
    public final IRailJoint start;
    public final IRailJoint end;
    
    public final int startAngle;
    
    public RailSegmentCurve(IPosM start, IPosM end, int startAngle, int endAngle) {
        super();
        this.start = new RailJoint(this, start,startAngle);
        this.end = new RailJoint(this, end,endAngle);

        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.generatePaths();
    }

    @Override
    protected void generatePaths() {

        // Reset
        getAllPaths().clear();

        // Create
        RailPath path = new RailPath(start, end);

        // Add start
        path.newPoint(start.x(), start.y(), start.z());

        try {
            CurveMath curveMath = new CurveMath(start.copy(), startAngle, end.copy(), endAngle, 0.5);
            List<IPos> points = curveMath.getCurvePoints();
            path.newPoints(points);
            
        } catch (Exception ex) {
            ex.printStackTrace(); //TODO generate log and data needed to ID reason for exception
        }
        // Add end
        path.newPoint(end.x(), end.y(), end.z());

        // add path
        getAllPaths().add(path);
        
        List<IRailPathPoint> points = path.getPathPoints();
        //Get the joints with gradients
        joints.clear();
        joints.add(start);
        for(int i = 1 ; i < points.size() - 1; i++) {
        	
        	IRailPathPoint prev = points.get(i-1);
        	IRailPathPoint here= points.get(i);
        	IRailPathPoint next= points.get(i+1);
        	
        	joints.add(new RailJoint(this, here,prev.getAngle(next)));
        }
        joints.add(end);
        
    }

    @Override
    protected double getRailDistance() { //TODO replace with better alg
        double distance = 0;
        if (!arePathsInit) {
            arePathsInit = true;
            generatePaths();
        }
        IRailPath path = getAllPaths().get(0);
        List<IRailPathPoint> points = path.getPathPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            IRailPathPoint a = points.get(i);
            IRailPathPoint b = points.get(i + 1);

            double dX = a.x() - b.x();
            double dY = a.y() - b.y();
            double dZ = a.z() - b.z();

            distance += Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        }
        return distance; //TODO cache
    }

    @Override
    public IRailJoint start() {
        return start;
    }

    @Override
    public IRailJoint end() {
        return end;
    }
}
