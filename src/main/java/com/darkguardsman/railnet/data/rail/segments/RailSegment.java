package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.rail.*;

import java.util.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public abstract class RailSegment implements IRailSegment {

    protected final ArrayList<IRailJoint> joints = new ArrayList(2);
    protected final ArrayList<IRailPath> paths = new ArrayList(1);

    protected final Map<IRailMaterial, Integer> railCost = new HashMap();
    protected final Map<IRailMaterial, Integer> remainingRailCost = new HashMap();

    protected boolean arePathsInit = false;
    protected boolean isRailCostInit = false;


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
    public IRailPath getPath(IRailJoint from, IRailJoint to) {

        for (IRailPath path : getAllPaths()) {
            if (path.getStart() == from && path.getEnd() == to
                    || path.isTwoWay() &&
                    path.getStart() == to && path.getEnd() == from) {
                return path;
            }
        }
        return null;
    }

    @Override
    public Map<IRailMaterial, Integer> getRailMaterialCost() {
        if (!isRailCostInit) {
            isRailCostInit = true;
            initRailCost();
        }
        return railCost;
    }

    protected abstract void initRailCost();

    @Override
    public Map<IRailMaterial, Integer> getRemainingRailMaterialCost() {
        if (!isRailCostInit) {
            isRailCostInit = true;
            initRailCost();
        }
        return remainingRailCost;
    }

    @Override
    public int buildRail(IRailMaterial material, int amount, boolean doAction) {

        if (amount > 0) {
            if (!isRailCostInit) {
                isRailCostInit = true;
                initRailCost();
            }

            //check if we still need the material
            if (remainingRailCost.containsKey(material)) {

                //Get items needed
                final int itemsNeeded = remainingRailCost.get(material);

                if (amount >= itemsNeeded) {

                    //Remove entry as we cover all needed items
                    if (doAction) {
                        remainingRailCost.remove(material);
                    }

                    //Items left over
                    int remain = amount - itemsNeeded;

                    //Return amount used
                    return amount - remain;
                } else {

                    //Items still left after applying amount
                    int left = itemsNeeded - amount;

                    //Update entry
                    if (doAction) {
                        remainingRailCost.put(material, left);
                    }

                    //Return amount used
                    return amount;
                }
            }
        }
        return 0;
    }

    protected abstract void generatePaths();
}
