package com.darkguardsman.railnet.api.rail;

import java.util.*;

/** Data defining a network of rail segments connected by nodes.
 *
 * Each node defines a transition in the network. It is possible that a network can have only 1 node. In which the node
 * will loop back onto itself for any joint provided. Keep this in mind when generating paths.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailNetwork {

    /** All nodes in the network */
    List<IRailNode> getRailNodes();
}
