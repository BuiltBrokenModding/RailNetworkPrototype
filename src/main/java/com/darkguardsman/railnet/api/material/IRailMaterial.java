package com.darkguardsman.railnet.api.material;

import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/28/18.
 */
public interface IRailMaterial {

    /**
     * ItemStack used to represent this material
     *
     * @return
     */
    Object getItemStack(); //TODO replace with ItemStack later

    /**
     * Type of material
     *
     * @return
     */
    IRailMaterialType getMaterialType();

    /**
     * Unlocalized name used to display the material
     *
     * @return
     */
    default String getMaterialUnlocalizedName() {
        return "rail.material." + getRegistryName() + ".name";
    }

    /**
     * Unique ID of the material
     * <p>
     * Mod ID will be prefixed for usage in {@link #getRegistryName()}
     * which will be used to ID the material in most systems. This
     * exists as an internal ID for each mod's set of materials.
     *
     * @return string id used for the material
     */
    String getMaterialID();

    /**
     * Gets the mod ID for the material
     *
     * @return
     */
    String getModID();

    /**
     * Registry name used to track the material
     *
     * @return
     */
    default String getRegistryName() //TODO replace with ResourceLocation object
    {
        return getModID() + ":" + getMaterialID();
    }

}
