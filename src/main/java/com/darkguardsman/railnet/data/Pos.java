package com.darkguardsman.railnet.data;

        import com.darkguardsman.railnet.api.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class Pos implements IPos {
    protected float x, y, z;

    public Pos(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos(){}

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float z() {
        return z;
    }
}
