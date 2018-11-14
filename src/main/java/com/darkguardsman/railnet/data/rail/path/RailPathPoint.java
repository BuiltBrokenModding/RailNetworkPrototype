package com.darkguardsman.railnet.data.rail.path;

import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.data.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailPathPoint extends Pos implements IRailPathPoint {

    private final int index;

    public RailPathPoint(float x, float y, float z, int index)
    {
        super(x, y, z);
        this.index = index;
    }

    @Override
    public int getIndex()
    {
        return index;
    }
}
