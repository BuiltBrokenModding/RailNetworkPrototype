package com.darkguardsman.railnet.lib;

import com.darkguardsman.railnet.api.math.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class Pos extends AbstractPos<Pos> {

    public Pos(float x, float y, float z) {
        super(x, y, z);
    }

    public Pos(double x, double y, double z) {
       super(x, y, z);
    }

    public Pos(IPos position) {
        super(position);
    }

    @Override
    public Pos newCopyAtPosition(float x, float y, float z) {
        return newPos(x, y, z);
    }
}
