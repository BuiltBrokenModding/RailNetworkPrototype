package com.darkguardsman.railnet.api.rail;

import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.material.IRailMaterialType;

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
     * Unique ID of the rail segment.
     * <p>
     * Used to track the rail for save/load
     * as well track render data stored seperately
     * from the rail.
     *
     * @return UUID
     */
    UUID getRailID();

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
     */
    List<IRailPath> getAllPaths();

    /**
     * Path from one join to the next
     *
     * @param from - start
     * @param to   - end
     * @return path
     */
    IRailPath getPath(IRailJoint from, IRailJoint to);

    /**
     * Start of the rail segment.
     * <p>
     * Does not define direction
     *
     * @return
     */
    IRailJoint start();

    /**
     * End of the rail segment.
     * <p>
     * Does not define direction
     *
     * @return
     */
    IRailJoint end();

    /**
     * Checks if the rail is completed.
     * <p>
     * Should only return true if all components
     * of the rail exist and are built. Damage
     * to the rail should not affect completion
     * status unless the damage prevents usage
     * of the rail.
     *
     * @return true if completed
     */
    default boolean isRailCompleted() {
        return getRemainingRailMaterialCost() == null;
    }

    /**
     * Gets the material cost of the rail
     * as a map of type to amount
     *
     * @return map of materials
     */
    Map<IRailMaterialType, Integer> getRailMaterialCost(); //TODO add with type, empty generics for placeholders

    /**
     * Gets the remaining materials needed to
     * complete the rail.
     *
     * @return
     */
    Map<IRailMaterialType, Integer> getRemainingRailMaterialCost(); //TODO add with type, empty generics for placeholders

    /**
     * Adds materials towards the completion of the
     * rail.
     * <p>
     * If doAction is true it will added
     * the material to the completion goal and
     * attempt to finish the rail.
     * <p>
     * If doAction is false it will simulate the
     * action allowing for checking the status
     * of the rail or if the action can be completed.
     *
     * @param material - material being added
     * @param amount   - amount of material added
     * @param doAction - true to build, false to simulate
     * @return amount of material used
     */
    int buildRail(IRailMaterial material, int amount, boolean doAction); //TODO replace with type, object is a placeholder
}
