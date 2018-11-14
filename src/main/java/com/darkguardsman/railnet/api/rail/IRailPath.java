package com.darkguardsman.railnet.api.rail;

import java.util.*;

/**
 * A path along a rail
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailPath {

    IRailJoint getStart();
    IRailJoint getEnd();
    boolean isTwoWay();
    /**
     * Gets all path points in the path
     *
     * @return
     */
    List<IRailPathPoint> getPathPoints();

    /**
     * Gets the next path point
     *
     * @param current - current point, used as an index
     * @param forward - true to go forward, false to go backwards
     * @return point, or null if hit the end
     */
    IRailPathPoint getNext(IRailPathPoint current, boolean forward);
}
