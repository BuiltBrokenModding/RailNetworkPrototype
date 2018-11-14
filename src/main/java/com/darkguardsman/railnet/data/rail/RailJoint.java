package com.darkguardsman.railnet.data.rail;

import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.data.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailJoint extends Pos implements IRailJoint {

    public final IRailSegment rail;

    public RailJoint(IRailSegment rail, float x, float y, float z) {
        this.rail = rail;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public IRailSegment getRail() {
        return rail;
    }
}
