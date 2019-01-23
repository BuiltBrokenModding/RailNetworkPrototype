package com.darkguardsman.railnet.data.rail;

import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.*;
import com.darkguardsman.railnet.lib.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailJoint extends AbstractPos<RailJoint> implements IRailJoint<RailJoint, Pos> {

    public final IRailSegment rail;
    protected double angle;
    
    public RailJoint(IRailSegment rail, float x, float y, float z, double angle) {
        this.rail = rail;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = gradient(angle);
    }

    public RailJoint(IRailSegment rail, IPos pos, double angle) {
        this.rail = rail;
        this.x = pos.x();
        this.y = pos.y();
        this.z = pos.z();
        this.angle = gradient(angle);
    }

    @Override
    public IRailSegment getRail() {
        return rail;
    }

    @Override
    public RailJoint newCopyAtPosition(float x, float y, float z) {
        return new RailJoint(rail, x, y, z, this.angle);
    }

	@Override
	public double gradient() {
		return angle;
	}
	/**
	 * Just ensures our given angle is within the 180 degrees arc we care about as this is heading angle which is back and forth
	 */
	private double gradient(double angle) {
		angle = MathHelpers.wrap(angle,360);
		if(angle>=180) {
			angle = angle-180;
		}
		return angle;
	}
}
