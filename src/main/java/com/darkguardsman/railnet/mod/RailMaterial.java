package com.darkguardsman.railnet.mod;

import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.material.IRailMaterialType;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public class RailMaterial implements IRailMaterial {

    private final String id;
    private final String mod;
    private final IRailMaterialType type;
    private final Object item;

    public RailMaterial(String id, String mod, IRailMaterialType type, Object item)
    {
        this.id = id;
        this.mod = mod;
        this.type = type;
        this.item = item;
    }

    @Override
    public Object getItemStack() {
        return item;
    }

    @Override
    public IRailMaterialType getMaterialType() {
        return type;
    }

    @Override
    public String getMaterialID() {
        return id;
    }

    @Override
    public String getModID() {
        return mod;
    }
}
