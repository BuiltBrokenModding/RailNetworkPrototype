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
	private static Point[] snapVectors = 
			new Point[] { new Point(1, 0), new Point(0, 1), new Point(-1, 0),
			new Point(0, -1), new Point(1, -1), new Point(1, 1), new Point(-1, 1), new Point(-1, -1),

	};
	public List<Pos> rail1 = new ArrayList<Pos>();
	public List<Pos> rail2 = new ArrayList<Pos>();

	public RailSegmentCurve(IPosM start, IPosM end) {
		this(start, end, 1000);		
	}
	public RailSegmentCurve(IPosM start, IPosM end, int startAngle) {
		this(start, end, startAngle,getAngleJustFromPoints(end, getSnapPoint(start),false));
	}
	public RailSegmentCurve(IPosM start, IPosM end, int startAngle, int endAngle) {		
		if (startAngle == 1000) {
			startAngle = getAngleJustFromPoints(getSnapPoint(start), end,true);
		}
		this.start = new RailJoint(this, getSnapPoint(start));
		this.end = new RailJoint(this, getSnapPoint(end));
		this.startAngle = startAngle;
		this.endAngle = endAngle;

	}


	/**
	 * TODO put this in universal
	 * 
	 * @param pos
	 * @return
	 */
	private static Pos getSnapPoint(IPosM pos) {
		int x = Math.round(pos.x());
		int y = Math.round(pos.y());
		int z = Math.round(pos.z());

		if (gridPoint(x) == 0 && gridPoint(z) == 0) {
			Point closestSnapPoint = null;
			double shortestDistance = 2d;
			Point2D origin = new Point2D.Double(pos.x(),pos.y());
			
			for(int i = 0; i < snapVectors.length;i++){
				Point testVector = snapVectors[i];
				Point snapPoint = new Point(x +testVector.x ,z +testVector.y); 
				double testDistance = snapPoint.distance(pos.x(), pos.z());
				if(testDistance<shortestDistance) {
					shortestDistance = testDistance;
					closestSnapPoint = snapPoint;
				}				
			}
			x = closestSnapPoint.x;
			z = closestSnapPoint.y;
		}
		return new Pos(x,y,z);
	}

	private static int gridPoint (int i) {
		return Math.abs(i % 2);
	}
	private static int getAngleFromPointAndPlayerFacing(IPos start, int angle) {
		return 0;
	}

	private static int getAngleJustFromPoints(IPosM start, IPosM end,boolean isStart) {
		Pos startSnapped =  getSnapPoint(start);
		Pos endSnapped =  getSnapPoint(end);
		
		if(gridPoint((int)startSnapped.x()) == 1 && gridPoint((int)startSnapped.z()) == 1) {
			//Angle
			int xdiff = (int)(startSnapped.x() - endSnapped.x());
			int zdiff = (int)(startSnapped.z() - endSnapped.z());
			if(xdiff>0) {
				if(zdiff>0) {
					return 225;
				}else {
					return 315;
				}
			} else {
				if(zdiff>0) {
					return 135;
				}else {
					return 45;
				}
			}
		} else if(gridPoint((int)startSnapped.x()) == 0) {
			//N - S
			/**if(startSnapped.z() == endSnapped.z()) {
				//Same horizontal line, wants to do a semi
				if((isStart && startSnapped.z() < end.z())|| (!isStart && endSnapped.z() < start.z())) {
					return 0;
				} else {
					return 180;
				}
					
			} else {			*/	
				if(end.z() - startSnapped.z() > 0 ) {
					return 0;
				} else {
					return 180;
				}
			/*}*/
			
		} else {
			//S - W
			if(end.x() - startSnapped.x() > 0) {
				return 90;
			} else {
				return 270;
			}
		}
		
	}

	@Override
	protected void generatePaths() {

		// Reset
		getAllPaths().clear();

		// Create
		RailPath path = new RailPath(start, end);

		// Add start
		path.newPoint(start.x(), start.y(), start.z());

		// Generate mid points
		int distance = (int) Math.ceil(start.distance(end));
		
		CurveMath curveMath = new CurveMath(start, startAngle, end, endAngle, 1);
		List<IPos> points = curveMath.getCurvePoints();

		influencePointA = curveMath.startInfluencePoint;
		influencePointB = curveMath.endInfluencePoint;

		path.newPoints(points);

		// Add end
		path.newPoint(end.x(), end.y(), end.z());

		// add path
		getAllPaths().add(path);		

	}
}
