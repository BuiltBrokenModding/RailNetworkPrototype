package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.*;
import com.darkguardsman.railnet.api.*;
import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.data.rail.*;
import com.darkguardsman.railnet.data.rail.path.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailSegmentLine extends RailSegment {
    public final IRailJoint start;
    public final IRailJoint end;
    public final RailHeading heading;
    public final int distance;

    public RailSegmentLine(RailHeading heading, float startX, float startY, float startZ, int distance)
    {
        this.heading = heading;
        this.distance = distance;
        start = new RailJoint(this, startX, startY, startZ);
        end = new RailJoint(this, startX + heading.offsetX * distance, startY, startZ + heading.offsetZ * distance);
    }


    @Override
    protected void generatePaths() {
        RailPath path = new RailPath(start, end);
        path.newPoint(start.x(), start.y(), start.z());

        int cuts = distance < RailConfig.railPathPointDistanceDivide ? 1 : distance / RailConfig.railPathPointDistanceDivide;

        float distancePerCut = distance / (cuts + 1f);
        for(int i = 0; i < cuts; i++)
        {
            path.newPoint(
                    start.x() + heading.offsetX * distancePerCut * i, 
                    start.y(),
                    start.z() + heading.offsetZ * distancePerCut * i);
        }

        path.newPoint(end.x(), end.y(), end.z());
    }
}
