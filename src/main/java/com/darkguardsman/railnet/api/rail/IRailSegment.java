package com.darkguardsman.railnet.api.rail;

import java.util.*;

/**
 * A single section of rail in the network. This section can be as small as a few
 * meters or kilometers in length. It doesn't matter so long as the rail segment
 * contains a start and end point. As well creates a path for the train to follow.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailSegment {

    /**
     * Joints of the rail segment. Normally only
     * contains 2 values but could be more
     * for unique rail types that don't follow
     * normal rules of logic. Such teleportation rails.
     *
     * @return joints
     */
    List<IRailJoint> getJoints();

    /**
     * All possible paths through this rail segment.
     * Should only contain the rail segment itself.
     * Used purely to store motion paths for the carts
     * to follow while moving.
     *
     * @return paths
     * @throws Exception 
     */
    List<IRailPath> getAllPaths() throws Exception;

    /**
     * Path from one join to the next
     *
     * @param from - start
     * @param to   - end
     * @return path
     * @throws Exception 
     */
    IRailPath getPath(IRailJoint from, IRailJoint to) throws Exception;


}
