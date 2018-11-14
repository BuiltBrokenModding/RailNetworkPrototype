package com.darkguardsman.railnet.api.train;

/**
 * Information about a cart. Normally this would be a static
 * object that is shared between several carts. So should only
 * contain generic information. Anything else should be supplemented
 * through other methods.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public interface ICartData {
    float getWidth();
    float getHeight();
    float getLenght();
    float getMass();

    //TODO wheels, friction, attachment points, etc
}
