package com.darkguardsman.railnet.lib;

        import com.darkguardsman.railnet.api.math.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public abstract class AbstractPos<P extends AbstractPos> implements IPosM<P, Pos> {
    protected float x, y, z;

    public AbstractPos(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AbstractPos(){}

    public AbstractPos(IPos position) {
        this.x = position.x();
        this.y = position.y();
        this.z = position.z();
    }

    public AbstractPos(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

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

    @Override
    public Pos newPos(float x, float y, float z)
    {
        return new Pos(x, y, z);
    }
}
