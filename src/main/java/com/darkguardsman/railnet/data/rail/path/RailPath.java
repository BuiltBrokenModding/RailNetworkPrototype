package com.darkguardsman.railnet.data.rail.path;

import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.rail.*;

import java.util.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailPath implements IRailPath {

    public final IRailJoint start;
    public final IRailJoint end;
    public boolean isTwoWay = true;

    private final List<IRailPathPoint> points = new ArrayList();

    public RailPath(IRailJoint start, IRailJoint end, boolean twoWay) {
        this(start, end);
        this.isTwoWay = twoWay;
    }

    public RailPath(IRailJoint start, IRailJoint end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public IRailJoint getStart() {
        return start;
    }

    @Override
    public IRailJoint getEnd() {
        return end;
    }

    @Override
    public boolean isTwoWay() {
        return isTwoWay;
    }

    @Override
    public List<IRailPathPoint> getPathPoints() {
        return points;
    }

    @Override
    public IRailPathPoint getNext(IRailPathPoint current, boolean forward) {
        int index = current.getIndex();
        if(forward)
        {
            if(index < getPathPoints().size() - 1)
            {
                return getPathPoints().get(index + 1);
            }
        }
        else if(index > 0)
        {
            return getPathPoints().get(index - 1);
        }
        return null;
    }

    public void newPoint(float x, float y, float z)
    {
        int index = getPathPoints().size();
        getPathPoints().add(new RailPathPoint(x, y, z, index));
    }

    public void newPoints(Collection<IPos> list)
    {
        list.forEach(pos -> newPoint(pos.x(), pos.y(), pos.z()));
    }
}
