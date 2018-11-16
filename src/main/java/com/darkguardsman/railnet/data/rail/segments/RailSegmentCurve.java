package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.rail.IRailJoint;
import com.darkguardsman.railnet.data.rail.RailJoint;
import com.darkguardsman.railnet.data.rail.path.RailPath;
import com.darkguardsman.railnet.lib.CurveMath;

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

        List<IPos> points = CurveMath.getCurvePoints(start, Math.toRadians(startAngle), end, Math.toRadians(endAngle), cuts);
        path.newPoints(points);

        //Add end
        path.newPoint(end.x(), end.y(), end.z());

        //add path
        getAllPaths().add(path);
    }
}
