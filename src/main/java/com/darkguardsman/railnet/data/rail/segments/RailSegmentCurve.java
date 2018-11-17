package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.IRailJoint;
import com.darkguardsman.railnet.data.rail.RailJoint;
import com.darkguardsman.railnet.data.rail.path.RailPath;
import com.darkguardsman.railnet.lib.CurveMath;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailSegmentCurve extends RailSegment {
    public final IRailJoint start;
    public final IRailJoint end;
    public final float startAngle;
    public final float endAngle;

    public IPos influencePointA;
    public IPos influencePointB;

    public List<IPos> rail1 = new ArrayList<IPos>();
    public List<IPos> rail2 = new ArrayList<IPos>();
    
    public RailSegmentCurve(IPos start, IPos end, float startAngle, float endAngle) {

        this.start = new RailJoint(this, start);
        this.end = new RailJoint(this, end);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }


    @Override
    protected void generatePaths() {

        //Reset
        getAllPaths().clear();

        //Create
        RailPath path = new RailPath(start, end);

        //Add start
        path.newPoint(start.x(), start.y(), start.z());

        //Generate mid points
        int distance = (int) Math.ceil(start.distance(end));
        int cuts = distance < RailConfig.railPathPointDistanceDivide ? 1 : distance / RailConfig.railPathPointDistanceDivide;

        CurveMath curveMath = new CurveMath(start, startAngle, end, endAngle, 0.1);
        List<IPos> points = curveMath.getCurvePoints();
        
        influencePointA = curveMath.startInfluencePoint;
        influencePointB = curveMath.endInfluencePoint;

        path.newPoints(points);

        //Add end
        path.newPoint(end.x(), end.y(), end.z());

        //add path
        getAllPaths().add(path);
        IPosM Rail1Start = start.addHorizontalVector(startAngle,1d);
        IPosM Rail1End = end.addHorizontalVector(endAngle,-1d);        
        rail1 = new CurveMath(Rail1Start, startAngle, Rail1End,endAngle, 0.1).getCurvePoints();
        rail1.add(0, Rail1Start);
        rail1.add(Rail1End);
        
        IPosM Rail2Start = start.addHorizontalVector(startAngle,-1d);
        IPosM Rail2End = end.addHorizontalVector(endAngle,1d);        
        rail2 = new CurveMath(Rail2Start, startAngle, Rail2End,endAngle, 0.1).getCurvePoints();
        rail2.add(0, Rail2Start);
        rail2.add(Rail2End);
       
    }
}
