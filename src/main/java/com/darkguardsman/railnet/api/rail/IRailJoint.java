package com.darkguardsman.railnet.api.rail;

import com.darkguardsman.railnet.api.math.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailJoint<N extends IRailJoint, P extends IPos> extends IPosM<N, P> {
    IRailSegment getRail();
    public double gradient();
}
