package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.api.rail.*;

import java.util.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public abstract class RailSegment implements IRailSegment {

    protected ArrayList<IRailJoint> joints = new ArrayList(2);
    protected ArrayList<IRailPath> paths = new ArrayList(1);
    

    protected boolean arePathsInit = false;


    @Override
    public List<IRailJoint> getJoints() {
        return joints;
    }

    
    @Override
    public List<IRailPath> getAllPaths() {
        if (!arePathsInit) {
            arePathsInit = true;
            generatePaths();
        }
        return paths;
    }


    @Override
    public IRailPath getPath(IRailJoint from, IRailJoint to){

        for (IRailPath path : getAllPaths()) {
            if (path.getStart() == from && path.getEnd() == to
                    || path.isTwoWay() &&
                    path.getStart() == to && path.getEnd() == from) {
                return path;
            }
        }
        return null;
    }

    protected abstract void generatePaths();
}
