package com.darkguardsman.railnet.data.rail.segments;

import com.darkguardsman.railnet.RailConfig;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.IRailJoint;
import com.darkguardsman.railnet.data.rail.RailJoint;
import com.darkguardsman.railnet.data.rail.path.RailPath;
import com.darkguardsman.railnet.lib.CurveMath;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @see <a href=
 *      "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 *      for what you can and can't do with the code. Created by
 *      Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class RailSegmentCurve extends RailSegment {
	public final IRailJoint start;
	public final IRailJoint end;
	public final int startAngle;
	public final int endAngle;

	public IPosM influencePointA;
	public IPosM influencePointB;

	public List<Pos> rail1 = new ArrayList<Pos>();
	public List<Pos> rail2 = new ArrayList<Pos>();

	public RailSegmentCurve(IPosM start, IPosM end, int startAngle, int endAngle) {		
		this.start = new RailJoint(this, start);
		this.end = new RailJoint(this, end);
		
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}

	@Override
	protected void generatePaths() {

		// Reset
		getAllPaths().clear();

		// Create
		RailPath path = new RailPath(start, end);

		// Add start
		path.newPoint(start.x(), start.y(), start.z());

		try {
		CurveMath curveMath = new CurveMath(start.copy(), startAngle, end.copy(), endAngle, 1);
		List<IPos> points = curveMath.getCurvePoints();

		influencePointA = curveMath.startInfluencePoint;
		influencePointB = curveMath.endInfluencePoint;

		path.newPoints(points);
		} catch (Exception ex) {
			
		}
		// Add end
		path.newPoint(end.x(), end.y(), end.z());

		// add path
		getAllPaths().add(path);		

	}

	@Override
	public IRailJoint start() {
		return start;
	}

	@Override
	public IRailJoint end() {
		return end;
	}
}
