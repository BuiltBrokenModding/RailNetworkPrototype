package com.darkguardsman.railnet.api.material;

import java.util.List;

/**
 * Material type used to track a set of material items that can be used in place of each other
 * for building rail segments.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public interface IRailMaterialType {
    /**
     * Unique ID of the material type
     *
     * @return string id used for the material type
     */
    String getMaterialTypeID();

    /**
     * List of all materials part of this type
     *
     * @return
     */
    List<IRailMaterial> getMaterials();
}
