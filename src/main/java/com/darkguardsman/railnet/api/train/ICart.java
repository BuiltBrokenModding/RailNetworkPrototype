package com.darkguardsman.railnet.api.train;

import com.darkguardsman.railnet.api.math.*;

/**
 * A single cart in the train
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface ICart extends IPos {
    /**
     * Information about the cart. Shouldn't
     * be used direction if other methods
     * exist. As this data is generic for all
     * carts of the same type.
     *
     * @return data
     */
    ICartData getCartData();

    /**
     * Cart attached to the front of this cart
     * @return
     */
    ICart getFrontCart();

    /**
     * Cart attached to the rear of this cart
     * @return
     */
    ICart getRearCart();

    /**
     * Called to attach a cart to this cart. Not all
     * carts support attachment from both sides. Some
     * may not even support connections at all.
     *
     * @param other    - cart being connected
     * @param front    - side
     * @param doAction - true to apply changes, false to test logic
     * @return true if connection worked
     */
    boolean attachCart(ICart other, boolean front, boolean doAction);
}
