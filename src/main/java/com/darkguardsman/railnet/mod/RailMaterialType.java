package com.darkguardsman.railnet.mod;

import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.material.IRailMaterialType;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public class RailMaterialType implements IRailMaterialType {

    private final String id;
    private final List<IRailMaterial> materials = new ArrayList();

    public RailMaterialType(String id) {
        this.id = id;
    }

    @Override
    public String getMaterialTypeID() {
        return id;
    }

    @Override
    public List<IRailMaterial> getMaterials() {
        return materials;
    }
}
