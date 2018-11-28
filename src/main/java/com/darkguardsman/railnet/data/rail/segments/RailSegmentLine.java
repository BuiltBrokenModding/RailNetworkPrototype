package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.*;
import com.darkguardsman.railnet.api.*;
import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.data.rail.*;
import com.darkguardsman.railnet.data.rail.path.*;

import java.util.UUID;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailSegmentLine extends RailSegment {
    public final IRailJoint start;

    public final IRailJoint end;
    public final RailHeading heading;
    public final int distance;

    public RailSegmentLine(RailHeading heading, float startX, float startY, float startZ, int distance) {
        super();
        this.heading = heading;
        this.distance = distance;
        start = new RailJoint(this, startX, startY, startZ);
        end = new RailJoint(this, startX + heading.offsetX * distance, startY, startZ + heading.offsetZ * distance);
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
        int cuts = distance < RailConfig.railPathPointDistanceDivide ? 1 : distance / RailConfig.railPathPointDistanceDivide;

        float distancePerCut = distance / (cuts + 1f);
        for (int i = 1; i <= cuts; i++) {
            path.newPoint(
                    start.x() + heading.offsetX * distancePerCut * i,
                    start.y(),
                    start.z() + heading.offsetZ * distancePerCut * i);
        }

        //Add end
        path.newPoint(end.x(), end.y(), end.z());

        //add path
        getAllPaths().add(path);
    }

    @Override
    protected double getRailDistance() {
        return start.distance(end); //TODO cache
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
