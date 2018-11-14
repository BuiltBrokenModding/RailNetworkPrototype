package com.darkguardsman.railnet.api.path;

import com.darkguardsman.railnet.api.train.*;

/**
 * Path that a train is following
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface ITrainPath {

    /**
     * Current train using the path
     *
     * @return train
     */
    ITrain getTrain();
}
