package com.darkguardsman.railnet.data.rail;

import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.lib.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailJoint extends AbstractPos<RailJoint> implements IRailJoint<RailJoint, Pos> {

    public final IRailSegment rail;

    public RailJoint(IRailSegment rail, float x, float y, float z) {
        this.rail = rail;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RailJoint(IRailSegment rail, IPos pos) {
        this.rail = rail;
        this.x = pos.x();
        this.y = pos.y();
        this.z = pos.z();
    }

    @Override
    public IRailSegment getRail() {
        return rail;
    }

    @Override
    public RailJoint newCopyAtPosition(float x, float y, float z) {
        return new RailJoint(rail, x, y, z);
    }
}
