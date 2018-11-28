package com.darkguardsman.railnet.api.material;

/**
 * Handles registering materials
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public interface IMaterialRegistry {

    /**
     * Called to register a new material to the given type
     *
     * @param mod       - id of the mod registering
     * @param id        - unqiue key to use for finding the material
     * @param type      - group of materials this material will be registered with
     * @param itemStack - item this material represents
     */
    IRailMaterial registerMaterial(String mod, String id, IRailMaterialType type, Object itemStack); //TODO replace mod & id with resource location
    //TODO implement event to fire to allow mods to register materials

    /**
     * Called to register a custom material
     *
     * @param material
     */
    void registerMaterial(IRailMaterial material);

    /**
     * Attempts to find the material type. If the
     * type is not found it will create a new
     * entry for use.
     *
     * @param id - unique key for the type
     * @return existing or new material type
     */
    IRailMaterialType createOrGetMaterialType(String id);

    /**
     * Gets a material by its registry name
     *
     * @param registryName - modID:materialID
     * @return material if found
     */
    IRailMaterial getMaterial(String registryName);

    /**
     * Gets the material type by its ID
     *
     * @param id - unique key for the material
     * @return type if found
     */
    IRailMaterialType getMaterialType(String id);
}
