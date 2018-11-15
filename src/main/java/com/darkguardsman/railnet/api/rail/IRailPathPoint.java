package com.darkguardsman.railnet.api.rail;

import com.darkguardsman.railnet.api.math.*;

/** Position in 3D space
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface IRailPathPoint<R extends IRailPathPoint> extends IPos<R>
{
    /**
     * Index of the point in the path
     * @return
     */
    int getIndex();
}
