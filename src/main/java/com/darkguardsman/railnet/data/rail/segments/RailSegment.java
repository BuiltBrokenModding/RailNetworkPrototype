package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.material.IRailMaterialType;
import com.darkguardsman.railnet.api.rail.*;

import java.util.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public abstract class RailSegment implements IRailSegment {

    protected final ArrayList<IRailJoint> joints = new ArrayList(2);
    protected final ArrayList<IRailPath> paths = new ArrayList(1);

    protected final Map<IRailMaterialType, Integer> railCost = new HashMap();
    protected final Map<IRailMaterialType, Integer> remainingRailCost = new HashMap();
    protected final Map<IRailMaterial, Integer> railMaterialsUsed = new HashMap();

    protected boolean arePathsInit = false;
    protected boolean isRailCostInit = false;

    protected UUID uniqueID;

    public RailSegment() {
        uniqueID = UUID.randomUUID();
    }

    @Override
    public UUID getRailID() {
        return uniqueID;
    }

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
    public Map<IRailMaterialType, Integer> getRailMaterialCost() {
        if (!isRailCostInit) {
            isRailCostInit = true;
            initRailCost();
        }
        return railCost;
    }

    protected void initRailCost() {
        final double distance = getRailDistance();

        int metalNeeded = (int) Math.floor(getNumberOfRails() * distance * RailConfig.railMetalMaterialAmountPerMeter);
        railCost.put(RailConfig.railMetalMaterial, metalNeeded);

        int woodNeeded = (int) Math.floor(getNumberOfRails() * distance * RailConfig.railWoodMaterialAmountPerMeter);
        railCost.put(RailConfig.railWoodMaterial, woodNeeded);

        remainingRailCost.putAll(railCost); //TODO when save/load is added change this to match loaded data
    }

    /**
     * Number of rails the track contains
     *
     * @return
     */
    protected int getNumberOfRails() {
        return 2;
    }

    /**
     * Width of the track
     *
     * @return
     */
    protected float getRailWidth() {
        return 1.5f; //US & EU standard 1,435 mm, rounded up for simplicity
    }

    @Override
    public Map<IRailMaterialType, Integer> getRemainingRailMaterialCost() {
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
            if (remainingRailCost.containsKey(material.getMaterialType())) {

                //Get items needed
                final int itemsNeeded = remainingRailCost.get(material.getMaterialType());

                if (amount >= itemsNeeded) {

                    //Remove entry as we cover all needed items
                    if (doAction) {
                        remainingRailCost.remove(material.getMaterialType());
                    }

                    //Items left over
                    int remain = amount - itemsNeeded;
                    int used = amount - remain;

                    if (doAction) {
                        trackUsedMaterials(material, used);
                    }

                    //Return amount used
                    return used;
                } else {

                    //Items still left after applying amount
                    int left = itemsNeeded - amount;

                    //Update entry
                    if (doAction) {
                        remainingRailCost.put(material.getMaterialType(), left);
                        trackUsedMaterials(material, amount);
                    }

                    //Return amount used
                    return amount;
                }
            }
        }
        return 0;
    }

    /**
     * Internal method to track materials applied when building
     * the track
     *
     * @param material - exact material
     * @param used     - number of material used
     */
    protected void trackUsedMaterials(IRailMaterial material, int used) {
        if (!railMaterialsUsed.containsKey(material)) {
            railMaterialsUsed.put(material, used);
        } else {
            railMaterialsUsed.put(material, used + railMaterialsUsed.get(material));
        }
    }

    /**
     * Called to generate the path carts will
     * follow when navigating the rail
     */
    protected abstract void generatePaths();

    /**
     * Called to get the distance traveled by
     * the track
     *
     * @return
     */
    protected abstract double getRailDistance();
}
