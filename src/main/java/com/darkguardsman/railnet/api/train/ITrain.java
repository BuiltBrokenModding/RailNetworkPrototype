package com.darkguardsman.railnet.api.train;

import java.util.*;

/**
 * Series of connected carts
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface ITrain {

    /**
     * All carts part of this train. Carts
     * include cargo, support, and engines.
     *
     * @return carts
     */
    List<ICart> getCarts();

    /**
     * Gets all engines that supply
     * energy to the train's movement
     *
     * @return engines
     */
    List<IEngine> getEngines();

    ICart getFirst();

    ICart getLast();
}
