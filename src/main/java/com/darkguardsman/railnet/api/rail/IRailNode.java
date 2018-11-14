package com.darkguardsman.railnet.api.rail;

import java.util.*;

/** A single transition point in the network. These nodes are only created
 * under a few conditions. The first being a junction or change in the path
 * that would results in more than 1 path. The second being if only a single
 * loop of rail exists in which a node will be generated to keep sanity.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailNode {
    /**
     * Gets joints that make up this node.
     * From the joints the actual rail segments
     * can be accessed.
     * @return
     */
    List<IRailJoint> getJoints();
}
