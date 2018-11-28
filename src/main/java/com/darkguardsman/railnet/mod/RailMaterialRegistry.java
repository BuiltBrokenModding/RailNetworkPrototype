package com.darkguardsman.railnet.mod;

import com.darkguardsman.railnet.api.material.IMaterialRegistry;
import com.darkguardsman.railnet.api.material.IRailMaterial;
import com.darkguardsman.railnet.api.material.IRailMaterialType;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public class RailMaterialRegistry implements IMaterialRegistry {
    private final Map<String, IRailMaterial> idToMaterial = new HashMap();
    private final Map<String, IRailMaterialType> idToMaterialType = new HashMap();

    @Override
    public IRailMaterial registerMaterial(String mod, String id, IRailMaterialType type, Object itemStack) {
        IRailMaterial material = new RailMaterial(mod.toLowerCase(), id.toLowerCase(), type, itemStack);
        registerMaterial(material);
        return material;
    }

    @Override
    public void registerMaterial(IRailMaterial material) {
        idToMaterial.put(material.getMaterialID(), material);
        material.getMaterialType().getMaterials().add(material);
    }

    @Override
    public IRailMaterialType createOrGetMaterialType(String id) {
        IRailMaterialType type = getMaterialType(id);
        if (type == null) {
            type = new RailMaterialType(id.toLowerCase());
            idToMaterialType.put(id.toLowerCase(), type);
        }
        return type;
    }

    @Override
    public IRailMaterial getMaterial(String registryName) {
        return idToMaterial.get(registryName.toLowerCase());
    }

    @Override
    public IRailMaterialType getMaterialType(String id) {
        return idToMaterialType.get(id.toLowerCase());
    }
}
