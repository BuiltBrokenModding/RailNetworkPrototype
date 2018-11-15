package com.darkguardsman.railnet.data.rail.path;

import com.darkguardsman.railnet.api.math.*;
import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.lib.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailPathPoint extends AbstractPos<RailPathPoint> implements IRailPathPoint<RailPathPoint> {

    public int index;

    public RailPathPoint(float x, float y, float z, int index)
    {
        super(x, y, z);
        this.index = index;
    }

    public RailPathPoint(IPos position, int index) {
        super(position);
        this.index = index;
    }

    @Override
    public int getIndex()
    {
        return index;
    }
}
